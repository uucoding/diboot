<#assign className = 'systemConfig' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="系统配置管理" loc1url="${ctx.contextPath}/systemConfig/" loc2="新建系统配置" back=true/>
    <@portlet>
        <@portletTitle title="新建系统配置" icon="fa-plus">
            <div class="tools">
                <a href="javascript:" class="collapse"> </a>
            </div>
        </@portletTitle>
        <#include "_form.ftl">
    </@portlet><#-- END PAGE BODY -->
</@wrapper>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/vue.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/js/views/system-config.js"></script>
</body>
</html>