<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${rs}/css/css.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/css/register.css"/>
<%/*-------------------------------------------------------------------------- 依赖库 */%>
<%@include file="/WEB-INF/jsp/include/_crypto_js.jsp" %>
<script language="Javascript" src="${rs}/lib/jquery.js"></script>
<%/*-------------------------------------------------------------------------- jQuery 插件 */%>
<script language="Javascript" src="${rs}/nutz/jqplug/jquery.password.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/jqplug/jquery.password.css"/>
<%/*-------------------------------------------------------------------------- 主文件 */%>
<script language="Javascript" src="${rs}/js/register.js"></script>
</head>
<body 
msg-pwd-unmatch="${msg['pwd.unmatch']}"
msg-pwd-strong="${msg['pwd.strong']}"
msg-pwd-weak="${msg['pwd.weak']}"
>
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
                    <input name="pwd" class="regi-input pwd-new" method="${PWD_ENCRYPT}" type="password">
                    <span class="pwd-tester"></span>
                    <div class="cmt">${msg['u.new.pwd.tip']}</div>
                </td>
            </tr>
            <tr>
                <td>${msg['u.new.pwd.re']}</td>
                <td>
                    <input class="regi-input pwd-re" type="password">
                    <span class="pwd-verifier"></span>
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
                <td class="regi-submit-td diss"><a class="regi-submit">${msg['u.new.submit']}</a></td>
            </tr>
        </table>
    </form>
    <%@include file="/WEB-INF/jsp/include/_msgs.jsp" %>
</body>
</html>