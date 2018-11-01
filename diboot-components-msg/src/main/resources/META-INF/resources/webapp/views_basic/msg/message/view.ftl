<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="消息管理" loc1url="${ctx.contextPath}/msg/message/" loc2="查看消息" back=true/>
<div class="content">
	<@portlet>
		<@portletTitle title="查看消息">
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