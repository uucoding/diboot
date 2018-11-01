<#assign plugins = ['summernote']/>
<#assign features=["C","R","U","D","L"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="测试富文本管理" loc1url="${ctx.contextPath}/testRichtext/" loc2="测试富文本记录" />
<div class="content">
	<@portlet>
		<@portletTitle title="测试富文本记录">
			<#if features?seq_contains("C")>
				<@actions>
					<a class="btn btn-sm blue" href="${ctx.contextPath}/testRichtext/create">
                        <i class="fa fa-plus"></i> 新建测试富文本
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