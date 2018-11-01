<#include "../include/head.ftl">
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        Diboot 轻代码开发平台
    </div>
    <!-- /.login-logo -->
    <div class="login-box-body">
        <p class="login-box-msg">登录账号</p>
    <#if RequestParameters.error?? && RequestParameters.error == 'attempts'>
        <div class="alert alert-warning alert-dismissable">
            <i class="icon fa fa-warning"></i> 登录失败次数超出最大限制！请稍后再试或联系管理员找回密码！
            <br><br><br>
        </div>
    <#else>
        <form role="form" id="form-login" method="POST" action="${ctx.contextPath}/login">
            <div class="form-group has-feedback">
                <input type="text" class="form-control blurfield" name="username" placeholder="用户名" required="true">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" placeholder="密码" required="true" class="form-control">
                <span class="fa fa-key form-control-feedback"></span>
            </div>
            <#if loginFailed?? && loginFailed gt 10>
                <div id="vcRow" class="form-group">
                    <div class="input-append input-group">
                        <input id="vc" type="text" name="vc" placeholder="输入验证码" class="form-control vc blurfield" required="true" aria-describedby="basic-addon">
                        <span class="add-on input-group-addon" title="验证码" data-defaulttxt="验证码" style="border-right: 0px solid #ccc; padding:0px; min-width: 90px;">
                            <img class="vcimg" src="${ctx.contextPath}/vc" style="width: 90px; height: 32px;">
                        </span>
                    </div>
                    <label class="control-label pull-left hide" for="password">验证码输入错误!</label>
                </div>
            </#if>
            <#if RequestParameters.error??>
                <div class="form-group has-feedback has-error">
                    <p class="text-red text-center">
                        <#if RequestParameters.error == 'expired'>
                            当前会话已失效，请重新登录！
                        <#elseif RequestParameters.error == 'invalid'>
                            用户名或密码错误，请重新输入！
                        <#elseif RequestParameters.error == 'attempts'>
                            登录尝试次数超出最大限制！
                        <#elseif RequestParameters.error == 'locked'>
                            该账号已被锁定！
                        <#else>
                            用户名或密码错误，请重新输入！
                        </#if>
                    </p>
                </div>
            </#if>
            <div class="row">
                <div class="col-xs-8">
                    <label class="label-checkbox">
                        <input id="remember_me" name="remember_me" type="checkbox" value="true" checked="checked"/>
                        <span></span>
                        记住我
                    </label>
                </div>
                <!-- /.col -->
                <div class="col-xs-4">
                    <button type="submit" class="btn btn-primary btn-block btn-flat" data-loading-text="正在登录..."> 登 录 </button>
                </div>
                <!-- /.col -->
            </div>
        </form>
    </#if>
    </div><#-- /.login-box-body -->
    <div class="copyright text-center" style="margin-top: 20px">&copy; 2018 <a href="http://www.dibo.ltd" target="_blank">苏州帝博信息技术有限公司</a></div>
</div><#-- /.login-box -->
<#include "../include/scripts.ftl">
<script type="text/javascript">
$(document).ready(function(){
    $("body").keydown(function(event) {
        if (event.keyCode == "13") {
            $(".btn.green.pull-right").click();
        }
    });
    <#if loginFailed?? && loginFailed gt 10>
    $("input.blurfield").on("blur", function(){
        $("#username").val($("#user_name").val()+"___"+$("#vc").val());
    });
    $("img.vcimg").on("click", function(){
        $("img.vcimg").attr("src", "${ctx.contextPath}/vc?"+new Date().getTime());
    });
    </#if>
});
</script>
</body>
</html>