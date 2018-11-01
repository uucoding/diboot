<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
<@location loc1="元数据管理" loc1url="${ctx.contextPath}/metadata/" loc2="新建元数据" back=true/>
<div class="content">
    <@portlet>
        <@portletTitle title="新建元数据">
            <div class="tools">
                <a href="javascript:;" class="collapse"> </a>
            </div>
        </@portletTitle>
        <@portletBody>
            <#include "_form.ftl">
        </@portletBody>
    </@portlet>
</div>
</@wrapper>
<script src="${ctx.contextPath}/static/js/views/metadata.js" type="text/javascript"></script>
</body>
</html>