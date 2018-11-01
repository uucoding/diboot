<#assign features=["C", "U", "D"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="元数据管理" loc1url="${ctx.contextPath}/metadata/" loc2="元数据记录" />
    <div class="content">
        <@portlet>
            <@portletTitle title="元数据记录">
                <#if features?seq_contains("C")>
                    <@actions>
                        <a class="btn blue btn-sm" href="${ctx.contextPath}/metadata/create">
                            <i class="fa fa-plus"></i> 新建元数据
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
