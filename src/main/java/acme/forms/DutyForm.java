package acme.forms;

import java.time.LocalDateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DutyForm {

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
	private Integer workloadHours;
	private Integer workloadMinutes;
	
	@URL
	private String link;
}
