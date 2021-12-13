
package acme.features.officer.endeavour;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.authenticated.officer.AuthenticatedOfficerRepository;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerEndeavourCreateService extends SpamFilterService<Officer, Endeavour> implements AbstractCreateService<Officer, Endeavour>{

	@Autowired
	private OfficerEndeavourService service;
	
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

		this.service.appendDutiesToModel(model, entity);

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

		this.service.calculateDuration(entity);

		if (owner.isPresent()) {
			entity.setOfficer(owner.get());
			this.repo.save(entity);
		}
	}

	@Transactional
	@Override
	public void validateAndFilter(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {	
		entity.setDuties(this.service.validateDuties(request, entity, errors));
	}

	@Transactional
	@Override
	public void onFailure(final Request<Endeavour> request, final Response<Endeavour> response, final Throwable oops) {
		this.service.appendDutiesToModel(response, request);
	}

	
	
	
}
