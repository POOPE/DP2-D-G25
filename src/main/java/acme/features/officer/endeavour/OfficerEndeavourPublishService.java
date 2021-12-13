
package acme.features.officer.endeavour;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.services.AbstractUpdateService;

@Component("officerEndeavourPublishService")
public class OfficerEndeavourPublishService extends SpamFilterService<Officer, Endeavour> implements AbstractUpdateService<Officer, Endeavour> {

	@Autowired
	private OfficerEndeavourService service;
	
	@Autowired
	private OfficerEndeavourRepository	repo;


	@Override
	public boolean authorise(final Request<Endeavour> request) {
		assert request != null;

		return this.service.checkPrincipalIsOwner(request.getModel().getInteger("id"));
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

		request.bind(entity, errors,"isPublic");
	}

	@Override
	public void update(final Request<Endeavour> request, final Endeavour entity) {

		entity.setIsPublic(true);
		this.service.calculateDuration(entity);
		this.repo.save(entity);
	}

	@Transactional
	@Override
	public void validateAndFilter(final Request<Endeavour> request, final Endeavour entity, final Errors errors) {
		entity.setIsPublic(true);
		entity.setDuties(this.service.validateDuties(request, entity, errors));
	}

	@Transactional
	@Override
	public void onFailure(final Request<Endeavour> request, final Response<Endeavour> response, final Throwable oops) {
		AbstractUpdateService.super.onFailure(request, response, oops);
		this.service.appendDutiesToModel(response, request);
	}

}
