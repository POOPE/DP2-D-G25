
package acme.entities.duties;

import java.time.LocalDateTime;

import acme.datatypes.BasicDuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DutyDto {

	private Integer			id;
	private String			title;
	private String			description;
	private LocalDateTime	executionStart;
	private LocalDateTime	executionEnd;
	private BasicDuration	workload;
	private Boolean			isPublic;

}
