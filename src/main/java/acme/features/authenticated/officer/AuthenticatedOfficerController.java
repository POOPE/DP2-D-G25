package acme.features.authenticated.officer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.Officer;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/officer/")
public class AuthenticatedOfficerController extends AbstractController<Authenticated, Officer> {

	// Internal state ---------------------------------------------------------

		@Autowired
		protected AuthenticatedOfficerCreateService	createService;


		// Constructors -----------------------------------------------------------


		@PostConstruct
		protected void initialise() {
			super.addBasicCommand(BasicCommand.CREATE, this.createService);
		}
}
