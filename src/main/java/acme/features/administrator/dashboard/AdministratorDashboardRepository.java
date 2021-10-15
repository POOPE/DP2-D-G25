
package acme.features.administrator.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(t) from Duty t where t.isPublic = true")
	Integer countPublicDuties();

	@Query("select count(t) from Duty t where t.isPublic = false")
	Integer countPrivateDuties();

	@Query("select avg(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Duty t")
	Double avgDutyExecutionPeriod();

	@Query("select stddev(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Duty t")
	Double stddevDutyExecutionPeriod();

	@Query("select min(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Duty t")
	Double minDutyExecutionPeriod();

	@Query("select max(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Duty t")
	Double maxDutyExecutionPeriod();

	@Query("select avg(t.workload.hours * 60 + t.workload.minutes) from Duty t")
	Double avgDutyWorkload();

	@Query("select stddev(t.workload.hours * 60 + t.workload.minutes) from Duty t")
	Double stddevDutyWorkload();

	@Query("select min(t.workload.hours * 60 + t.workload.minutes) from Duty t")
	Double minDutyWorkload();

	@Query("select max(t.workload.hours * 60 + t.workload.minutes) from Duty t")
	Double maxDutyWorkload();

}
