/**
 * toastr通知参数设置
 */
if (toastr) {
    toastr.options = {
        "positionClass": "toast-top-center",
        "closeButton": true,
        "debug": false,
        "progressBar": false,
        "onclick": null,
        "showDuration": "500",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    };
}
// jquery扩展
(function ($) {
    /**
     * 处理Ajax返还结果，将状态显示到Form
     */
    $.fn.handleResult = function (data) {
        var parentRow = $("#" + $(this).attr("id") + "Row");
        if (data.errors) {
            parentRow.removeClass("has-success").addClass("has-error");
            parentRow.find("label").html(data.errors[0]);
            parentRow.find("label").removeClass("hide");
        } else {
            parentRow.removeClass("has-error").addClass("has-success");
            parentRow.find("label").html("");
            parentRow.find("label").addClass("hide");
        }
    };
    /**
     * ajax加上请求头
     *
     */
    if (typeof(csrfToken) != 'undefined'){
        $.ajaxSetup({
            beforeSend: function(xhr) {
                xhr.setRequestHeader('_csrf_token', csrfToken);
            }
        });
    }
    /**
     * 初始化选择框
     * 绑定dom: data-binddom="#hiddenInputId" data-initurl="init url" data-triggerurl="url" data-prompt="- 选择 -" data-target="#domId"
     */
    $.fn.initSelectOptions = function () {
        // 自动初始化
        var _self = $(this);
        var url = _self.data("initurl");
        if (!url) {
            return;
        }
        $.ajax({
            url: url,
            async: true,
            dataType: "json",
            success: function (options) {
                var prompt = _self.data("prompt") || "- 请选择 -";
                // 重新初始化下拉框
                _self.find("option").remove();
                _self.append("<option value=''>" + prompt + "</option>");
                var selectedValue = _self.data("selectedvalue");
                // 设置隐藏的input值
                var binddom = _self.data("binddom");
                if(!selectedValue && binddom){
                    selectedValue = $(binddom).val();
                }
                $.each(options, function(val1, val2) {
                    var id = val1, name = val2;
                    if (typeof val2 == "object") {
                        id = val2.id || val2.uid || val2.v;
                        name = val2.name || val2.label || val2.k;
                    }
                    _self.append("<option value='" + id + "' " + (id == selectedValue ? "selected" : "") + ">" + name + "</option>");
                });
                if (_self.hasClass("select2")) {
                    _self.select2({
                        //width: '100%'
                    });
                }
            }
        });
    };


    jQuery.fn.extend({
        uploadPreview: function (opts) {
            var _self = this,
                _this = $(this);
            opts = jQuery.extend({
                Img: _this.data('target') || "ImgPr",
                Width: 120,
                Height: 120,
                ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
                Callback: function () {}
            }, opts || {});
            _self.getObjectURL = function (file) {
                var url = null;
                if (window.createObjectURL != undefined) {
                    url = window.createObjectURL(file)
                } else if (window.URL != undefined) {
                    url = window.URL.createObjectURL(file)
                } else if (window.webkitURL != undefined) {
                    url = window.webkitURL.createObjectURL(file)
                }
                return url
            };
            _this.change(function () {
                if (this.value) {
                    if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                        alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                        this.value = "";
                        return false
                    }

                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))

                    opts.Callback()
                }
            })
        }
    });

    // extend functions
    $.extend({
        Utils: {
            initPageEvent: function () {
                // 初始化Form Validator
                $("form").each(function (index, element) {
                    $(element).bindValidator();
                });
                //-- 初始化删除确认事件 -->
                $("a.action-confirm").on("click", $.Utils.confirmEventHandler);
                // 初始化selectAll事件
                $("#selectAll").on("click", function() {
                    $(".selectAll-child").prop("checked", $(this).is(':checked'));
                });

                // 左侧菜单最大化最小化
                // $('a.sidebar-toggle').trigger('click')
                // $('aside.main-sidebar').on({
                //     mouseover: function () {
                //         if ($('body').hasClass('sidebar-collapse')) {
                //             $('a.sidebar-toggle').trigger('click')
                //         }
                //     },
                //     mouseout: function () {
                //         if (!$('body').hasClass('sidebar-collapse')) {
                //             $('a.sidebar-toggle').trigger('click')
                //         }
                //     }
                // });

                //header账号及通知鼠标hover事件
                $('.dropdown').hover(function () {
                    $(this).addClass('open')
                }, function(){
                    $(this).removeClass('open')
                });
                //操作按钮鼠标hover事件
                $('.box-tools').hover(function () {
                    $(this).addClass('open')
                }, function () {
                    $(this).removeClass('open')
                });
                //左侧子菜单鼠标hover事件
                $('.nav-item').hover(function () {
                    if(!$(this).hasClass('menu-open')){
                        $(this).find('a').trigger('click')
                    }
                }, function () {
                    if(!$(this).hasClass('menu-open')){
                        $(this).find('.treeview-menu').hide()
                    }
                });
                // 初始化勾选操作事件
                $(".btn-batch").on("click", function() {
                    var modelIds = $.Utils.getSelectedModelIds();
                    if(modelIds.length == 0){
                        alert("请选择您需要操作的记录！");
                        return false;
                    }
                    if($(this).data("allow") == "1" && modelIds.length > 1){
                        alert("该操作不支持批量！请仅勾选一项进行操作！");
                        return false;
                    }
                    var queryParams = "";
                    if($(".paginationNav").data("params")){
                        queryParams = $(".paginationNav").data("params");
                    }
                    var modelIdsStr = modelIds.join(",");
                    var url = $(this).data("action");
                    // 构造表单并提交
                    var form = $("<form />", {action : url, method:"post", style:"display:none;"}).appendTo("body");
                    form.append("<input type=\"hidden\" name=\"modelIds\" value=\"" + modelIdsStr + "\" />");
                    form.append("<input type=\"hidden\" name=\"queryParams\" value=\"" + queryParams + "\" />");
                    form.submit();
                    e.preventDefault();
                });
                // 初始化下拉
                $("button.btn-select").on("click", function(){
                    var value = $(this).data("val");
                    var parent = $(this).parent()[0];
                    $($(parent).data("trigger")).val(value);
                    $(parent).find(".btn-select").removeClass("bg-olive").addClass("bg-gray");
                    $(this).addClass("bg-olive");
                });
                $("select.select-init").each(function(index, element){
                    $(element).initSelectOptions();
                });
                // 绑定change事件
                $("select.select-combo").each(function(index, element){
                    $(element).unbind("change");
                    $(element).bind("change", $.Utils.initComboSelectOptions);
                });
                // 添加CSRF
                if(csrfToken && csrfToken !=""){
                    $("form[method='post']").append("<input type=\"hidden\" name=\"_csrf_token\" value=\""+ csrfToken +"\" />");
                }
                // 提示框
                $('[data-toggle="tooltip"]').tooltip();
                $("#pageSizeSelector").on("change", function(){
                    var url = $(this).data("url");
                    url += ((url.indexOf("?") > 0)? "&":"?") + "pageSize=" + $(this).val();
                    location.href = url;
                });
                // 模态框
                $('.btn-modal').off('click').on('click',function () {
                    $('#modal').find('.modal-body').html('');
                    var url = $(this).data('url');
                    var title = $(this).data('title');
                    $('#modal').find('.modal-title').text(title);
                    $.ajax({
                        url: contextPath + url,
                        type:'GET',
                        dataType:'html',
                        success:function (data) {
                            $('#modal').find('.modal-body').html(data);
                        }
                    }).done(function () {
                        $('#modal').modal('show');
                    });
                });
                $('.btn-modal-box').off('click').on('click',function () {
                    var _this = this
                    $modal = $("#modal")
                    $modal.find('.modal-body').html('')
                    var url = $(this).data('url')
                    var title = $(this).data('title')
                    $modal.find('.modal-title').text(title);
                    $modal.find('.modal-body').load(url + ' .box', function(html){
                        $modal.modal('show')
                        $(this).find('form').off('submit').submit(function(){
                            var validateResult = $(this).validate()
                            if (!validateResult){
                                return validateResult
                            }
                            toastr.success("提交中...", "操作成功")

                            var enctype = $(this).attr("enctype")
                            if (enctype && enctype == 'multipart/form-data'){
                                // 提交文件表单
                                var formData = new FormData($(this)[0])
                                $.ajax({
                                    url: url,
                                    type: 'post',
                                    data: formData,
                                    processData:false,
                                    contentType:false,
                                    success:function(data){
                                        if ($(_this).prev('a') && $(_this).prev('a').length > 0){
                                            $modal.modal('hide')
                                            // 如果有接下来要显示的模态框，则显示，之后关闭时刷新页面
                                            setTimeout(function(){
                                                $(_this).prev('a').trigger('click')
                                                $modal.on('hidden.bs.modal', function(e){
                                                    window.location.reload()
                                                })
                                            }, 400)
                                        } else {
                                            // 如果没有则绑定事件，关闭时刷新页面
                                            $modal.on('hidden.bs.modal', function(e){
                                                window.location.reload()
                                            })
                                            $modal.modal('hide')
                                        }
                                    },
                                })
                            } else {
                                // 提交普通数据表单
                                $.post(url, $(this).serialize(), function(){
                                    if ($(_this).prev('a') && $(_this).prev('a').length > 0){
                                        $modal.modal('hide')
                                        // 如果有接下来要显示的模态框，则显示，之后关闭时刷新页面
                                        setTimeout(function(){
                                            $(_this).prev('a').trigger('click')
                                            $modal.on('hidden.bs.modal', function(e){
                                                window.location.reload()
                                            })
                                        }, 400)
                                    } else {
                                        // 如果没有则绑定事件，关闭时刷新页面
                                        $modal.on('hidden.bs.modal', function(e){
                                            window.location.reload()
                                        })
                                        $modal.modal('hide')
                                    }
                                })
                            }
                            return false
                        })
                    })

                });
                // 初始化select2-ajax，并回显相应的值
                $('.select2-ajax').each(function(index, elem){
                    // 初始化select2-ajax
                    var _this = this
                    $(elem).select2({
                        ajax: {
                            url: $(_this).data('url'),
                            data: function(params){
                                return {
                                    model: $(_this).data('model'),
                                    field: $(_this).data('field'),
                                    label: $(_this).data('label'),
                                    textfield: $(_this).data('textfield'),
                                    search: params.term
                                }
                            },
                            cache: true
                        },
                        language: {
                            noResults: function () {
                                return "暂无数据"
                            }
                        }
                    })
                    // 回显select2-ajax的值
                    var value = $(_this).data('value')
                    var text= $(_this).data('text')
                    if (value && text){
                        var html = '<option value="'+value+'" selected>'+text+'</option>'
                        $(_this).append(html)
                        $(_this).trigger('change')
                    }
                });
                // 固定表头
                $('table.header-fixed').fixHeader({topDom: ''});
                // 图片上传预览
                $(".imgUpload").uploadPreview();
                //字段排序
                $('.field-sort').fieldSort()
            },
            // 操作之前的确认框
            confirmEventHandler: function(){
                var link = $(this);
                var optname = link.data('title') ? link.data('title') : link.attr('title');
                optname = optname ? optname : '删除';
                if(confirm(link.data("confirm"))){
                    var redirectUrl = link.data("redirect");
                    $.post(link.data("url"), {}, function(result){
                        if(result){
                            if(result.error){
                                alert(result.error);
                            }
                            else if(result.success){
                                toastr.success(optname + "操作成功！", "操作成功");
                                if(redirectUrl){
                                    location.href = redirectUrl;
                                }
                                else{
                                    location.reload();
                                }
                            }
                        }
                    });
                }
            },
            // 聚焦菜单
            focusMenu: function() {
                var url = jQuery.ajaxSettings.url;
                var urlpath=url.split(contextPath)[1];
                var matchedMenu;
                // adminLTE sidebar
                $("ul.sidebar-menu li.nav-item").each(function(){
                    var menu = $(this).data("m");;
                    if (menu) {
                        if(urlpath == menu){
                            matchedMenu = $(this);
                            return false;
                        }
                        else if(url.indexOf(menu) > 0) {
                            matchedMenu = $(this);
                        }
                    }
                });
                if(matchedMenu){
                    matchedMenu.addClass("active").siblings().removeClass("active");  // adminLTE sidebar
                    var parentMenu = matchedMenu.parents("li.nav-item");
                    if(parentMenu){
                        parentMenu.addClass("active menu-open");
                    }
                }
                else {
                    $("ul.sidebar-menu li.treeview").removeClass("active");
                }
            },
			// 删除条目事件
            initRemoveRowEvent: function(){
                $(".btn-deleterow").unbind("click");
                $(".btn-deleterow").on("click", function(){
                    var target = $(this).data("target");
                    $(this).parents(target)[0].remove();
                });
            },
            // 克隆html
            initCloneRowEvent: function(){
                $(".btn-buildrow").unbind("click");
                $('.btn-buildrow').on("click", function(){
                    var wrapper = $(this).data("wrapper");
                    var template = $(this).data("template");
                    if(!wrapper || !template){
                        alert("button需绑定data-wrapper及data-template属性!");
                        return false;
                    }
                    // 替换class属性
                    $(wrapper).append($(template).html());
                    $.Utils.initRemoveRowEvent();
                });
            },
            // 初始化二级下拉框
            initComboSelectOptions: function () {
                var _self = $(this);
                var prompt = _self.data("prompt") || "- 请选择 -";
                if (!_self.val()) {
                    return;
                }
                // 处理联动select
                var triggerUrls = _self.data("triggerurl").split(",");
                var binddoms = _self.data("binddom")? _self.data("binddom").split(",") : null;
                var targets = _self.data("target").split(",");
                for(var i=0; i<triggerUrls.length; i++){
                    var url = triggerUrls[i] + _self.val();
                    var selectedValue = null;
                    if(binddoms){
                        var hiddenInput = $(binddoms[i]); // 绑定dom
                        $(hiddenInput).val(_self.val());
                        selectedValue = _self.val();
                    }
                    // 重新初始化下拉框
                    var options = $.ajax({
                        url: url,
                        async: false,
                        dataType: "json"
                    }).responseJSON;
                    if(options){
                        var selOpt = $(targets[i]);
                        selOpt.find("option").remove();
                        selOpt.append("<option value=''>" + prompt + "</option>");
                        if (options) {
                            $.each(options, function(val1, val2) {
                                var id = val1, name = val2;
                                if (typeof val2 == "object") {
                                    id = val2.id || val2.uid || val2.v;
                                    name = val2.name || val2.label || val2.k;
                                }
                                selOpt.append("<option value='" + id + "'>" + name + "</option>");
                            });
                            if (selOpt.hasClass("select2")) {
                                selOpt.select2({
                                    //width: '100%'
                                });
                            }
                            if(binddoms){
                                var hiddenInput = $(binddoms[i]); // 绑定dom
                                selOpt.on("change", function(){
                                    $(hiddenInput).val($(this).val());
                                });
                            }
                        }
                    }
                }
            },
            // 初始化公告跑马灯
            startTicker: function() {
                if ($('.vticker')) {
                    var dd = $('.vticker').easyTicker({
                        direction: 'up',
                        easing: 'easeInOutBack',
                        speed: 'slow',
                        interval: 9000,
                        height: 50,
                        visible: 0,
                        mousePause: 1
                    }).data('easyTicker');
                    dd.start();
                }
            },
            // 获取选中的id列表
            getSelectedModelIds: function () {
                var modelIds = [];
                $(".selectAll-child").each(function(index, element){
                    if($(this).is(':checked')){
                        modelIds.push($(this).val());
                    }
                });
                if(modelIds.length == 0){
                    alert("请先选择需要处理的记录！");
                    return false;
                }
                if($(this).data("allow") == "1" && modelIds.length > 1){
                    alert("该修改不支持批量操作！请仅勾选一项进行更新！");
                    return false;
                }
                return modelIds;
            }
        }
    });
    // keep alive
    window.setInterval(function() {
        $.get(contextPath + "/ping?r=" + new Date().getTime())
    }, 300000);
})(jQuery);