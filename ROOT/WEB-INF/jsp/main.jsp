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
        <a class="sky-info-proj">${msg['sky.uinfo.projs']}</a>
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
<script language="Javascript" src="${rs}/lib/jquery.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.log.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.obj.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.time.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.url.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.str.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.sys.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.be.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.json.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.ajax.js"></script>
<script language="Javascript" src="${rs}/nutz/core/z.keyboard.js"></script>
<script language="Javascript" src="${rs}/nutz/core/zui.js"></script>
<%/*-------------------------------------------------------------------------- jQuery 插件 */%>
<script language="Javascript" src="${rs}/nutz/jqplug/jquery.password.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/jqplug/jquery.password.css"/>
<script language="Javascript" src="${rs}/nutz/jqplug/jquery.floatdiv.js"></script>
<%/*-------------------------------------------------------------------------- NutzUI */%>
<script language="Javascript" src="${rs}/nutz/ui-js/ui.tabs.h.js"></script>
<script language="Javascript" src="${rs}/nutz/ui-js/ui.avatas.js"></script>
<script language="Javascript" src="${rs}/nutz/ui-js/ui.usr.pwd.js"></script>
<script language="Javascript" src="${rs}/nutz/ui-js/ui.usr.profile.js"></script>

<link rel="stylesheet" type="text/css" href="${rs}/nutz/ui-css/ui.tabs.h.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/ui-css/ui.avatas.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/ui-css/ui.usr.pwd.css"/>
<link rel="stylesheet" type="text/css" href="${rs}/nutz/ui-css/ui.usr.profile.css"/>
<%/*-------------------------------------------------------------------------- zTask 特殊UI */%>
<script language="Javascript" src="${rs}/js/ui.ztask.usr.js"></script>

<link rel="stylesheet" type="text/css" href="${rs}/css/ui.ztask.usr.css"/>
<%/*-------------------------------------------------------------------------- 主文件 */%>
<script language="Javascript" src="${rs}/js/main.js"></script>
</body>
</html>