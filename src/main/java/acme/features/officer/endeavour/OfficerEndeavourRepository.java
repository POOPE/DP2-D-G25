package acme.features.officer.endeavour;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.endeavours.Endeavour;

@Repository
public interface OfficerEndeavourRepository extends JpaRepository<Endeavour, Integer>, JpaSpecificationExecutor<Endeavour> {


}
