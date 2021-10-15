package acme.features.authenticated.duty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;

@Repository
public interface AuthenticatedDutyRepository extends JpaRepository<Duty, Integer>, JpaSpecificationExecutor<Duty> {


}
