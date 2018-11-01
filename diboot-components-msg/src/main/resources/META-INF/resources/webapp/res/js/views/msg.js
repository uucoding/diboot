/**
 * 发送短信页面scripts
 * @author mazc
 */
$(document).ready(function() {
    $('.btn-sendsms').on("click", function(){
        // 如果是绑定了一个
        var selectedModelIdsObj = $("#selectedModelIds");
        if(selectedModelIdsObj.data("bindid")){
            selectedModelIdsObj.val(selectedModelIdsObj.data("bindid"));
        }
        else{
            var modelIds = [];
            $(".selectAll-child").each(function(index, element){
                if($(this).is(':checked')){
                    modelIds.push($(this).val());
                }
            });
            if(modelIds.length == 0){
                alert("请先选择需要发送短信的学员！");
                return false;
            }
            selectedModelIdsObj.val(modelIds.join(","));
        }
        // 显示对话框
        $("#dialog").modal('show');
    });

    // 选择模板时 自动填充内容
    $('#dialog #tmplId').on("change", function() {
        var option = $(this).find("option:selected");
        var content = $(option).data("content");
        if(!content){
            content = "";
        }
        $('#dialog #content').val(content);
    });

    // 获取选中的checkbox - 处理待发送对象
    $('#dialog button.send').on("click", function() {
        var content = $('#dialog #content').val();
        if(!content){
            toastr.error("短信内容不能为空", "操作失败");
            return false;
        }
        var form = $("#dialog form");
        $.post(form.attr("action"), form.serialize())
        .done(function(data){
            if(data.code > 0){
                toastr.error(data.msg, "发送失败");
            }
            else{
                toastr.success(data.msg, "发送成功");
                $("#dialog").modal('hide');
            }
        })
        .fail(function(){
            toastr.error("发送失败，请稍后重试！", "发送失败");
        });
        e.preventDefault();
    });
});