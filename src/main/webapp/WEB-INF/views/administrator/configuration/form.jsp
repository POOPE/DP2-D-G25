<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h2>
	<acme:message code="administrator.configuration.form.title.show"/>
</h2>

<acme:form>
	<acme:form-hidden path="configurationId"/>
	
	<acme:form-textarea code="administrator.configuration.form.spamwords" path="spamWords"/>
	<acme:form-textbox code="administrator.configuration.form.threshold" path="threshold"/>
	
	<acme:form-submit code="administrator.configuration.form.button.update" action="/administrator/configuration/update"/>
</acme:form>
