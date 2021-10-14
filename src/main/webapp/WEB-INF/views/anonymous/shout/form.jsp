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
	<!-- shout -->
	<acme:form-hidden path="moment" />
	<acme:form-textbox code="anonymous.shout.form.label.author"
		placeholder="Author" path="author" />
	<acme:form-textarea code="anonymous.shout.form.label.text"
		placeholder="Description" path="text" />
	<acme:form-textbox code="anonymous.shout.form.label.info"
		placeholder="https://yourlink.com" path="info" />

</acme:form>