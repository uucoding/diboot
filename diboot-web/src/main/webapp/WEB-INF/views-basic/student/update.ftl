<#assign plugins = ['editormd', 'lightbox', 'datetimepicker']/>
<#assign className = 'student' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="学生管理" loc1url="${ctx.contextPath}/student/" loc2="更新学生" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="更新学生" icon="fa-edit"></@portletTitle>
		<#include "_form.ftl">
	</@portlet><#-- END PAGE BODY -->
	</div>
</@wrapper>
</body>
</html>