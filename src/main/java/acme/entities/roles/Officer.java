package acme.entities.roles;

import javax.persistence.Entity;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Officer extends UserRole{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6588641480060098115L;

}
