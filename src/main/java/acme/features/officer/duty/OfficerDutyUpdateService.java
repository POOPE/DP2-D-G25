
package acme.features.officer.duty;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class OfficerDutyUpdateService extends SpamFilterService<Officer, Duty> implements AbstractUpdateService<Officer, Duty> {

	@Autowired
	private OfficerDutyService			service;

	@Autowired
	private OfficerDutyRepository		repo;

	@Autowired
	private ModelMapper					modelMapper;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;
		return this.service.checkPrincipalIsOwner(request.getModel().getInteger("id"));
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "executionStart", "executionEnd", "workload", "link", "isPublic");

	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		Duty res = null;
		final Optional<Duty> duty = this.repo.findOne(Duty.withId(request.getModel().getInteger("id")).and(Duty.withOfficer(request.getPrincipal().getAccountId())));
		if (duty.isPresent()) {
			res = duty.get();
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
	public void update(final Request<Duty> request, final Duty entity) {
		this.repo.findById(request.getModel().getInteger("id")).ifPresent(i -> {
			final Duty duty = this.modelMapper.map(entity, Duty.class);
			this.repo.save(duty);
		});
		;
	}

	@Override
	public void validateAndFilter(final Request<Duty> request, final Duty entity, final Errors errors) {
		this.service.validate(request, entity, errors);
	}

}
