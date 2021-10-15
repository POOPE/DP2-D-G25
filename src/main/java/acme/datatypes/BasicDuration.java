package acme.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicDuration {

	@NotNull
	@Range(min=0, max=99)
	private Integer hours;
	@Range(min=0, max=59)
	private Integer minutes;
	@Override
	public String toString() {
		return this.hours + ":" + this.minutes;
	}
	
	
}
