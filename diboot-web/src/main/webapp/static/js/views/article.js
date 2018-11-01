/**
 * 元数据页面scripts
 * @author mazc
 */
$(document).ready(function() {


    var $summernote = $('#summernote');
    $summernote.summernote({
        height: 320,
        minHeight: 300,
        maxHeight: 800,
        lang: 'zh-CN',
        placeholder: '编辑文章内容',
        callbacks: {
            onImageUpload: function(files) {
                // upload image to server and create imgNode...
                uploadImage(files[0], $summernote, contextPath+"/qiniu/upload/image");
            }
        }
    });
    if($("#content").val()){
        $summernote.summernote('code', $("#content").val());
    }
    // <#-- 提交表单，保存话题 -->
    var actionUrl = $("#actionUrl").val();
    $('#submitBtn').on("click", function() {
        var form = $("#mainForm");
        if(!form.validate()){
            return;
        }
        // <#-- 上传图片尚未完成 -->
        if(!$("#upload-image-loading").hasClass("hide")){
            alert("正在上传图片, 请稍候再提交！");
            return false;
        }
        // <#-- 提交表单发布话题 -->
        $("#submitBtn").button('loading');
        var sectionId = $("#sectionId").val();
        var source = $("#source").val() || "";
        var readCount = $("#readCount").val();
        $.post(actionUrl, {
            sectionId: sectionId,
            source: source,
            readCount: readCount,
            title: $("#title").val(),
            content: $summernote.summernote('code')
        })
        .done(function(data){
            $("#submitBtn").button('reset');
            if(data.errors){
                var html = data.errors.join("\n");
                alert(html);
            }
            else{
                toastr.success("文章发布成功！", "操作成功");
                location.href = contextPath + "/article/list?sectionId="+$("#sectionId").val();
            }
        })
        .fail(function(error){
            $("#submitBtn").button('reset');
            alert("文章发布失败，请稍候再试！");
        });
    });
    // 上传图片文件到服务器
    function uploadImage(file, $summernote, ajaxUploadFileUrl) {
        //上传文件到服务器
        $("#upload-image-loading").removeClass("hide");
        var fData = new FormData($("#imageForm")[0]);
        fData.append("attachment", file);
        $.ajax({
            url: ajaxUploadFileUrl,
            data: fData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        }).done(function(result){
            if(result && result.link){
                var imgNode = document.createElement("img");
                imgNode.src = contextPath+result.link;
                $summernote.summernote('insertNode', imgNode);
            }
            else if(result && result.errors){
                var html = result.errors.join("<br/>");
                toastr.error(html, "图片上传失败");
            }
            $("#upload-image-loading").addClass("hide");
        })
        .fail(function(error){
            $("#upload-image-loading").addClass("hide");
            toastr.error("上传图片失败，图片大小不能超过5M，请尝试更换其他图片重试！", "图片上传失败");
        });
    }
});