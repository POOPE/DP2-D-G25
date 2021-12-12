/*
 * AnonymousShoutController.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.officer.endeavour;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.endeavours.Endeavour;
import acme.entities.roles.Officer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;

@Controller
@RequestMapping("/officer/endeavour/")
public class OfficerEndeavourController extends AbstractController<Officer, Endeavour> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected OfficerEndeavourListService	listService;
	
	@Autowired
	protected OfficerEndeavourShowService	showService;
	
	@Autowired
	protected OfficerEndeavourCreateService createService;
	
	@Autowired
	protected OfficerEndeavourUpdateService updateService;
	
	@Autowired
	protected OfficerEndeavourDeleteService deleteService;

	@Autowired
	@Qualifier("officerEndeavourPublishService")
	protected OfficerEndeavourPublishService publishService;

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addCustomCommand(CustomCommand.LIST_MINE, BasicCommand.LIST, this.listService);
		super.addCustomCommand(CustomCommand.PUBLISH, BasicCommand.UPDATE, this.publishService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
