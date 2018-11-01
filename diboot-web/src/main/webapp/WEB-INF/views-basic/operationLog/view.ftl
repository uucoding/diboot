<#assign features=["C", "U", "D"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="操作日志管理" loc1url="${ctx.contextPath}/operationLog/" loc2="查看操作日志" back=true />
<div class="content">
    <@portlet>
        <@portletTitle title="查看操作日志">
        </@portletTitle>
        <@portletBody>
            <#include "_view.ftl">
        </@portletBody>
    </@portlet><#-- END PAGE BODY -->
</div>
</@wrapper>
</body>
</html>