<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<%@ page import="java.io.PrintStream"%>
<%@ page import="org.nutz.lang.Lang"%>
<%@ page import="java.lang.Exception"%>
<%@ page import="org.nutz.web.WebException"%>
<html>
    <head>
        <title>${msg['err.page.title']}</title>
        <link rel="stylesheet" type="text/css" href="${rs}/css/css.css"/>
        <link rel="stylesheet" type="text/css" href="${rs}/css/err.css">
    </head>
    <body>
        ${obj.reason}
        <h3><b>${msg[obj.key]}</b> <c:if test="${obj.reason != null}"><em>"${msg[obj.reason]}"</em></c:if></h3>
        <pre class="err_detail"><%
        	Throwable e = (Throwable) request.getAttribute("obj");
        	StringBuilder sb = new StringBuilder();
        	PrintStream ps = new PrintStream(Lang.ops(sb),true);
        	e.printStackTrace(ps);
        	pageContext.getOut().print(sb);
        %></pre>
    </body>
</html>