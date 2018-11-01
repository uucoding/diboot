<#assign plugins=[] />
<#assign features=["L","R"] />
<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="短信记录管理" loc1url="${ctx.contextPath}/message/" loc2="短信记录" />
	<div class="content">
		<@portlet>
			<@portletTitle title="短信记录">
				<#if features?seq_contains("C")>
					<@actions>
						<a class="btn btn-sm blue" href="${ctx.contextPath}/message/create">
							<i class="fa fa-plus"></i> 新建消息
						</a>
					</@actions>
				</#if>
			</@portletTitle>
			<@portletBody>
				<#include "list_page.ftl">
				<#include "../../include/pagination.ftl">
			</@portletBody>
		</@portlet><#-- END PAGE BODY -->
	</div><#-- END CONTENT BODY -->
</@wrapper>
</body>
</html>