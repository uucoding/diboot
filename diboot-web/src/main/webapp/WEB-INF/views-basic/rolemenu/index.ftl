<#assign features=["C"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="角色权限管理" loc1url="${ctx.contextPath}/rolemenu/" loc2="角色与操作权限设置" />
    <div class="content">
        <@portlet>
            <@portletTitle title="角色与操作权限">
                <#if features?seq_contains("C")>
                    <@actions>
                        <button type="button" class="btn btn-sm blue" data-toggle="modal" data-target="#dialog">
                            <i class="fa fa-plus"></i> 新建角色
                        </button>
                    </@actions>
                </#if>
            </@portletTitle>
            <@portletBody>
                <#include "_index_body.ftl">
            </@portletBody>
        </@portlet><#-- END PAGE BODY -->
    </div><#-- END CONTENT BODY -->
</@wrapper>
<#include "_form_role.ftl" />
</body>
</html>