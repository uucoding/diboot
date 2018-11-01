<#assign plugins = ['editormd', 'lightbox', 'datetimepicker']/>
<#assign className = 'testModel' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="测试模型管理" loc1url="${ctx.contextPath}/testModel/" loc2="更新测试模型" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="更新测试模型" icon="fa-edit"></@portletTitle>
		<#include "_form.ftl">
	</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>