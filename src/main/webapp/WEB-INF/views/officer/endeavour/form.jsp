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

	<div class="col form-group">
		<div class="row">
			<label id="btn-suggest-execperiod" class="btn btn-secondary">
				<acme:message code="endeavour.suggest.execution" />
			</label>
		</div>
	</div>
	<acme:form-checkbox code="officer.endeavour.form.label.ispublic"
		readonly="true" path="isPublic" />


	<acme:form-textbox readonly="true"
		code="officer.endeavour.form.label.workload" path="duration" />

	<input id="available_duties_input" name="available_duties_input"
		type="hidden">
	<input id="binded_duties_input" name="binded_duties_input"
		type="hidden">



	<div class="form-group">
		<div class="col">
			<div class="row">
				<label for="duties">Duties</label>
			</div>
			<div class="row">
				<div class="col">
					<div class=" card">
						<div class="card-header">Available</div>
						<div id="available_duties_collection" class="card-body">
							<jstl:forEach items="${available_duties}" var="duty"
								varStatus="loop">
								<div class="col m-2">
									<label id="duty_${duty.id}"
										class="btn btn-warning btn-duty-select text-left"> <span><strong><jstl:out
													value="${duty.title}"></jstl:out></strong></span> <jstl:if
											test="${duty.isPublic}">
											<span class="fa fa-lock-open"></span>
										</jstl:if> <jstl:if test="${!duty.isPublic}">
											<span class="fa fa-lock"></span>
										</jstl:if> <br> <jstl:out value="${duty.description}"></jstl:out> <br>
										<acme:message code="officer.endeavour.form.label.workload" />:&nbsp;<jstl:out
											value="${duty.workload}"></jstl:out> <br> <acme:localDateTime
											date="${duty.executionStart}" pattern="${date_format}" />
										&nbsp;-&nbsp; <acme:localDateTime date="${duty.executionEnd}"
											pattern="${date_format}" />
									</label>
								</div>
							</jstl:forEach>
						</div>
					</div>
				</div>
				<div class="col-2">
					<div class="row">
						<div class="col text-center m-1 ">
							<button id="btn-bind-all" type="button" class="btn btn-light">
								<span class="fa fa-angle-double-right"></span>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col text-center m-1 ">
							<button id="btn-bind-selected" type="button"
								class="btn btn-light">
								<span class="fa fa-angle-right"></span>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col text-center m-1">
							<button id="btn-unbind-selected" type="button"
								class="btn btn-light">
								<span class="fa fa-angle-left"></span>
							</button>
						</div>
					</div>
					<div class="row">
						<div class="col text-center m-1 ">
							<button id="btn-unbind-all" type="button" class="btn btn-light">
								<span class="fa fa-angle-double-left"></span>
							</button>
						</div>
					</div>
				</div>
				<div class="col">
					<div class=" card">
						<div class="card-header">Added</div>
						<div id="binded_duties_collection" class="card-body">
							<jstl:forEach items="${binded_duties}" var="duty"
								varStatus="loop">
								<div class="col m-2">
									<label id="duty_${duty.id}"
										class="btn btn-warning btn-duty-select text-left"> <span><strong><jstl:out
													value="${duty.title}"></jstl:out></strong></span> <jstl:if
											test="${duty.isPublic}">
											<span class="fa fa-lock-open"></span>
										</jstl:if> <jstl:if test="${!duty.isPublic}">
											<span class="fa fa-lock"></span>
										</jstl:if> <br> <jstl:out value="${duty.description}"></jstl:out> <br>
										<acme:message code="officer.endeavour.form.label.workload" />:&nbsp;<jstl:out
											value="${duty.workload}"></jstl:out> <br> <acme:localDateTime
											date="${duty.executionStart}" pattern="${date_format}" />
										&nbsp;-&nbsp; <acme:localDateTime date="${duty.executionEnd}"
											pattern="${date_format}" />
									</label>
								</div>
							</jstl:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<acme:form-errors path="duties" />
			</div>
		</div>
	</div>

	<acme:form-submit test="${command == 'create'}"
		code="officer.endeavour.form.button.create"
		action="/officer/endeavour/create" />

	<jstl:if test="${!isPublic}">
		<acme:form-submit test="${command == 'show' }"
			code="officer.endeavour.form.button.add"
			action="/officer/endeavour/publish" />
	</jstl:if>
	<acme:form-submit test="${command == 'show' }"
		code="officer.endeavour.form.button.update"
		action="/officer/endeavour/update" />
	<acme:form-submit test="${command == 'show' }"
		code="officer.endeavour.form.button.delete"
		action="/officer/endeavour/delete" />

	<jstl:if test="${!isPublic}">
		<acme:form-submit test="${command == 'update' }"
			code="officer.endeavour.form.button.add"
			action="/officer/endeavour/publish" />
	</jstl:if>
	<acme:form-submit test="${command == 'update'}"
		code="officer.endeavour.form.button.update"
		action="/officer/endeavour/update" />
	<acme:form-submit test="${command == 'update' }"
		code="officer.endeavour.form.button.delete"
		action="/officer/endeavour/delete" />

	<jstl:if test="${!isPublic}">
		<acme:form-submit test="${command == 'publish' }"
			code="officer.endeavour.form.button.add"
			action="/officer/endeavour/publish" />
	</jstl:if>
	<acme:form-submit test="${command == 'publish'}"
		code="officer.endeavour.form.button.update"
		action="/officer/endeavour/update" />
	<acme:form-submit test="${command == 'publish' }"
		code="officer.endeavour.form.button.delete"
		action="/officer/endeavour/delete" />

	<jstl:if test="${!isPublic}">
		<acme:form-submit test="${command == 'delete' }"
			code="officer.endeavour.form.button.add"
			action="/officer/endeavour/publish" />
	</jstl:if>
	<acme:form-submit test="${command == 'delete'}"
		code="officer.endeavour.form.button.update"
		action="/officer/endeavour/update" />
	<acme:form-submit test="${command == 'delete'}"
		code="officer.endeavour.form.button.delete"
		action="/officer/endeavour/delete" />
	<acme:form-return code="officer.endeavour.form.button.return" />
</acme:form>

<script type="text/javascript"
	src="libraries/endeavour/endeavourform.js"></script>
