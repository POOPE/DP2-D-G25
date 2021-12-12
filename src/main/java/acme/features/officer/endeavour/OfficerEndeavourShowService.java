
package acme.features.officer.endeavour;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.duties.Duty;
import acme.entities.duties.DutyDto;
import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.features.officer.duty.OfficerDutyRepository;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class OfficerEndeavourShowService implements AbstractShowService<Officer, Endeavour> {

	@Autowired
	private OfficerEndeavourRepository repo;
	
	@Autowired
	private OfficerDutyRepository dutyRepo;
	
	@Autowired
	private ModelMapper mapper;

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
		
		model.setAttribute("binded_duties", this.mapper.map(entity.getDuties(), new TypeToken<List<DutyDto>>() {
		}.getType()));	
		final List<Duty> available = this.dutyRepo.findAll(Duty.canBeAddedToEndeavour(entity.getIsPublic()));
		available.removeAll(entity.getDuties());
		model.setAttribute("available_duties", this.mapper.map(available, new TypeToken<List<DutyDto>>() {
		}.getType()));

		request.unbind(entity, model, "executionStart", "executionEnd", "duration","isPublic");

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
