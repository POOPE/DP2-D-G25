
package acme.features.officer.endeavour;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import acme.datatypes.BasicDuration;
import acme.entities.duties.Duty;
import acme.entities.duties.DutyDto;
import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Component("officerEndeavourPublishService")
public class OfficerEndeavourPublishService extends SpamFilterService<Officer, Endeavour> implements AbstractUpdateService<Officer, Endeavour> {

	@Autowired
	private OfficerEndeavourRepository	repo;

	@Autowired
	private OfficerDutyRepository		dutyRepo;

	@Autowired
	private ModelMapper					mapper;


	@Override
	public boolean authorise(final Request<Endeavour> request) {
		assert request != null;

		final Optional<Endeavour> endeavour = this.repo.findOne(Endeavour.withId(request.getModel().getInteger("id")));
		if (endeavour.isPresent()) {
			if (endeavour.get().getOfficer().getUserAccount().getId() != PrincipalHelper.get().getAccountId()) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public void unbind(final Request<Endeavour> request, final Endeavour entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "executionStart", "executionEnd");

	}

	@Override
	public Endeavour findOne(final Request<Endeavour> request) {
		Endeavour res = null;
		final Optional<Endeavour> endeavour = this.repo.findOne(Endeavour.withId(request.getModel().getInteger("id")));
		if (endeavour.isPresent()) {
			res = endeavour.get();
		}
		return res;
	}

	@Override
	public void bind(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void update(final Request<Endeavour> request, final Endeavour entity) {

		entity.setIsPublic(true);
		if (entity.getDuties() != null && !entity.getDuties().isEmpty()) {
			final Integer minutes = entity.getDuties().stream().map(Duty::getWorkload).mapToInt(BasicDuration::getAsMinutes).sum();
			final BasicDuration duration = new BasicDuration(0, minutes);
			entity.setDuration(duration);
		} else {
			entity.setDuration(new BasicDuration());
		}
		this.repo.save(entity);
	}

	@Transactional
	@Override
	public void validateAndFilter(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		//fetch duties

		if (request.getModel().getAttribute("binded_duties_input", String.class) != null && !request.getModel().getAttribute("binded_duties_input", String.class).isBlank()) {
			final List<Integer> dutyIds = Arrays.asList(request.getModel().getAttribute("binded_duties_input", String.class).split(",")).stream().map(s -> Integer.valueOf(s.substring(5))).collect(Collectors.toList());
			final List<Duty> duties = this.dutyRepo.findByIdInCollection(dutyIds);

			//private/public
			try {
				//if restriction true means the endeavour has a duty that it should not have
				final Boolean publicRestriction = duties.stream().anyMatch(d -> {
					if (Boolean.FALSE.equals(d.getIsPublic())) {
						return true;
					} else {
						return false;
					}
				});
				Assert.isTrue(!publicRestriction, "Public endeavours cannot have private duties");
			} catch (final Exception e) {
				final String errorMsg = MessageHelper.getMessage("officer.endeavour.form.error.duty.publicrestriction");
				errors.add("duties", errorMsg);
			}

			//execution period
			try {
				final Optional<LocalDateTime> dutyExecutionPeriodStart = duties.stream().map(Duty::getExecutionStart).min(LocalDateTime::compareTo);
				final Optional<LocalDateTime> dutyExecutionPeriodEnd = duties.stream().map(Duty::getExecutionEnd).max(LocalDateTime::compareTo);

				if (dutyExecutionPeriodStart.isPresent() && dutyExecutionPeriodEnd.isPresent()) {
					Assert.isTrue(entity.getExecutionStart().isBefore(dutyExecutionPeriodStart.get()) && entity.getExecutionEnd().isAfter(dutyExecutionPeriodEnd.get()), "Duties do not fit within given endeavour execution period");
				}
			} catch (final Exception e) {
				final String errorMsg = MessageHelper.getMessage("officer.endeavour.form.error.duty.executionperiod");
				errors.add("duties", errorMsg);
			}

			if (!errors.hasErrors()) {
				entity.setDuties(duties);
			}
		} else {
			entity.setDuties(new ArrayList<>());
		}

	}

	@Transactional
	@Override
	public void onFailure(final Request<Endeavour> request, final Response<Endeavour> response, final Throwable oops) {
		AbstractUpdateService.super.onFailure(request, response, oops);
		final Optional<Endeavour> endeavour = this.repo.findById(Integer.valueOf((String) request.getModel().getAttribute("id")));
		if (endeavour.isPresent()) {
			final List<Duty> duties = endeavour.get().getDuties();
			final List<Duty> available = this.dutyRepo.findAll(Duty.canBeAddedToEndeavour(endeavour.get().getIsPublic()));
			available.removeAll(duties);
			
			response.getModel().setAttribute("binded_duties", this.mapper.map(duties, new TypeToken<List<DutyDto>>() {
			}.getType()));
			response.getModel().setAttribute("available_duties", this.mapper.map(available, new TypeToken<List<DutyDto>>() {
			}.getType()));
		} else {
			response.getModel().setAttribute("available_duties", this.mapper.map(this.dutyRepo.findAll(Duty.canBeAddedToEndeavour(true)), new TypeToken<List<DutyDto>>() {
			}.getType()));
			response.getModel().setAttribute("binded_duties", new ArrayList<DutyDto>());
		}
	}

}
