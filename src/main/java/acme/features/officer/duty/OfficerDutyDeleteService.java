package acme.features.officer.duty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class OfficerDutyDeleteService implements AbstractDeleteService<Officer, Duty>{

	@Autowired
	private OfficerDutyRepository repo;

	
	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		final Integer officerUserAccountId = this.repo.getOne(request.getModel().getInteger("id")).getOfficer().getUserAccount().getId();
		assert request.getPrincipal().getAccountId() == officerUserAccountId;
		
		return true;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "executionStart","executionEnd","workload","link");
		
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		Duty res = null;
		final Optional<Duty> duty = this.repo.findOne(Duty.withId(request.getModel().getInteger("id")));
		if(duty.isPresent()) {
			res = duty.get();
		}else {
			throw new RuntimeException("Duty does not exist");
		}
		return res;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Duty> request, final Duty entity) {
		assert request != null;
		assert entity != null;

		this.repo.delete(entity);
		
	}

}
