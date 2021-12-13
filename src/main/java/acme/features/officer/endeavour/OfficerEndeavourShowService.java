
package acme.features.officer.endeavour;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class OfficerEndeavourShowService implements AbstractShowService<Officer, Endeavour> {

	@Autowired
	private OfficerEndeavourRepository	repo;

	@Autowired
	private OfficerDutyRepository		dutyRepo;
	
	@Autowired
	private OfficerEndeavourService service;

	@Autowired
	private ModelMapper					mapper;


	@Override
	public boolean authorise(final Request<Endeavour> request) {
		assert request != null;
		return true;
	}

	@Transactional
	@Override
	public void unbind(final Request<Endeavour> request, final Endeavour entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		this.service.appendDutiesToModel(model, entity);

		request.unbind(entity, model, "executionStart", "executionEnd", "duration", "isPublic");
	}

	@Transactional
	@Override
	public Endeavour findOne(final Request<Endeavour> request) {
		Endeavour res = null;

		final Optional<Endeavour> endeavour = this.repo.findOne(Endeavour.withId(request.getModel().getInteger("id")));
		if (endeavour.isPresent()) {
			res = endeavour.get();
			Hibernate.initialize(res.getDuties());
		}
		return res;
	}

}
