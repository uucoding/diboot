<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="系统用户管理" loc1url="${ctx.contextPath}/user/" loc2="更新用户" back=true/>
    <div class="content">
        <@portlet>
            <@portletTitle title="更新用户">
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
<script type="text/javascript" src="${ctx.contextPath}/static/js/views/user.js"></script>
</body>
</html>