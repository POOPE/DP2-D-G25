/*
 * Dashboard.java
 *
 * Copyright (c) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.forms;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Task information
	String						countPublicDuties;
	String						countPrivateDuties;
//	Integer						countPublicTasks;
//	Integer						countPrivateTasks;

	String						avgDutyExecutionPeriod;
	String						stddevDutyExecutionPeriod;
	String						minDutyExecutionPeriod;
	String						maxDutyExecutionPeriod;

	String						avgDutyWorkload;
	String						stddevDutyWorkload;
	String						minDutyWorkload;
	String						maxDutyWorkload;

	// september
//	String						foletImportantRatio;
//	String						foletZeroBudgetRatio;
//	Map<String,String> 				foletAvgBudget;
//	Map<String,String>				foletStddevBudget;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
