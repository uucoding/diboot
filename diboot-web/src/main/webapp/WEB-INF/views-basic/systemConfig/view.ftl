<#assign features=["C","R","U","D","L","batch","rest","export","openAsDialog"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="系统配置管理" loc1url="${ctx.contextPath}/systemConfig/" loc2="查看系统配置" back=true/>
    <div class="content">
	<@portlet>
		<@portletTitle title="查看系统配置" icon="fa-search-plus">
			<@actions>
                <button type="button" class="btn blue dropdown-toggle" data-toggle="dropdown">  操作
                    <i class="fa fa-angle-down"></i>
                </button>
                <ul class="dropdown-menu pull-right" role="menu">
					<#if features?seq_contains("U")>
                        <li>
                            <a class="font-blue" href="${ctx.contextPath}/systemConfig/update/${model.id}">
                                <i class="fa fa-edit font-blue"></i> 修改系统配置
                            </a>
                        </li>
                        <li class="divider"> </li>
					</#if>
					<#if features?seq_contains("D")>
                        <li>
                            <a href="#" class="action-confirm font-red" title="删除记录" data-confirm="您确认要删除该记录吗？"
                               data-url="${ctx.contextPath}/systemConfig/delete/${model.id}" data-redirect="${ctx.contextPath}/systemConfig/" >
                                <i class="fa fa-times font-red"></i> 删除系统配置
                            </a>
                        </li>
                        <li class="divider"> </li>
					</#if>
					<#if features?seq_contains("C")>
                        <li>
                            <a class="font-green" href="${ctx.contextPath}/systemConfig/create">
                                <i class="fa fa-plus font-green"></i> 新建系统配置
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