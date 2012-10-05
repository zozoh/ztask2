<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${rs}/css/ztask/entry.css"/>
</head>
<body>
    <form action="${base}/u/do/login" method="POST">
        <table>
            <tr>
                <td>${msg['login.unm']}</td>
                <td><input name="nm" class="login-input" value="zozoh"></td>
            </tr>
            <tr>
                <td>${msg['login.pwd']}</td>
                <td><input name="pwd" class="login-input" type="password" value="123456"></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <input type="submit" value="${msg['login.signup']}">
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><a href="${base}/u/register">${msg['u.new.title']}</a></td>
            </tr>
        </table>
    </form>
</body>
</html>