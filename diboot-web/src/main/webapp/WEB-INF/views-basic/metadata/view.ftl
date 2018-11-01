<#assign features=["C", "U", "D"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="元数据管理" loc1url="${ctx.contextPath}/metadata/" loc2="查看元数据" back=true/>
    <div class="content">
        <@portlet>
            <@portletTitle title="查看元数据">
                <@actions>
                    <button type="button" class="btn blue dropdown-toggle" data-toggle="dropdown"> ${btnname!" 操作 "}
                        <i class="fa fa-angle-down"></i>
                    </button>
                    <ul class="dropdown-menu pull-right" role="menu">
                        <#if features?seq_contains("U")>
                            <li>
                                <a class="font-blue" href="${ctx.contextPath}/metadata/update/${model.id}">
                                    <i class="fa fa-edit font-blue"></i> 修改元数据
                                </a>
                            </li>
                            <li class="divider"> </li>
                        </#if>
                        <#if features?seq_contains("D") && !(model.system)>
                            <li>
                                <a href="#" class="action-confirm font-red" title="删除元数据" data-confirm="您确认要删除该元数据吗？"
                                   data-url="${ctx.contextPath}/metadata/delete/${model.id}" data-redirect="${ctx.contextPath}/metadata/" >
                                    <i class="fa fa-times font-red"></i> 删除元数据
                                </a>
                            </li>
                            <li class="divider"> </li>
                        </#if>
                        <#if features?seq_contains("C")>
                            <li>
                                <a class="font-green" href="${ctx.contextPath}/metadata/create">
                                    <i class="fa fa-plus font-green"></i> 新建元数据
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