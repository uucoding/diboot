<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
    <@location loc1="元数据管理" loc1url="${ctx.contextPath}/metadata/" loc2="更新元数据" back=true/>
    <div class="content">
        <@portlet>
            <@portletTitle title="更新元数据">
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
<script src="${ctx.contextPath}/static/js/views/metadata.js" type="text/javascript"></script>
</body>
</html>