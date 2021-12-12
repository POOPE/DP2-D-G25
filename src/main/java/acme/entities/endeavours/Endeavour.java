
package acme.entities.endeavours;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.Specification;

import acme.datatypes.BasicDuration;
import acme.entities.duties.Duty;
import acme.entities.roles.Officer;
import acme.framework.entities.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endeavour extends DomainEntity {

	/**
	* 
	*/
	private static final long	serialVersionUID	= 7862533006967715121L;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Officer				officer;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Duty>			duties;

	@Column(nullable = false)
	private Boolean				isPublic;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime		executionStart;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime		executionEnd;

	private BasicDuration		duration;


	public Endeavour calculateExecutionPeriod() {
		if (!this.getDuties().isEmpty()) {
			LocalDateTime start = null;
			LocalDateTime end = null;
			for (final Duty duty : this.getDuties()) {
				if (start == null || duty.getExecutionStart().isBefore(start))
					start = duty.getExecutionStart();
				if (end == null || duty.getExecutionEnd().isAfter(end))
					end = duty.getExecutionEnd();
			}

			if (start != null && end != null) {
				this.setExecutionStart(start.minus(1, ChronoUnit.DAYS).with(LocalTime.of(6, 0)));
				this.setExecutionEnd(end.plus(1, ChronoUnit.DAYS).with(LocalTime.of(17, 0)));
			}

		}
		return this;
	}

	public static Specification<Endeavour> withId(final Integer id) {
		return new Specification<Endeavour>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1588747159144666818L;


			@Override
			public Predicate toPredicate(final Root<Endeavour> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("id"), id);
			}

		};
	}

	public static Specification<Endeavour> withOfficer(final Integer id) {
		return new Specification<Endeavour>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1588747159144666818L;


			@Override
			public Predicate toPredicate(final Root<Endeavour> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("officer").get("userAccount").get("id"), id);
			}

		};
	}

	public static Specification<Endeavour> isFinished(final boolean finished) {
		return new Specification<Endeavour>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Endeavour> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

				return finished ? criteriaBuilder.lessThanOrEqualTo(root.get("executionEnd"), LocalDateTime.now()) : criteriaBuilder.greaterThanOrEqualTo(root.get("executionEnd"), LocalDateTime.now());

			}

		};
	}

	public static Specification<Endeavour> isPublic(final boolean finished) {
		return new Specification<Endeavour>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Endeavour> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

				return criteriaBuilder.equal(root.get("isPublic"), finished);

			}

		};
	}

	public static Specification<Endeavour> withDuty(final Integer dutyId) {
		return new Specification<Endeavour>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Endeavour> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				final Join<Endeavour, Duty> join = root.join("duties");
				return criteriaBuilder.equal(join.get("id"), dutyId);

			}

		};
	}
}
