<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${rs}/css/css.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/css/entry.css"/>
<%/*-------------------------------------------------------------------------- 依赖库 */%>
<%@include file="/WEB-INF/jsp/include/_crypto_js.jsp" %>
<script language="Javascript" src="${rs}/lib/jquery.js"></script>
<%/*-------------------------------------------------------------------------- jQuery 插件 */%>
<script language="Javascript" src="${rs}/nutz/jqplug/jquery.password.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/jqplug/jquery.password.css"/>
<%/*-------------------------------------------------------------------------- 主文件 */%>
<script language="Javascript" src="${rs}/js/entry.js"></script>
</head>
<body>
    <form action="${base}/u/do/login" method="POST">
        <table cellspacing="2" cellpadding="6">
            <tr>
                <td class="entry-fnm">${msg['login.unm']}</td>
                <td><input name="nm" class="login-input" value="zozoh"></td>
            </tr>
            <tr>
                <td class="entry-fnm">${msg['login.pwd']}</td>
                <td><input name="pwd" class="login-input" type="password" method="${PWD_ENCRYPT}" value="123456"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><a class="entry-btn diss">${msg['login.signup']}</a></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><a href="${base}/u/register">${msg['u.new.title']}</a></td>
            </tr>
        </table>
    </form>
</body>
</html>