<%--
- form.jsp
-
- Copyright (c) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message
		code="administrator.dashboard.form.title.general-indicators" />
</h2>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-public-tasks" /></th>
		<td><acme:print value="${countPublicDuties}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-private-tasks" /></th>
		<td><acme:print value="${countPrivateDuties}" /></td>
	</tr>
</table>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-average-execution-periods" />
		</th>
		<td><acme:print value="${avgDutyExecutionPeriod}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-deviation-execution-periods" />
		</th>
		<td><acme:print value="${stddevDutyExecutionPeriod}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-minimum-execution-periods" />
		</th>
		<td><acme:print value="${minDutyExecutionPeriod}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-maximum-execution-periods" />
		</th>
		<td><acme:print value="${maxDutyExecutionPeriod}" /></td>
	</tr>
</table>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-average-workloads" />
		</th>
		<td><acme:print value="${avgDutyWorkload}" />
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-deviation-workloads" />
		</th>
		<td><acme:print value="${stddevDutyWorkload}" />
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-minimum-workloads" />
		</th>
		<td><acme:print value="${minDutyWorkload}" />
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.number-maximum-workloads" />
		</th>
		<td><acme:print value="${maxDutyWorkload}" />
	</tr>
</table>

