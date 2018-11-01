<#assign plugins = ['editormd', 'lightbox', 'datetimepicker']/>
<#assign features=["C","R","U","D","L","Batch","Export"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="测试模型管理" loc1url="${ctx.contextPath}/testModel/" loc2="查看测试模型" back=true/>
	<div class="content">
	<@portlet>
		<@portletTitle title="查看测试模型" icon="fa-search-plus">
			<@actions>
                <button type="button" class="btn blue dropdown-toggle" data-toggle="dropdown">  操作 
                    <i class="fa fa-angle-down"></i>
                </button>
                <ul class="dropdown-menu pull-right" role="menu">
					<#if features?seq_contains("U")>
                        <li>
                            <a class="font-blue" href="${ctx.contextPath}/testModel/update/${model.id}">
                                <i class="fa fa-edit font-blue"></i> 修改测试模型
                            </a>
                        </li>
                        <li class="divider"> </li>
					</#if>
					<#if features?seq_contains("D")>
                        <li>
                            <a href="#" class="action-confirm font-red" title="删除记录" data-confirm="您确认要删除该记录吗？"
                               data-url="${ctx.contextPath}/testModel/delete/${model.id}" data-redirect="${ctx.contextPath}/testModel/" >
                                <i class="fa fa-times font-red"></i> 删除测试模型
                            </a>
                        </li>
                        <li class="divider"> </li>
					</#if>
					<#if features?seq_contains("C")>
                        <li>
                            <a class="font-green" href="${ctx.contextPath}/testModel/create">
                                <i class="fa fa-plus font-green"></i> 新建测试模型
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