<%--
- form.jsp
-
- Copyright (C) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"
	import="acme.framework.helpers.MessageHelper,java.util.Collection, java.util.ArrayList, java.util.Map, javax.servlet.jsp.tagext.JspFragment"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<jstl:set var="date_format">
	<spring:message code="datetime.format" />
</jstl:set>

<acme:form>

	<input id="date_format" type="hidden" value="${date_format}">

	<acme:form-textbox code="officer.endeavour.form.label.executionstart"
		path="executionStart" />
	<acme:form-textbox code="officer.endeavour.form.label.executionend"
		path="executionEnd" />

	<acme:form-textbox readonly="true"
		code="officer.endeavour.form.label.workload" path="duration" />

	<input id="available_duties_input" name="available_duties_input"
		type="hidden">
	<input id="binded_duties_input" name="binded_duties_input"
		type="hidden">

	<!-- display duties -->
	<jstl:forEach items="${binded_duties}" var="duty">
		<div class="form-group">
			<div class="card">
				<div class="card-header">
					<jstl:out value="${duty.title}"></jstl:out>
				</div>
				<div class="card-body">
					<div class="col">
						<div class="row">
							<jstl:out value="${duty.description}"></jstl:out>
						</div>
						<div class="row">
							<acme:message code="officer.endeavour.form.label.workload" />
							:&nbsp;
							<jstl:out value="${duty.workload}"></jstl:out>
						</div>
						<div class="row">
							<acme:localDateTime date="${duty.executionStart}"
								pattern="${date_format}" />
							&nbsp;-&nbsp;
							<acme:localDateTime date="${duty.executionEnd}"
								pattern="${date_format}" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</jstl:forEach>


	<acme:form-return code="officer.endeavour.form.button.return" />
</acme:form>

