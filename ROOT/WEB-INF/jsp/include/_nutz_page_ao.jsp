<%/*
   * 将 JSP 设置的 NUTZ_PAGE_AO 字符串，传递给 JS
   */%>
<c:if test="${NUTZ_PAGE_AO != null}">
<script languange="Javascript">
var window.NUTZ_PAGE_AO = "${NUTZ_PAGE_AO}";
</script>
</c:if>