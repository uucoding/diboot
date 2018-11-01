/***
 * 用户页面 script
 * @author Yaojf@dibo.ltd
 * @version 2018/2/1
 * Copyright@www.dibo.ltd
 */
$(document).ready(function(){
    $(".password-btn").click(function(event) {
        var password = $("#password");
        if( password.attr("type") == "password" ){
            password.attr("type", "text");
            $(this).find('i').removeClass('fa-eye-slash').addClass('fa-eye');
        } else{
            password.attr("type", "password");
            $(this).find('i').removeClass('fa-eye').addClass('fa-eye-slash');
        }
    });
    $('#changepwdBtn').click(function () {
        $(this).addClass('hide');
        $('#password').val('');
        $('#password_div').removeClass('hide');
    })
});