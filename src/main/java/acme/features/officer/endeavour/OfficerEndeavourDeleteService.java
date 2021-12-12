package acme.features.officer.endeavour;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class OfficerEndeavourDeleteService implements AbstractDeleteService<Officer, Endeavour>{

	@Autowired
	private OfficerEndeavourRepository repo;

	
	@Override
	public boolean authorise(final Request<Endeavour> request) {
		assert request != null;
		final Integer officerUserAccountId = this.repo.getOne(request.getModel().getInteger("id")).getOfficer().getUserAccount().getId();
		assert request.getPrincipal().getAccountId() == officerUserAccountId;
		
		return true;
	}

	@Override
	public void unbind(final Request<Endeavour> request, final Endeavour entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "executionStart","executionEnd","workload","link");
		
	}

	@Override
	public Endeavour findOne(final Request<Endeavour> request) {
		Endeavour res = null;
		final Optional<Endeavour> endeavour = this.repo.findOne(Endeavour.withId(request.getModel().getInteger("id")));
		if(endeavour.isPresent()) {
			res = endeavour.get();
		}else {
			throw new RuntimeException("Endeavour does not exist");
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
	public void validate(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Endeavour> request, final Endeavour entity) {
		assert request != null;
		assert entity != null;

		this.repo.delete(entity);
		
	}

}
