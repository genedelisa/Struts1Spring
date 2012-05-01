<?xml version="1.0" encoding="UTF-8"?>
<jsp:root xmlns="http://www.w3.org/1999/xhtml"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
	xmlns:bean="http://struts.apache.org/tags-bean"
	xmlns:htmls="http://struts.apache.org/tags-html"
	xmlns:logic="http://struts.apache.org/tags-logic" version="2.0">

	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" />
	<jsp:directive.page contentType="application/xhtml+xml; charset=utf-8" />
	<jsp:directive.page pageEncoding="UTF-8" />

	<html xmlns="http://www.w3.org/1999/xhtml">
	<htmls:xhtml />
	<head>
	<title>Oops</title>
	<meta http-equiv="Content-Type"
		content="application/xhtml+xml; charset=utf-8" />
	<style type="text/css">
.error {
	color: red;
	font-size: 18;
	text-align: left;
}
</style>
	</head>

	<body>

	<logic:messagesPresent>
		<table border="0">
			<htmls:messages id="errorMessage">
				<tr>
					<td class="error"><bean:write name="errorMessage" /></td>
				</tr>
			</htmls:messages>
		</table>
	</logic:messagesPresent>


	</body>
	</html>
</jsp:root>
