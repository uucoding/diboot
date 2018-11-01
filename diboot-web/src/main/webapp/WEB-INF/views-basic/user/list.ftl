<#assign features=["C", "U", "D"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="系统用户管理" loc1url="${ctx.contextPath}/user/" loc2="用户列表" />
    <div class="content">
        <@portlet>
            <@portletTitle title="用户列表">
                <#if features?seq_contains("C")>
                    <@actions>
                        <a class="btn btn-info btn-sm" href="${ctx.contextPath}/user/create">
                            <i class="fa fa-plus"></i> 新建用户
                        </a>
                    </@actions>
                </#if>
            </@portletTitle>
            <@portletBody>
                <#include "list_page.ftl">
                <#include "../include/pagination.ftl">
            </@portletBody>
        </@portlet><#-- END PAGE BODY -->
    </div><#-- END CONTENT BODY -->
</@wrapper>
</body>
</html>