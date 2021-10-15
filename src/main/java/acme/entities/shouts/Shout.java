package acme.entities.shouts;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.Specification;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Shout extends DomainEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------


	@NotNull
	@Past
	private LocalDateTime				moment;

	@NotBlank
	@Length(min = 5, max = 25)
	private String			author;

	@NotBlank
	@Length(max = 100)
	private String			text;
	
	@URL
	private String link;


	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	
	// Specifications
	
	public static Specification<Shout> isFromLastMonth(){
		return new Specification<Shout>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -801717304557338995L;

			@Override
			public Predicate toPredicate(final Root<Shout> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {
				final LocalDateTime reference = LocalDateTime.now().minusMonths(1);
				return criteriaBuilder.greaterThanOrEqualTo(root.get("moment"), reference);
			}
			
		};
	}


}
