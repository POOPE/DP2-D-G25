package acme.entities.duties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Duty extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3718473227955650379L;
	
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@Future
	@NotNull
	private LocalDateTime executionStart;
	@NotNull
	private LocalDateTime executionEnd;
	@NotNull
	private BigDecimal workload;
	@URL
	private String link;
	
}
