package acme.features.anonymous.duty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractShowService;

@Service
public class AnonymousDutyShowService implements AbstractShowService<Anonymous, Duty>{

	@Autowired
	private AnonymousDutyRepository repo;

	
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

		request.unbind(entity, model, "title", "description", "executionStart","executionEnd","workload","link");
		
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		Duty res = null;
		final Optional<Duty> duty = this.repo.findOne(Duty.withId(request.getModel().getInteger("id")).and(Duty.executionOver(false)).and(Duty.isPublic(true)));
		if(duty.isPresent()) {
			res = duty.get();
		}
		return res;
	}

}
