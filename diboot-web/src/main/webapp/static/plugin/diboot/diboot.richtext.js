/**
 * 富文本编辑器页面初始化
 */
$(document).ready(function() {
    $('.rich-text').each(function (index, element) {
        var editor = $(element);
        var forField = $(editor.data("field"));
        editor.summernote({
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'underline', 'clear']],
                //['color', ['color']], 字体颜色
                ['para', ['ul', 'ol', 'paragraph']],
                ['table', ['table']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['fullscreen']] //, 'codeview'
            ],
            height: 240,
            minHeight: 200,
            maxHeight: 800,
            lang: 'zh-CN',
            placeholder: '编辑内容',
            callbacks: {
                onImageUpload: function(files) {
                    // upload image to server and append image dom
                    uploadImage(editor, files, contextPath+"/common/upload");
                },
                onBlur: function() {
                    if(forField){
                        forField.val(editor.summernote('code'));
                    }
                    console.debug(editor.summernote('code'))
                    console.debug(forField.val())
                }
            }
        });
        // 回显
        if(forField && forField.val()){
            editor.summernote('code', forField.val());
        }
    });

    // 上传图片文件到服务器
    function uploadImage(editor, files, ajaxUploadFileUrl) {
        //上传文件到服务器
        toastr.info("正在上传图片，请稍候...", "上传中");
        var formData = new FormData();
        formData.append("attachment", files[0]); //暂时只支持每次上传1个
        $.ajax({
            url: ajaxUploadFileUrl,
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST'
        }).done(function(result){
            if(result && result.code == 0){
                var imgNode = document.createElement("img");
                imgNode.src = contextPath + result.data.url;
                editor.summernote('insertNode', imgNode);
                toastr.success(result.msg, "上传成功");
            }
            else{
                toastr.error(result.msg, "上传失败");
            }
        })
        .fail(function(error){
            toastr.error("上传图片失败，请尝试缩减图片大小或更换其他图片重试！", "图片上传失败");
        });
    }
});