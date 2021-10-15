package acme.features.anonymous.shout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.shouts.Shout;

@Repository
public interface AnonymousShoutRepository extends JpaRepository<Shout, Integer>, JpaSpecificationExecutor<Shout> {

	
}
