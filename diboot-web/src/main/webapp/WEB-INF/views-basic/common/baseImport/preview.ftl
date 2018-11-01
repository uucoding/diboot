<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="${modelName}管理" loc1url="${ctx.contextPath}/${indexPageUrl}/"
	loc2="上传数据预览" back=true/>
	<div class="content">
		<@portlet>
			<@portletTitle title="上传数据预览">
				<@actions>
					<#if !errors?exists && dataList??>
						<form action="${ctx.contextPath}/${indexPageUrl}/previewSave" method="post">
							<input type="hidden" name="importUid" value="${importUid!''}">
							<button class="btn btn-success" type="submit"><i class="fa fa-upload"></i> 上传数据</button>
						</form>
					<#else>
						<a class="btn font-white" href="${ctx.contextPath}/${indexPageUrl}/"> 返回重新上传 </a>
					</#if>
				</@actions>
			</@portletTitle>
			<@portletBody>
				<#assign isPreview=true />
				<#include "_view.ftl">
			</@portletBody>
		</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>