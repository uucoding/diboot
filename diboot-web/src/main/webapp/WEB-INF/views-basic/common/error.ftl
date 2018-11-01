<#include "../include/head.ftl">
<body class="${bodyClass}">
<#if Session.user??>
    <@wrapper>
        <@location loc2="出错了" />
    <section class="content">
        <div class="error-page">
            <h2 class="headline text-yellow"> <#if statusCode??>${statusCode}<#else>404</#if></h2>
            <div class="error-content" style="padding-top: 25px">
                <h3><i class="fa fa-warning text-yellow"></i> 抱歉，您访问的页面出错了！</h3>
                <p>
                    <#if exceptionType?? && exceptionType == "NoPermissionException">
                        您无权访问该页面！
                    <#elseif exceptionType?? && exceptionType == "MultipleSession">
                    ${message!"您已经登录了系统，请勿重复登录！"} <a href="${ctx.contextPath}/logout">退出重新登录</a>
                    <#else>
                        您访问的页面不存在或发生错误，请稍后再试或联系技术人员！
                    </#if>
                </p>
            </div>
            <!-- /.error-content -->
        </div>
        <!-- /.error-page -->
    </section>
    </@wrapper>
<#else>
<section class="content">
    <div class="error-page">
        <h2 class="headline text-yellow"> <#if statusCode??>${statusCode}<#else>404</#if></h2>
        <div class="error-content" style="padding-top: 25px">
            <h3><i class="fa fa-warning text-yellow"></i> 抱歉，您访问的页面出错了！</h3>
            <p>
                <#if exceptionType?? && exceptionType == "NoPermissionException">
                    您无权访问该页面！
                <#elseif exceptionType?? && exceptionType == "MultipleSession">
                ${message!"您已经登录了系统，请勿重复登录！"} <a href="${ctx.contextPath}/logout">退出重新登录</a>
                <#else>
                    您访问的页面不存在或发生错误，请稍后再试或联系技术人员！
                </#if>
            </p>
        </div>
    </div>
</section>
    <#include "../include/footer.ftl">
</#if>
</body>
</html>