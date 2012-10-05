<%@include file="/WEB-INF/jsp/include/_setup.jsp" %>
<html>
<head>
<%@include file="/WEB-INF/jsp/include/_nutz_page_ao.jsp" %>
<%/*-------------------------------------------------------------------------- 依赖库 -*/%>
<script language="Javascript" src="${rs}/js/lib/jquery.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.log.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.obj.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.time.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.url.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.str.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.sys.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/z.be.js"></script>
<script language="Javascript" src="${rs}/js/nutz/core/zui.js"></script>
<%/*-------------------------------------------------------------------------- 通用UI -*/%>
<script language="Javascript" src="${rs}/js/nutz/ui-js/ui.tabs.h.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/css/nutz/ui.tabs.h.css"/>
<script language="Javascript" src="${rs}/js/nutz/ui-js/ui.avatas.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/css/nutz/ui.avatas.css"/>
<script language="Javascript" src="${rs}/js/nutz/ui-js/ui.usr.general.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/css/nutz/ui.usr.general.css"/>
<%/*-------------------------------------------------------------------------- 特殊UI -*/%>
<script language="Javascript" src="${rs}/js/ztask/ui.ztask.usr.js"></script>
<link rel="stylesheet" type="text/css" href="${rs}/css/ztask/ui.ztask.usr.css"/>
<%/*-------------------------------------------------------------------------- 主文件 -*/%>
<script language="Javascript" src="${rs}/js/ztask/main.js"></script>

<link rel="stylesheet" type="text/css" href="${rs}/css/ztask/main.css"/>
</head>
<body nutz-url-prefix="${base}" nutz-me-name="${me.loginName}">
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
<%@include file="/WEB-INF/jsp/include/_msgs.jsp" %>
</body>
</html>