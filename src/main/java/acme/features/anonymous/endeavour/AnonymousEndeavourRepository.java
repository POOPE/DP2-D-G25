package acme.features.anonymous.endeavour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.endeavours.Endeavour;

@Repository
public interface AnonymousEndeavourRepository extends JpaRepository<Endeavour, Integer>, JpaSpecificationExecutor<Endeavour> {


}
