
package acme.features.administrator.dashboard;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	//ENDEAVOUR STUFF
	@Query("select count(e) from Endeavour e")
	Integer countEndeavours();
	
	@Query("select count(t) from Endeavour t where t.isPublic = true")
	Integer countPublicEndeavours();
	
	@Query("select count(t) from Endeavour t where t.isPublic = false")
	Integer countPrivateEndeavours();

	@Query("select count(t) from Endeavour t where t.executionEnd < :date")
	Integer countEndeavoursBeforeDate(@Param("date")LocalDateTime date);
	
	@Query("select count(t) from Endeavour t where t.executionEnd > :date")
	Integer countEndeavoursAfterDate(@Param("date")LocalDateTime date);
	
		//EXEC PERIODS
	@Query("select avg(t.duration.hours * 60 + t.duration.minutes) from Endeavour t")
	Double avgEndeavourWorkload();

	@Query("select stddev(t.duration.hours * 60 + t.duration.minutes) from Endeavour t")
	Double stddevEndeavourWorkload();

	@Query("select min(t.duration.hours * 60 + t.duration.minutes) from Endeavour t")
	Double minEndeavourWorkload();

	@Query("select max(t.duration.hours * 60 + t.duration.minutes) from Endeavour t")
	Double maxEndeavourWorkload();
	
		//EXEC PERIODS
	@Query("select avg(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Endeavour t")
	Double avgEndeavourExecutionPeriod();

	@Query("select stddev(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Endeavour t")
	Double stddevEndeavourExecutionPeriod();

	@Query("select min(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Endeavour t")
	Double minEndeavourExecutionPeriod();

	@Query("select max(to_seconds(t.executionEnd)-to_seconds(t.executionStart)) from Endeavour t")
	Double maxEndeavourExecutionPeriod();
}
