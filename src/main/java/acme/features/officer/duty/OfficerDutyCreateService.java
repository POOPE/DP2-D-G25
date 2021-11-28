
package acme.features.officer.duty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.features.authenticated.officer.AuthenticatedOfficerRepository;
import acme.features.spamfilter.SpamFilterService;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class OfficerDutyCreateService extends SpamFilterService<Officer, Duty> implements AbstractCreateService<Officer, Duty> {

	@Autowired
	private OfficerDutyRepository	repo;

	@Autowired
	private AuthenticatedOfficerRepository		officerRepo;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "executionStart", "executionEnd", "workload", "link");

	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public Duty instantiate(final Request<Duty> request) {
		return new Duty();
	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {
		final Optional<Officer> owner = this.officerRepo.findOneByAccountId(request.getPrincipal().getAccountId());
		if (owner.isPresent()) {
			entity.setOfficer(owner.get());
			this.repo.save(entity);
		}
	}

	@Override
	public void validateAndFilter(final Request<Duty> request, final Duty entity, final Errors errors) {
		//do nothing
	}

}
