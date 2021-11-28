package acme.features.authenticated.officer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Officer;
import acme.framework.entities.UserAccount;

@Repository
public interface AuthenticatedOfficerRepository extends JpaRepository<Officer, Integer>, JpaSpecificationExecutor<Officer> {

	@Query("select o from Officer o where o.userAccount.id = ?1")
	Optional<Officer> findOneByAccountId(Integer id);
	
	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

}
