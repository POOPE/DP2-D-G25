
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import acme.datatypes.BasicDuration;
import acme.entities.duties.Duty;
import acme.entities.duties.DutyDto;
import acme.entities.endeavours.Endeavour;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.PrincipalHelper;

@Service
@Qualifier("officerEndeavourCommonService")
public class OfficerEndeavourService {

	Logger						logger	= LoggerFactory.getLogger(OfficerEndeavourService.class);

	@Autowired
	ModelMapper					mapper;

	@Autowired
	OfficerEndeavourRepository	repo;

	@Autowired
	OfficerDutyRepository		dutyRepo;


	@Transactional
	public void appendDutiesToModel(final Model model, final Endeavour entity) {
		try {
			final List<Duty> available = this.dutyRepo.findAll(Duty.canBeAddedToEndeavour(entity.getIsPublic()));
			available.removeAll(entity.getDuties());
			model.setAttribute("binded_duties", this.mapper.map(entity.getDuties(), new TypeToken<List<DutyDto>>() {
			}.getType()));
			model.setAttribute("available_duties", this.mapper.map(available, new TypeToken<List<DutyDto>>() {
			}.getType()));
		} catch (final Exception e) {
			this.logger.warn("appendDutiesToModel() - Error appending duties ", e);
			model.setAttribute("available_duties", this.mapper.map(this.dutyRepo.findAll(Duty.canBeAddedToEndeavour(entity.getIsPublic())), new TypeToken<List<DutyDto>>() {
			}.getType()));
			model.setAttribute("binded_duties", new ArrayList<DutyDto>());
		}
	}

	@Transactional
	public void appendDutiesToModel(final Response<Endeavour> response, final Request<Endeavour> request) {
		Assert.isTrue(response != null, "Response is null");
		Assert.isTrue(request != null, "Request is null");
		Assert.isTrue(response.getModel() != null, "Model is null");

		final Optional<Endeavour> opt = this.repo.findById(Integer.valueOf((String) request.getModel().getAttribute("id")));
		Assert.isTrue(opt.isPresent(), "Could not find endeavour");
		this.appendDutiesToModel(response.getModel(), opt.get());

	}

	@Transactional
	public void validate(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		try {
			Assert.isTrue(entity.getExecutionEnd().isAfter(entity.getExecutionStart()), "Execution start after execution end");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.endeavour.form.label.error.execution.inconsistency");
			errors.add("executionEnd", errorMsg);
		}

		entity.setDuties(this.validateDuties(request, entity, errors));
	}

	@Transactional
	public List<Duty> validateDuties(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		try {
			final List<Integer> dutyIds = Arrays.asList(request.getModel().getAttribute("binded_duties_input", String.class).split(",")).stream().map(s -> Integer.valueOf(s.substring(5))).collect(Collectors.toList());
			final List<Duty> duties = this.dutyRepo.findByIdInCollection(dutyIds);
			return this.validateDuties(entity, duties, errors);
		} catch (final Exception e) {
			this.logger.warn("Problem validating duties, appending empty list", e);
			return new ArrayList<Duty>();
		}
	}

	@Transactional
	public List<Duty> validateDuties(final Endeavour entity, final List<Duty> duties, final Errors errors) {
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

		return duties;
	}

	public void calculateDuration(final Endeavour entity) {
		if (!entity.getDuties().isEmpty()) {
			final Integer minutes = entity.getDuties().stream().map(Duty::getWorkload).mapToInt(BasicDuration::getAsMinutes).sum();
			final BasicDuration duration = new BasicDuration(0, minutes);
			entity.setDuration(duration);
		} else {
			entity.setDuration(new BasicDuration());
		}
	}

	public boolean checkPrincipalIsOwner(final int id) {
		final Optional<Endeavour> endeavour = this.repo.findOne(Endeavour.withId(id));
		if (endeavour.isPresent()) {
			if (endeavour.get().getOfficer().getUserAccount().getId() != PrincipalHelper.get().getAccountId()) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
}
