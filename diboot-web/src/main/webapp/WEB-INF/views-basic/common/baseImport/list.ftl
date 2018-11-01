<#assign plugins=[] />
<#assign features=["L","R"] />
<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="${modelName}管理" loc1url="${ctx.contextPath}/${indexPageUrl}/" loc2="${modelName}记录" />
	<div class="content">
		<@portlet>
			<@portletTitle title="${modelName}数据上传">
				<#if exampleFileUrl??>
					<@actions>
						<a class="btn font-white" href="${ctx.contextPath}/${exampleFileUrl}">
							<i class="fa fa-download"></i> 下载示例文件
						</a>
					</@actions>
				</#if>
			</@portletTitle>
			<@portletBody>
				<#include "_upload_form.ftl" />
			</@portletBody>
		</@portlet><#-- END PAGE BODY -->

		<@portlet>
			<@portletTitle title="${modelName}记录">
			</@portletTitle>
			<@portletBody>
				<#include "list_page.ftl">
				<#include "../../include/pagination.ftl">
			</@portletBody>
		</@portlet><#-- END PAGE BODY -->
	</div><#-- END CONTENT BODY -->
</@wrapper>
<#-- 绑定事件 -->
<script type="text/javascript">
    $(document).ready(function(){
        $('.btn-action').on("click", function(){
            $("#uploadForm").attr("action", "${ctx.contextPath}/${indexPageUrl}/"+$(this).data("action"));
            $("#uploadForm").submit();
        });
    });
</script>
</body>
</html>