<#assign features=["C","R","U","D","L","batch","rest","export","openAsDialog"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="定时任务管理" loc1url="${ctx.contextPath}/timerTask/" loc2="查看定时任务" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="查看定时任务" icon="fa-search-plus">
			<@actions>
			</@actions>
		</@portletTitle>
		<@portletBody>
			<#include "_view.ftl">
		</@portletBody>
	</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>