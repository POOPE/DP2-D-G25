
package acme.datatypes;

import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class BasicDuration implements Comparable<BasicDuration>{

	@NotNull
	@Range(min = 0, max = 99)
	private Integer	hours;
	@Range(min = 0, max = 59)
	private Integer	minutes;




	public static BasicDuration of(final LocalDate start, final LocalDate end) {
		final Integer minutes = Math.toIntExact(Duration.between(start, end).toMinutes());
		return new BasicDuration(null,minutes);
	}

	public static BasicDuration of(final Duration duration) {
		return new BasicDuration(null, Long.valueOf(duration.toMinutes()).intValue());	
	}
	
	/**
	 * If minutes are more than 59 it adds to hours
	 * 
	 * @param hours
	 * @param minutes
	 */
	public BasicDuration(final Integer hours, final Integer minutes) {
		super();
		if (hours != null) {
			this.hours = hours;
		} else {
			this.hours = 0;
		}

		if (minutes != null) {
			if (minutes < 60) {
				this.minutes = minutes;
			} else {
				final Integer moreHours = minutes / 60;
				this.hours += moreHours;
				this.minutes = minutes % 60;
			}
		}else {
			this.minutes = 0;
		}
	}
	
	public Integer getAsMinutes() {
		return (this.hours * 60) + this.minutes;
	}
	
	public Duration getAsDuration() {
		return Duration.ofMinutes(this.getAsMinutes());
	}
	
	@Override
	public String toString() {
		return this.hours + ":" + String.format("%02d", this.minutes);
	}

	/**
	 * return -1 if smaller than o, 1 if o is bigger
	 */
	@Override
	public int compareTo(final BasicDuration o) {
		return Integer.compare(this.getAsMinutes(), o.getAsMinutes());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.hours == null) ? 0 : this.hours.hashCode());
		result = prime * result + ((this.minutes == null) ? 0 : this.minutes.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		final BasicDuration other = (BasicDuration) obj;
		if (this.hours == null) {
			if (other.hours != null)
				return false;
		} else if (!this.hours.equals(other.hours))
			return false;
		if (this.minutes == null) {
			if (other.minutes != null)
				return false;
		} else if (!this.minutes.equals(other.minutes))
			return false;
		return true;
	}

}
