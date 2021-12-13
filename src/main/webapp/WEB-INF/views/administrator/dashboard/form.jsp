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

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.countPublicEndeavours" /></th>
		<td><jstl:if test="${countPublicEndeavours != null}">
				<acme:print value="${countPublicEndeavours}" />
			</jstl:if></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.countPrivateEndeavours" /></th>
		<td><jstl:if test="${countPrivateEndeavours != null}">
				<acme:print value="${countPrivateEndeavours}" />
			</jstl:if></td>
	</tr>
</table>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.countFinishedEndeavours" /></th>
		<td><jstl:if test="${countFinishedEndeavours != null}">
				<acme:print value="${countFinishedEndeavours}" />
			</jstl:if></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.countUnfinishedEndeavours" /></th>
		<td><jstl:if test="${countUnfinishedEndeavours != null}">
				<acme:print value="${countUnfinishedEndeavours}" />
			</jstl:if></td>
	</tr>
</table>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.avgEndeavourExecutionPeriod" /></th>
		<td><jstl:if test="${avgEndeavourExecutionPeriod != null}">
				<acme:print value="${avgEndeavourExecutionPeriod}" />
			</jstl:if></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.stddevEndeavourExecutionPeriod" /></th>
		<td><jstl:if test="${stddevEndeavourExecutionPeriod != null}">
				<acme:print value="${stddevEndeavourExecutionPeriod}" />
			</jstl:if></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.minEndeavourExecutionPeriod" /></th>
		<td><jstl:if test="${minEndeavourExecutionPeriod != null}">
				<acme:print value="${minEndeavourExecutionPeriod}" />
			</jstl:if></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.maxEndeavourExecutionPeriod" /></th>
		<td><jstl:if test="${maxEndeavourExecutionPeriod != null}">
				<acme:print value="${maxEndeavourExecutionPeriod}" />
			</jstl:if></td>
	</tr>
</table>

<table class="table table-sm">
	<caption></caption>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.avgEndeavourWorkload" /></th>
		<td><jstl:if test="${avgEndeavourWorkload != null}">
				<acme:print value="${avgEndeavourWorkload}" />
			</jstl:if></td>

	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.stddevEndeavourWorkload" /></th>
		<td><jstl:if test="${stddevEndeavourWorkload != null}">
				<acme:print value="${stddevEndeavourWorkload}" />
			</jstl:if></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.minEndeavourWorkload" /></th>
		<td><jstl:if test="${minEndeavourWorkload != null}">
				<acme:print value="${minEndeavourWorkload}" />
			</jstl:if></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="administrator.dashboard.form.label.endeavours.maxEndeavourWorkload" /></th>
		<td><jstl:if test="${maxEndeavourWorkload != null}">
				<acme:print value="${maxEndeavourWorkload}" />
			</jstl:if></td>
	</tr>
</table>

