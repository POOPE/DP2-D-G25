
package acme.features.officer.duty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class OfficerDutyShowService implements AbstractShowService<Officer, Duty> {

	@Autowired
	private OfficerDutyRepository repo;


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
	public Duty findOne(final Request<Duty> request) {
		Duty res = null;
		final Optional<Duty> duty = this.repo.findOne(Duty.withId(request.getModel().getInteger("id")).and(Duty.withOfficer(request.getPrincipal().getAccountId())));
		if (duty.isPresent()) {
			res = duty.get();
		}
		return res;
	}

}
