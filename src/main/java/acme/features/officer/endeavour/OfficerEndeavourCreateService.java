
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
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import acme.datatypes.BasicDuration;
import acme.entities.duties.Duty;
import acme.entities.duties.DutyDto;
import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.authenticated.officer.AuthenticatedOfficerRepository;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.helpers.MessageHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerEndeavourCreateService extends SpamFilterService<Officer, Endeavour> implements AbstractCreateService<Officer, Endeavour> {

	@Autowired
	private ModelMapper						mapper;

	@Autowired
	private OfficerEndeavourRepository		repo;

	@Autowired
	private OfficerDutyRepository			dutyRepo;

	@Autowired
	private AuthenticatedOfficerRepository	officerRepo;


	@Override
	public boolean authorise(final Request<Endeavour> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Endeavour> request, final Endeavour entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		model.setAttribute("available_duties", this.mapper.map(this.dutyRepo.findAll(), new TypeToken<List<DutyDto>>() {
		}.getType()));
		model.setAttribute("binded_duties", new ArrayList<DutyDto>());

		request.unbind(entity, model, "executionStart", "executionEnd");
	}

	@Override
	public void bind(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public Endeavour instantiate(final Request<Endeavour> request) {
		final Endeavour res = new Endeavour();
		res.setIsPublic(false);
		return res;
	}

	@Override
	public void create(final Request<Endeavour> request, final Endeavour entity) {
		final Optional<Officer> owner = this.officerRepo.findOneByAccountId(request.getPrincipal().getAccountId());

		if (!entity.getDuties().isEmpty()) {
			final Integer minutes = entity.getDuties().stream().map(Duty::getWorkload).mapToInt(BasicDuration::getAsMinutes).sum();
			final BasicDuration duration = new BasicDuration(0, minutes);
			entity.setDuration(duration);
		} else {
			entity.setDuration(new BasicDuration());
		}

		if (owner.isPresent()) {
			entity.setOfficer(owner.get());
			this.repo.save(entity);
		}
	}

	@Transactional
	@Override
	public void validateAndFilter(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		
		try {
			Assert.isTrue(entity.getExecutionEnd().isAfter(entity.getExecutionStart()),"Execution start after execution end");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.endeavour.form.label.error.execution.inconsistency");
			errors.add("executionEnd", errorMsg);
		}
		
		//fetch duties
		if (request.getModel().getAttribute("binded_duties_input", String.class) != null && !request.getModel().getAttribute("binded_duties_input", String.class).isBlank()) {
			final List<Integer> dutyIds = Arrays.asList(request.getModel().getAttribute("binded_duties_input", String.class).split(",")).stream().map(s -> Integer.valueOf(s.substring(5))).collect(Collectors.toList());
			final List<Duty> duties = this.dutyRepo.findByIdInCollection(dutyIds);

			//private/public
			try {
				//if restriction true means the endeavour has a duty that it should not have
				final Boolean publicRestriction = duties.stream().anyMatch(d -> {
					if (Boolean.TRUE.equals(entity.getIsPublic()) && Boolean.FALSE.equals(d.getIsPublic())) {
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
		}
	}

	@Transactional
	@Override
	public void onFailure(final Request<Endeavour> request, final Response<Endeavour> response, final Throwable oops) {
		response.getModel().setAttribute("available_duties", this.mapper.map(this.dutyRepo.findAll(), new TypeToken<List<DutyDto>>() {
		}.getType()));
		response.getModel().setAttribute("binded_duties", new ArrayList<DutyDto>());
	}
	
	
}
