<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="角色权限管理" loc1url="${ctx.contextPath}/rolemenu/" loc2="设置操作权限" back=true/>
<div class="content">
    <@portlet>
        <@portletTitle title="设置操作权限">
            <div class="tools">
                <a href="javascript:;" class="collapse"> </a>
            </div>
        </@portletTitle>
        <@portletBody>
            <#include "_form.ftl">
        </@portletBody>
    </@portlet><#-- END PAGE BODY -->
</div>
</@wrapper>
</body>
</html>