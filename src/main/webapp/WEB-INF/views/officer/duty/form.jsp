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

<%@page language="java" import="acme.framework.helpers.MessageHelper"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="officer.duty.form.label.title" path="title" />
	<acme:form-textbox code="officer.duty.form.label.description" path="description" />
	
	<acme:form-textbox code="officer.duty.form.label.executionstart" path="executionStart" />
	<acme:form-textbox code="officer.duty.form.label.executionend" path="executionEnd" />
	
	<acme:form-textbox code="officer.duty.form.label.workload" path="workload" />
	
	<acme:form-textbox code="officer.duty.form.label.link" path="link" />
	
	<acme:form-submit test="${command == 'show' }"
		code="officer.duty.form.button.update" action="/officer/duty/update" />
	<acme:form-submit test="${command == 'show' }"
		code="officer.duty.form.button.delete" action="/officer/duty/delete" />
	<acme:form-submit test="${command == 'create'}"
		code="officer.duty.form.button.create" action="/officer/duty/create" />
	<acme:form-submit test="${command == 'update'}"
		code="officer.duty.form.button.update" action="/officer/duty/update" />
	<acme:form-submit test="${command == 'delete'}"
		code="officer.duty.form.button.delete" action="/officer/duty/delete" />
	<acme:form-return code="officer.duty.form.button.return" />
</acme:form>