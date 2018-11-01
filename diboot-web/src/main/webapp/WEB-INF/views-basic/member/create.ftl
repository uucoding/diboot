<#assign plugins = ["summernote"] />
<#assign className = 'member' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="学员信息管理" loc1url="${ctx.contextPath}/member/" loc2="新建学员信息" back=true/>
    <div class="content">
    <@portlet>
        <@portletTitle title="新建学员信息" icon="fa-plus">
            <div class="tools">
                <a href="javascript:" class="collapse"> </a>
            </div>
        </@portletTitle>
        <#include "_form.ftl">
    </@portlet><#-- END PAGE BODY -->
    </div>
</@wrapper>
<script src="${ctx.contextPath}/static/js/plugin/diboot/diboot.richtext.js"></script>
</body>
</html>