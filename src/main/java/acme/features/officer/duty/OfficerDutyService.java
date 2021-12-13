
package acme.features.officer.duty;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import acme.entities.duties.Duty;
import acme.entities.endeavours.Endeavour;
import acme.features.officer.endeavour.OfficerEndeavourRepository;
import acme.framework.components.Errors;
import acme.framework.components.Request;
import acme.framework.helpers.MessageHelper;
import acme.framework.helpers.PrincipalHelper;

@Service
@Qualifier("officerDutyCommonService")
public class OfficerDutyService {

	@Autowired
	OfficerEndeavourRepository	endeavourRepo;

	@Autowired
	OfficerDutyRepository		repo;


	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {

		if (entity.getId() != 0 && Boolean.FALSE.equals(entity.getIsPublic()) && this.endeavourRepo.findAll(Endeavour.withDuty(entity.getId())).stream().anyMatch(Endeavour::getIsPublic)) {
			final String errorMsg = MessageHelper.getMessage("officer.duty.form.error.publicrestriction");
			errors.add("duties", errorMsg);
		}

		try {
			Assert.isTrue(entity.getExecutionStart().isAfter(LocalDateTime.now()), "Execution start date has to be in the futre");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.duty.form.label.error.executionstart.future");
			errors.add("executionStart", errorMsg);
		}

		try {
			Assert.isTrue(entity.getExecutionEnd().isAfter(LocalDateTime.now()), "Execution start date has to be in the futre");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.duty.form.label.error.executionend.future");
			errors.add("executionEnd", errorMsg);
		}

		try {
			Assert.isTrue(entity.getExecutionEnd().isAfter(entity.getExecutionStart()), "Execution start after execution end");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.duty.form.label.error.execution.inconsistency");
			errors.add("executionEnd", errorMsg);
		}
	}

	public void validateForDelete(final Request<Duty> request, final Duty entity, final Errors errors) {
		try {
			Assert.isTrue(this.endeavourRepo.findAll(Endeavour.withDuty(entity.getId())).isEmpty(),"There are associated endeavours");
		} catch (final Exception e) {
			final String errorMsg = MessageHelper.getMessage("officer.duty.form.label.error.associated");
			errors.add("link", errorMsg);
		}
	}

	public boolean checkPrincipalIsOwner(final int id) {
		final Optional<Duty> d = this.repo.findOne(Duty.withId(id));
		if (d.isPresent()) {
			if (d.get().getOfficer().getUserAccount().getId() != PrincipalHelper.get().getAccountId()) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
}
