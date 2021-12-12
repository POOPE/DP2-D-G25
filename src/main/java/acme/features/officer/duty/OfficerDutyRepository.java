package acme.features.officer.duty;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.entities.duties.Duty;

@Repository
public interface OfficerDutyRepository extends JpaRepository<Duty, Integer>, JpaSpecificationExecutor<Duty> {

	@Query("select d from Duty d where d.isPublic = true")
	public List<Duty> findAllPublic();
	
	@Query("select d from Duty d where d.id in :ids")
	public List<Duty> findByIdInCollection(@Param("ids") List<Integer> ids);
}
