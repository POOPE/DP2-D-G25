package acme.features.anonymous.shout;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.shouts.Shout;

@Repository
public interface AnonymousShoutRepository extends JpaRepository<Shout, Integer>, JpaSpecificationExecutor<Shout> {

	@Query("select s from Shout s where s.moment >= ?1 order by s.moment desc")
	List<Shout> findInTheLast30Days(Date date30DaysAgo);

	
}
