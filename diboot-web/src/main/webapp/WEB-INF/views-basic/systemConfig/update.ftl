<#assign className = 'systemConfig' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="系统配置管理" loc1url="${ctx.contextPath}/systemConfig/" loc2="更新系统配置" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="更新系统配置" icon="fa-edit"></@portletTitle>
		<#include "_form.ftl">
	</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/vue.js"></script>
<script src="${ctx.contextPath}/static/js/views/system-config.js" type="text/javascript"></script>
</body>
</html>