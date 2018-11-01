<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="定时任务管理" loc1url="${ctx.contextPath}/timerTask/" loc2="新建定时任务" back=true/>
    <div class="content">
    <@portlet>
        <@portletTitle title="新建定时任务" icon="fa-plus">
            <div class="tools">
                <a href="javascript:" class="collapse"> </a>
            </div>
        </@portletTitle>
        <#include "_form.ftl">
    </@portlet><#-- END PAGE BODY -->
    </div>
</@wrapper>
</body>
</html>