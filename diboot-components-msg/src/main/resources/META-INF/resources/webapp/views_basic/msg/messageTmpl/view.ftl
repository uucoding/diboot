<#assign features=["L","C","R","U","D"] />
<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="短信模板管理" loc1url="${ctx.contextPath}/msg/messageTmpl/" loc2="查看短信模板" back=true/>
	<div class="content">
		<@portlet>
			<@portletTitle title="查看短信模板">
				<@actions>
					<button type="button" class="btn blue dropdown-toggle" data-toggle="dropdown">  操作
						<i class="fa fa-angle-down"></i>
					</button>
					<ul class="dropdown-menu pull-right" role="menu">
						<#if features?seq_contains("U")>
							<li>
								<a class="font-blue" href="${ctx.contextPath}/msg/messageTmpl/update/${model.id}">
									<i class="fa fa-edit font-blue"></i> 修改短信模板
								</a>
							</li>
							<li class="divider"> </li>
						</#if>
						<#if features?seq_contains("D") && !model.system>
							<li>
								<a href="#" class="action-confirm font-red" title="删除记录" data-confirm="您确认要删除该记录吗？"
								   data-url="${ctx.contextPath}/msg/messageTmpl/delete/${model.id}" data-redirect="${ctx.contextPath}/msg/messageTmpl/" >
									<i class="fa fa-times font-red"></i> 删除短信模板
								</a>
							</li>
							<li class="divider"> </li>
						</#if>
						<#if features?seq_contains("C")>
							<li>
								<a class="font-green" href="${ctx.contextPath}/msg/messageTmpl/create">
									<i class="fa fa-plus font-green"></i> 新建短信模板
								</a>
							</li>
							<li class="divider"> </li>
						</#if>
					</ul>
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