<#include "../../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="消息管理" loc1url="${ctx.contextPath}/message/" loc2="新建消息" back=true/>
<div class="content">
    <@portlet>
        <@portletTitle title="新建消息">
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