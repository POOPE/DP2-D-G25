
package acme.entities.duties;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.Specification;

import acme.datatypes.BasicDuration;
import acme.entities.roles.Officer;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Duty extends DomainEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3718473227955650379L;

	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	private Officer officer;
	@NotBlank
	private String				title;
	@NotBlank
	private String				description;
	@NotNull
	private LocalDateTime		executionStart;
	@NotNull
	private LocalDateTime		executionEnd;
	@NotNull
	private BasicDuration		workload;
	@URL
	private String				link;

	//actually represents public/private
	private Boolean				isPublic;

	// specifications
	
	public static Specification<Duty> withOfficer(final Integer id) {
		return new Specification<Duty>() {


			/**
			 * 
			 */
			private static final long serialVersionUID = -1588747159144666818L;

			@Override
			public Predicate toPredicate(final Root<Duty> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("officer").get("userAccount").get("id"), id);
			}

		};
	}

	public static Specification<Duty> executionOver(final boolean isExecutionOver) {
		return new Specification<Duty>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Duty> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				final LocalDateTime reference = LocalDateTime.now();
				if (!isExecutionOver) {
					return criteriaBuilder.greaterThan(root.get("executionEnd"), reference);
				} else {
					return criteriaBuilder.lessThanOrEqualTo(root.get("executionEnd"), reference);
				}
			}

		};
	}
	
	public static Specification<Duty> withId(final Integer id) {
		return new Specification<Duty>() {


			/**
			 * 
			 */
			private static final long serialVersionUID = -1588747159144666818L;

			@Override
			public Predicate toPredicate(final Root<Duty> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get("id"), id);
			}

		};
	}

	public static Specification<Duty> isPublic(final boolean isPublic) {
		return new Specification<Duty>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Duty> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

				return criteriaBuilder.equal(root.get("isPublic"), isPublic);

			}

		};
	}
	
	public static Specification<Duty> canBeAddedToEndeavour(final boolean isPublic) {
		return new Specification<Duty>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7040689053525813319L;


			@Override
			public Predicate toPredicate(final Root<Duty> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				if(isPublic) {
					return criteriaBuilder.equal(root.get("isPublic"), true);
				}else {
					return criteriaBuilder.and();
				}
			}

		};
	}

	

}
