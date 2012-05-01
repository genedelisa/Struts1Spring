<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns="http://www.w3.org/1999/xhtml"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:logic="http://struts.apache.org/tags-logic"
	xmlns:bean="http://struts.apache.org/tags-bean"
	xmlns:htmls="http://struts.apache.org/tags-html" version="2.0">

	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<jsp:directive.page contentType="application/xhtml+xml; charset=utf-8" />
	<jsp:directive.page pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
	<htmls:xhtml />

	<head>
	<title>Login</title>
		<meta http-equiv="Content-Type"
			content="application/xhtml+xml; charset=utf-8" />
	<style type="text/css">
/* move these to an external file when you get them right */
.smallBold {
	color: black;
	font-size: 12;
	font-weight: bold;
	text-align: left;
}

.badInput {
	border-color: red;
	border-style: solid;
	color: red;
	font-size: 12;
	font-weight: bold;
	text-align: left;
}

.error {
	color: red;
	font-size: 18;
	text-align: left;
}
</style>
	</head>

	<body>

	<!-- The errorStyleClass sets the input to that style when the form fails the validation. -->
	<htmls:form action="/login">

		<fieldset><legend>Please log in</legend> <!-- you cannot use the xhtml label tag since the struts tags do no emit the id attribute -->
		<table>
			<tr>
				<td><fmt:message key="label.userName" /></td>
				<td><htmls:text property="userName" styleClass="smallBold"
					errorStyleClass="badInput" maxlength="3" /></td>

				<logic:messagesPresent property="userName">
					<td class="error"><htmls:messages id="error"
						property="userName">
						<bean:write name="error" />
					</htmls:messages></td>
				</logic:messagesPresent>
			</tr>
			<tr>
				<td><fmt:message key="label.password" /></td>
				<td><htmls:password property="password" styleClass="smallBold"
					errorStyleClass="badInput" maxlength="3" /></td>
				<logic:messagesPresent property="password">
					<td class="error"><htmls:messages id="error"
						property="password">
						<bean:write name="error" />
					</htmls:messages></td>
				</logic:messagesPresent>
			</tr>
			<tr>
				<td><htmls:submit value="Login" /></td>
			</tr>
		</table>

		</fieldset>
	</htmls:form>

	</body>
	</html>
</jsp:root>