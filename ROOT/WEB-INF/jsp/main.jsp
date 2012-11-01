<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<%@include file="/WEB-INF/jsp/include/_nutz_page_ao.jsp" %>
<%@include file="/WEB-INF/jsp/include/_crypto_js.jsp" %>
<link rel="stylesheet" type="text/css" href="${rs}/css/css.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/css/main.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/css/keyboard.info.css"/>
</head>
<body nutz-url-prefix="${base}" nutz-me-name="${me.loginName}" nutz-usr-pwd-method="${PWD_ENCRYPT}">
<div id="sky" class="cfloat">
    <div id="skyMenu"></div>
    <div id="skyInfo" css-width="300">
        <a href="${base}/u/do/logout">${msg['sky.uinfo.logout']}</a>
        <a class="sky-info-me">${msg['sky.uinfo.me']}</a>
        <a class="sky-info-dmn">${msg['sky.uinfo.dmn']}</a>
        <a><b>${me.loginName}</b><em id="skyInfoMsg">0</em></a>
    </div>
</div>
<div id="arena"></div>
<ul class="keyboard-info">
	<li class="keyboard-info-ctrl">CTL</li>
	<li class="keyboard-info-alt">ALT</li>
	<li class="keyboard-info-shift">SFT</li>
</ul>
<%@include file="/WEB-INF/jsp/include/_msgs.jsp" %>
<%/*-------------------------------------------------------------------------- 依赖库 */%>
${links}
</body>
</html>