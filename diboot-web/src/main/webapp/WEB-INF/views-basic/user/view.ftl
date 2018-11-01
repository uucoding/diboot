<#assign features=["C", "U", "D"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="系统用户管理" loc1url="${ctx.contextPath}/user/" loc2="查看用户" back=true/>
    <div class="content">
        <@portlet>
            <@portletTitle title="查看用户">
                <@actions>
                    <button type="button" class="btn btn-sm blue dropdown-toggle" data-toggle="dropdown">${btnname!" 操作 "}
                        <i class="fa fa-angle-down"></i>
                    </button>
                    <ul class="dropdown-menu pull-right" role="menu">
                        <#if features?seq_contains("U")>
                            <li>
                                <a class="font-blue" href="${ctx.contextPath}/user/update/${model.id}">
                                    <i class="fa fa-edit font-blue"></i> 修改用户
                                </a>
                            </li>
                            <li class="divider"> </li>
                        </#if>
                        <#if features?seq_contains("D") && (!(Session.user.id)?exists || Session.user.id!=model.id)>
                            <li>
                                <a href="#" class="action-confirm font-red" title="删除用户" data-confirm="您确认要删除该用户吗？"
                                   data-url="${ctx.contextPath}/user/delete/${model.id}" data-redirect="${ctx.contextPath}/user/" >
                                    <i class="fa fa-times font-red"></i> 删除用户
                                </a>
                            </li>
                            <li class="divider"> </li>
                        </#if>
                        <#if features?seq_contains("C")>
                            <li>
                                <a class="font-green" href="${ctx.contextPath}/user/create">
                                    <i class="fa fa-plus font-green"></i> 新建用户
                                </a>
                            </li>
                            <li class="divider"> </li>
                        </#if>
                    </ul>
                </@actions>
            <#--
            <div class="tools">
                <a href="javascript:;" class="collapse"> </a>
            </div>-->
            </@portletTitle>
            <@portletBody>
                <#include "_view.ftl">
            </@portletBody>
        </@portlet><#-- END PAGE BODY -->
    </div>
</@wrapper>
</body>
</html>