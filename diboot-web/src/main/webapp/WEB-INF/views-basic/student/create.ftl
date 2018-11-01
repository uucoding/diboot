<#assign plugins = ['editormd', 'lightbox', 'datetimepicker']/>
<#assign className = 'student' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="学生管理" loc1url="${ctx.contextPath}/student/" loc2="新建学生" back=true/>
    <div class="content">
    <@portlet>
        <@portletTitle title="新建学生" icon="fa-plus">
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