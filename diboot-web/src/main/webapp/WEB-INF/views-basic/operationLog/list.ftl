<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="操作日志管理" loc1url="${ctx.contextPath}/operationLog/" loc2="日志记录" />
    <div class="content">
        <@portlet>
            <@portletTitle title="日志记录">
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