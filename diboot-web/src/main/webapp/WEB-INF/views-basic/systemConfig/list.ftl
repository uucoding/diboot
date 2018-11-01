<#assign features=["openAsRedirect","C","U","D","R","L","paging"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="系统配置管理" loc1url="${ctx.contextPath}/systemConfig/" loc2="系统配置记录" />
<div class="content">
	<@portlet>
		<@portletTitle title="系统配置记录">
			<#if features?seq_contains("C")>
				<@actions>
					<a class="btn btn-sm blue" href="${ctx.contextPath}/systemConfig/create">
                        <i class="fa fa-plus"></i> 新建系统配置
                    </a>
				</@actions>
			</#if>
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