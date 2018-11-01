<#assign skin="green" /><#-- 默认主题色 -->
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>Diboot 轻代码开发平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="Diboot轻代码开发平台" name="keywords">
    <meta content="高质量、高效率、智能化的软件开发平台" name="description"/>
    <meta content="苏州帝博信息技术有限公司 - www.dibo.ltd" name="author"/>
    <link href="${ctx.contextPath}/static/plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx.contextPath}/static/theme-basic/css/AdminLTE.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/theme-basic/css/skin-${skin}.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/theme-basic/css/web.custom.css" rel="stylesheet" type="text/css" />
    <link href="${ctx.contextPath}/static/plugin/toastr/toastr.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/plugin/pace/pace.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/plugin/select2/css/select2.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/plugin/datepicker/css/datepicker3.min.css" rel="stylesheet">
    <link href="${ctx.contextPath}/static/plugin/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <#-- 按需加载插件css -->
    <#if plugins??>
    <#list plugins as plugin>
        <link href="${ctx.contextPath}/static/plugin/${plugin}/css/${plugin}.min.css" rel="stylesheet">
    </#list>
    </#if>
    <link rel="shortcut icon" href="${ctx.contextPath}/static/favicon.ico">
    <#-- 全局变量 -->
    <script type="text/javascript">
        var contextPath = "${ctx.contextPath}";
        var csrfToken = "<#if _csrf_token??>${_csrf_token}</#if>";
    </script>
</head>
<#-- 加载宏定义 -->
<#include "macro.ftl">
<#assign bodyClass="skin-${skin} sidebar-mini" />