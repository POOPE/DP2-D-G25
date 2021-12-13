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

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.BasicDuration;
import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorDashboardRepository	repository;

	// AbstractShowService<Administrator, Dashboard> interface ----------------

	private Dashboard							dash;


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
			"maxDutyWorkload", "countPublicEndeavours", "countPrivateEndeavours", "countFinishedEndeavours", "countUnfinishedEndeavours", "avgEndeavourExecutionPeriod", "stddevEndeavourExecutionPeriod", "minEndeavourExecutionPeriod",
			"maxEndeavourExecutionPeriod", "avgEndeavourWorkload", "stddevEndeavourWorkload", "minEndeavourWorkload", "maxEndeavourWorkload");

	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;
		this.setDutyStats();
		this.setEndeavourStats();

		return this.dash;
	}

	public String secondsToDaysAndHours(final Double time) {
		return ((int) (time / 24 / 60)) + "d " + ((int) (time / 60 % 24)) + "hrs";
	}

	public String minutesToHoursAndMinutes(final Double time) {
		return ((int) (time / 60)) + ":" + ((int) (time % 60));
	}

	private void setEndeavourStats() {
		//endeavour counts public
		final Integer countPublicEndeavours = this.repository.countPublicEndeavours();
		if (countPublicEndeavours != null) {
			this.dash.setCountPublicEndeavours(String.valueOf(countPublicEndeavours));
		} else {
			this.dash.setCountPublicEndeavours("N/A");
		}
		final Integer countPrivateEndeavours = this.repository.countPrivateEndeavours();
		if (countPrivateEndeavours != null) {
			this.dash.setCountPrivateEndeavours(String.valueOf(countPrivateEndeavours));
		} else {
			this.dash.setCountPrivateEndeavours("N/A");
		}
		//count endeavour private
		final Integer countFinishedEndeavours = this.repository.countEndeavoursBeforeDate(LocalDateTime.now());
		if (countFinishedEndeavours != null) {
			this.dash.setCountFinishedEndeavours(String.valueOf(countFinishedEndeavours));
		} else {
			this.dash.setCountFinishedEndeavours("N/A");
		}
		final Integer countUnfinishedEndeavours = this.repository.countEndeavoursAfterDate(LocalDateTime.now());
		if (countUnfinishedEndeavours != null) {
			this.dash.setCountUnfinishedEndeavours(String.valueOf(countUnfinishedEndeavours));
		} else {
			this.dash.setCountUnfinishedEndeavours("N/A");
		}

		//endeavour execution period
		Double avgEndeavourExecutionPeriod = this.repository.avgEndeavourExecutionPeriod();
		if (avgEndeavourExecutionPeriod != null) {
			avgEndeavourExecutionPeriod = avgEndeavourExecutionPeriod / (60);
			this.dash.setAvgEndeavourExecutionPeriod(this.secondsToDaysAndHours(avgEndeavourExecutionPeriod));
		} else {
			this.dash.setAvgEndeavourExecutionPeriod("N/A");
		}
		Double stddevEndeavourExecutionPeriod = this.repository.stddevEndeavourExecutionPeriod();
		if (stddevEndeavourExecutionPeriod != null) {
			stddevEndeavourExecutionPeriod = stddevEndeavourExecutionPeriod / (60);
			this.dash.setStddevEndeavourExecutionPeriod(this.secondsToDaysAndHours(stddevEndeavourExecutionPeriod));
		} else {
			this.dash.setStddevEndeavourExecutionPeriod("N/A");
		}
		Double minEndeavourExecutionPeriod = this.repository.minEndeavourExecutionPeriod();
		if (minEndeavourExecutionPeriod != null) {
			minEndeavourExecutionPeriod = minEndeavourExecutionPeriod / (60);
			this.dash.setMinEndeavourExecutionPeriod(this.secondsToDaysAndHours(minEndeavourExecutionPeriod));
		} else {
			this.dash.setMinEndeavourExecutionPeriod("N/A");
		}
		Double maxEndeavourExecutionPeriod = this.repository.maxEndeavourExecutionPeriod();
		if (maxEndeavourExecutionPeriod != null) {
			maxEndeavourExecutionPeriod = maxEndeavourExecutionPeriod / (60);
			this.dash.setMaxEndeavourExecutionPeriod(this.secondsToDaysAndHours(maxEndeavourExecutionPeriod));
		} else {
			this.dash.setMaxEndeavourExecutionPeriod("N/A");
		}
		//endeavour workload
		final Double avgEndeavourWorkload = this.repository.avgEndeavourWorkload();
		if (avgEndeavourWorkload != null) {
			final BasicDuration inMinutes = new BasicDuration(null, avgEndeavourWorkload.intValue());
			this.dash.setAvgEndeavourWorkload(inMinutes.toString());
		} else {
			this.dash.setAvgEndeavourWorkload("N/A");
		}
		final Double stddevEndeavourWorkload = this.repository.stddevEndeavourWorkload();
		if (stddevEndeavourWorkload != null) {
			final BasicDuration inMinutes = new BasicDuration(null, stddevEndeavourWorkload.intValue());
			this.dash.setStddevEndeavourWorkload(inMinutes.toString());
		} else {
			this.dash.setStddevEndeavourWorkload("N/A");
		}
		final Double minEndeavourWorkload = this.repository.minEndeavourWorkload();
		if (minEndeavourWorkload != null) {
			final BasicDuration inMinutes = new BasicDuration(null, minEndeavourWorkload.intValue());
			this.dash.setMinEndeavourWorkload(inMinutes.toString());
		} else {
			this.dash.setMinEndeavourWorkload("N/A");
		}
		final Double maxEndeavourWorkload = this.repository.maxEndeavourWorkload();
		if (maxEndeavourWorkload != null) {
			final BasicDuration inMinutes = new BasicDuration(null, maxEndeavourWorkload.intValue());
			this.dash.setMaxEndeavourWorkload(inMinutes.toString());
		} else {
			this.dash.setMaxEndeavourWorkload("N/A");
		}
	}

	private void setDutyStats() {
		// Duty information
		this.dash = new Dashboard();

		this.dash.setCountPublicDuties(this.repository.countPublicDuties().toString());
		this.dash.setCountPrivateDuties(this.repository.countPrivateDuties().toString());

		//duty execution period
		Double avgDutyExecutionPeriod = this.repository.avgDutyExecutionPeriod();
		if (avgDutyExecutionPeriod != null) {
			avgDutyExecutionPeriod = avgDutyExecutionPeriod / (60);
			this.dash.setAvgDutyExecutionPeriod(this.secondsToDaysAndHours(avgDutyExecutionPeriod));
		} else {
			this.dash.setAvgDutyExecutionPeriod("N/A");
		}
		Double stddevDutyExecutionPeriod = this.repository.stddevDutyExecutionPeriod();
		if (stddevDutyExecutionPeriod != null) {
			stddevDutyExecutionPeriod = stddevDutyExecutionPeriod / (60);
			this.dash.setStddevDutyExecutionPeriod(this.secondsToDaysAndHours(stddevDutyExecutionPeriod));
		} else {
			this.dash.setStddevDutyExecutionPeriod("N/A");
		}
		Double minDutyExecutionPeriod = this.repository.minDutyExecutionPeriod();
		if (minDutyExecutionPeriod != null) {
			minDutyExecutionPeriod = minDutyExecutionPeriod / (60);
			this.dash.setMinDutyExecutionPeriod(this.secondsToDaysAndHours(minDutyExecutionPeriod));
		} else {
			this.dash.setMinDutyExecutionPeriod("N/A");
		}
		Double maxDutyExecutionPeriod = this.repository.maxDutyExecutionPeriod();
		if (maxDutyExecutionPeriod != null) {
			maxDutyExecutionPeriod = maxDutyExecutionPeriod / (60);
			this.dash.setMaxDutyExecutionPeriod(this.secondsToDaysAndHours(maxDutyExecutionPeriod));
		} else {
			this.dash.setMaxDutyExecutionPeriod("N/A");
		}

		//duty workload
		final Double avgDutyWorkload = this.repository.avgDutyWorkload();
		if (avgDutyWorkload != null) {
			this.dash.setAvgDutyWorkload(this.minutesToHoursAndMinutes(avgDutyWorkload));
		} else {
			this.dash.setAvgDutyWorkload("N/A");
		}
		final Double stddevDutyWorkload = this.repository.stddevDutyWorkload();
		if (stddevDutyWorkload != null) {
			this.dash.setStddevDutyWorkload(this.minutesToHoursAndMinutes(stddevDutyWorkload));
		} else {
			this.dash.setStddevDutyWorkload("N/A");
		}
		final Double minDutyWorkload = this.repository.minDutyWorkload();
		if (minDutyWorkload != null) {
			this.dash.setMinDutyWorkload(this.minutesToHoursAndMinutes(minDutyWorkload));
		} else {
			this.dash.setMinDutyWorkload("N/A");
		}
		final Double maxDutyWorkload = this.repository.maxDutyWorkload();
		if (maxDutyWorkload != null) {
			this.dash.setMaxDutyWorkload(this.minutesToHoursAndMinutes(maxDutyWorkload));
		} else {
			this.dash.setMaxDutyWorkload("N/A");
		}
	}

}
