<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${rs}/css/ztask/register.css"/>
<script language="Javascript" src="${rs}/js/lib/jquery.js"></script>
<script language="Javascript" src="${rs}/js/ztask/register.js"></script>
</head>
<body>
    <form action="${base}/u/do/reg" method="POST">
        <table>
            <tr>
                <td>${msg['u.new.lnm']}</td>
                <td>
                    <input name="lnm" class="regi-input">
                    <div class="cmt">${msg['u.new.lnm.tip']}</div>
                </td>
            </tr>
            <tr>
                <td>${msg['u.new.pwd']}</td>
                <td>
                    <input name="pwd" class="regi-input">
                    <div class="cmt">${msg['u.new.pwd.tip']}</div>
                </td>
            </tr>
            <tr>
                <td>${msg['u.new.pwd.re']}</td>
                <td>
                    <input class="regi-input repwd">
                </td>
            </tr>
            <tr>
                <td>${msg['u.new.email']}</td>
                <td>
                    <input name="email" class="regi-input">
                    <div class="cmt">${msg['u.new.email.tip']}</div>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td><a class="regi-submit">${msg['u.new.submit']}</a></td>
            </tr>
        </table>
    </form>
</body>
</html>