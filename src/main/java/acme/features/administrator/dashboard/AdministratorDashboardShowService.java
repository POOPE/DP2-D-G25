/*
 * AdministratorDashboardShowService.java
 *
 * Copyright (c) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "countPublicDuties", "countPrivateDuties", "avgDutyExecutionPeriod", "stddevDutyExecutionPeriod", "minDutyExecutionPeriod", "maxDutyExecutionPeriod", "avgDutyWorkload", "stddevDutyWorkload", "minDutyWorkload",
			"maxDutyWorkload");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		// Task information
		final Dashboard result = new Dashboard();
		
		result.setCountPublicDuties(this.repository.countPublicDuties().toString());
		result.setCountPrivateDuties(this.repository.countPrivateDuties().toString());
		
		//duty execution period
		Double avgDutyExecutionPeriod = this.repository.avgDutyExecutionPeriod();
		if ( avgDutyExecutionPeriod != null) {
			avgDutyExecutionPeriod=avgDutyExecutionPeriod/(60);
			result.setAvgDutyExecutionPeriod(this.secondsToDaysAndHours(avgDutyExecutionPeriod));
		} else {
			result.setAvgDutyExecutionPeriod("N/A");
		}
		Double stddevDutyExecutionPeriod = this.repository.stddevDutyExecutionPeriod();
		if (stddevDutyExecutionPeriod != null) {
			stddevDutyExecutionPeriod=stddevDutyExecutionPeriod/(60);
			result.setStddevDutyExecutionPeriod(this.secondsToDaysAndHours(stddevDutyExecutionPeriod));
		} else {
			result.setStddevDutyExecutionPeriod("N/A");
		}
		Double minDutyExecutionPeriod = this.repository.minDutyExecutionPeriod();
		if (minDutyExecutionPeriod != null) {
			minDutyExecutionPeriod=minDutyExecutionPeriod/(60);
			result.setMinDutyExecutionPeriod(this.secondsToDaysAndHours(minDutyExecutionPeriod));
		} else {
			result.setMinDutyExecutionPeriod("N/A");
		}
		Double maxDutyExecutionPeriod = this.repository.maxDutyExecutionPeriod();
		if (maxDutyExecutionPeriod != null) {
			maxDutyExecutionPeriod=maxDutyExecutionPeriod/(60);
			result.setMaxDutyExecutionPeriod(this.secondsToDaysAndHours(maxDutyExecutionPeriod));
		} else {
			result.setMaxDutyExecutionPeriod("N/A");
		}
		
		
		//duty workload
		final Double avgDutyWorkload = this.repository.avgDutyWorkload();
		if ( avgDutyWorkload != null) {
			result.setAvgDutyWorkload(this.minutesToHoursAndMinutes(avgDutyWorkload));
		} else {
			result.setAvgDutyWorkload("N/A");
		}
		final Double stddevDutyWorkload = this.repository.stddevDutyWorkload();
		if ( avgDutyWorkload != null) {
			result.setStddevDutyWorkload(this.minutesToHoursAndMinutes(stddevDutyWorkload));
		} else {
			result.setStddevDutyWorkload("N/A");
		}
		final Double minDutyWorkload = this.repository.minDutyWorkload();
		if ( avgDutyWorkload != null) {
			result.setMinDutyWorkload(this.minutesToHoursAndMinutes(minDutyWorkload));
		} else {
			result.setMinDutyWorkload("N/A");
		}
		final Double maxDutyWorkload = this.repository.maxDutyWorkload();
		if ( avgDutyWorkload != null) {
			result.setMaxDutyWorkload(this.minutesToHoursAndMinutes(maxDutyWorkload));
		} else {
			result.setMaxDutyWorkload("N/A");
		}

		return result;
	}

	public String secondsToDaysAndHours(final Double time) {
		return ((int)(time / 24 / 60 )) + "d " + ((int)(time / 60 % 24)) + "hrs";
	}
	
	public String minutesToHoursAndMinutes(final Double time) {
		return ((int)(time / 60 )) + ":" + ((int)(time % 60  ));
	}

}
