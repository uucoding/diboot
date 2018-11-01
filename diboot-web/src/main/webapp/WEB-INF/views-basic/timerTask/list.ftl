<#assign plugins=["datepicker", "datetimepicker"] />
<#assign features=["C","U","D","R","L","paging"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="定时任务管理" loc1url="${ctx.contextPath}/timerTask/" loc2="定时任务记录" />
<div class="content">
	<@portlet>
		<@portletTitle title="添加定时任务" icon="fa-plus">
		</@portletTitle>
		<#include "_form.ftl">
	</@portlet><#-- END PAGE BODY -->

	<@portlet>
		<@portletTitle title="定时任务记录">
		</@portletTitle>
		<@portletBody>
			<#include "list_page.ftl">
			<#include "../include/pagination.ftl">
		</@portletBody>
	</@portlet><#-- END PAGE BODY -->
</div><#-- END CONTENT BODY -->
</@wrapper>
</body>
</html>