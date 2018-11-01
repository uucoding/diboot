<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="定时任务管理" loc1url="${ctx.contextPath}/timerTask/" loc2="更新定时任务" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="更新定时任务" icon="fa-edit"></@portletTitle>
		<#include "_form.ftl">
	</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>