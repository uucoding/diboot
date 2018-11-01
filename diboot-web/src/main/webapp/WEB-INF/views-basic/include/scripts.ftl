<!--[if lt IE 9]>
<script src="${ctx.contextPath}/static/plugin/h5fix/respond.min.js"></script>
<script src="${ctx.contextPath}/static/plugin/h5fix/ie8.fix.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/jquery.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/theme-basic/js/adminlte.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/toastr/toastr.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/pace/pace.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/select2/js/select2.min.js"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/datepicker/js/datepicker.min.js"></script>
<#if plugins??>
<#list plugins as plugin>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/${plugin}/js/${plugin}.min.js"></script>
</#list>
<#if plugins?seq_contains("datetimepicker")>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/datetimepicker/js/datetimepicker.zh-CN.js"></script>
</#if>
<#if plugins?seq_contains("summernote")>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/summernote/js/lang/summernote-zh-CN.js"></script>
</#if>
</#if>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/diboot/diboot.validator.js?v=${version!'1.0'}"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/diboot/diboot.fixedHeader.js?v=${version!'1.0'}"></script>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/diboot/diboot.fieldSort.js?v=${version!'1.0'}"></script>
<#-- theme-basic需要替换主题目录 -->
<script type="text/javascript" src="${ctx.contextPath}/static/theme-basic/js/web.custom.js?v=${version!'1.0'}"></script>
<#if plugins?? && plugins?seq_contains("summernote")>
<script type="text/javascript" src="${ctx.contextPath}/static/plugin/diboot/diboot.richtext.js?v=${version!'1.0'}"></script>
</#if>
<script type="text/javascript">
$(document).ready(function(){
    <#-- 初始化当前页面的JS事件 -->
    $.Utils.initPageEvent();
    $.Utils.focusMenu();
    <#-- 显示提示信息 -->
    <#if result??>
        <#if result[0] == 'true'>
        toastr.success("${result[1]}", "操作成功");
        <#else>
        toastr.error("${result[1]}", "操作失败");
        </#if>
    </#if>
    <#if errors??>
        toastr.error("${errors}", "数据校验未通过");
    </#if>
    $("select.select2").select2();
    $(".datepicker").datepicker({language: 'zh-CN', format: "yyyy-mm-dd", autoclose: true});
    <#if plugins?? && plugins?seq_contains("datetimepicker")>
    $('.datetimepicker').datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        startDate: new Date()
    });
    </#if>
});
</script>