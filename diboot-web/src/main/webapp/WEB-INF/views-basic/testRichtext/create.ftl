<#assign plugins = ['summernote']/>
<#assign className = 'testRichtext' />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="测试富文本管理" loc1url="${ctx.contextPath}/testRichtext/" loc2="新建测试富文本" back=true/>
    <div class="content">
    <@portlet>
        <@portletTitle title="新建测试富文本" icon="fa-plus">
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