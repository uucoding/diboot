/****
 * Simple Form Validator for Bootstrap
 * Mazc@dibo.ltd
 * 2016/2/19
 */
;(function($) {
    /***
     * 绑定Form验证
     */
    $.fn.bindValidator = function() {
        var form = $(this);
        form.find(".form-control").each(function(index, element){
            var _self = $(element);
            if(_self.hasClass("select2")){
                _self.bind("change", function() {
                    _self.validateField();
                });
            }
            else{
                _self.bind("blur", function() {
                    _self.validateField();
                });
            }
        });
        form.submit(function() {
            return form.validate();
        });
    };
    /***
     * Form验证
     */
    $.fn.validate = function() {
        var valid = true;
        $(this).find(".form-control").each(function(index, element) {
            if (!$(this).validateField()) {
                valid = false;
            }
        });
        return valid;
    };
    /**
     * 字段验证
     */
    $.fn.validateField = function() {
        var field = $(this);
        var value = field.val();
        var label = field.attr("placeholder");
        if (label && label.indexOf("(") > 0) {
            label = label.substring(0, label.indexOf("("));
        }
        var fv = field.data("fv");
        // 不可见（即隐藏的）
        if(!field.is(':visible')){
            return true;
        }
        // 没有设置validator或者未填写的非必需字段
        if (!fv || (!value && (!field.attr("required") && fv.toLowerCase().indexOf("notnull") == -1))) {
            return true;
        }
        //如果不是select则需要append icon
        var needIcon = field[0].tagName.toLowerCase() != "select" &&
            field[0].tagName.toLowerCase() != "textarea";

        var valid = true;
        var msg = label ? label : "";
        var validators = fv.split(",");
        for (var i in validators) {
            if (!validators[i]) {
                continue;
            }
            if (validators[i].toLowerCase() == "notnull") {
                if (!value) {
                    valid = false;
                    msg += "不能为空！";
                    break;
                }
            } else if (validators[i].toLowerCase() == "number") {
                var regExp = /^-?\d+(\.\d+)?$/;
                valid = regExp.test(value);
                if (!valid) {
                    msg += "格式有误，请输入数字！";
                    break;
                }
            } else if (validators[i].toLowerCase().indexOf("length") == 0) {
                var range = validators[i].substring(validators[i].indexOf("(") + 1, validators[i].lastIndexOf(")"));
                if (range.indexOf("-") == -1) {
                    if(range.startsWith("=")){
                        valid = (value.length == parseInt(range.substring(1)));
                        if (!valid) {
                            msg += "长度不符合要求，该字段限定固定长度为 " + range + " 位！";
                            break;
                        }
                    }
                    else{
                        valid = value.length <= parseInt(range);
                        if (!valid) {
                            msg += "长度不符合要求，该字段限定最大长度为 " + range + " 位！";
                            break;
                        }
                    }
                } else {
                    var arr = range.split("-");
                    if (arr[0]) {
                        valid = value.length >= arr[0];
                        if (!valid) {
                            msg += "长度不符合要求，该字段限定至少 " + arr[0] + " 个字符！";
                            break;
                        }
                    }
                    if (arr[1]) {
                        valid = value.length <= arr[1];
                        if (!valid) {
                            msg += "长度不符合要求，该字段限定最多 " + arr[1] + " 个字符！";
                            break;
                        }
                    }
                }
            } else if (validators[i].toLowerCase() == "email") {
                var emailRegExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                valid = emailRegExp.test(value);
                if (!valid) {
                    msg += "格式有误，请输入正确的邮箱！";
                    break;
                }
            } else if (validators[i].toLowerCase() == "phone") {
                var regExp = /^1\d{10}$/gi;
                valid = regExp.test(value);
                if (!valid) {
                    regExp = /^0\d{2,3}-?\d{7,8}$/;
                    valid = regExp.test(value);
                }
                if (!valid) {
                    msg += "格式有误，请输入正确的手机号码！";
                    break;
                }
            } else if(validators[i].toLowerCase() == "phonestr"){
                // 支持+86等开头的电话格式校验
                var regExp = /^(\+|\d)\d{10,12}/;
                valid = regExp.test(value);
                if (!valid) {
                    msg += "格式有误，请输入正确的手机号码！";
                    break;
                }
            } else if (validators[i].toLowerCase() == "password") {
                if (!field.data("original") || value != field.data("original")) {
                    var regExp = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\d]{6,32}$/;
                    valid = regExp.test(value);
                    if (!valid) {
                        msg += "不符合规则要求，请输入6位及以上且须包含大小写字母及数字！";
                        break;
                    }
                }
            } else if (validators[i].toLowerCase() == "wechat"){
                // 根据企业微信通讯录同步的微信账号格式要求进行校验
                var regExp = /^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/;
                valid = regExp.test(value);
                if (!valid){
                    msg += "不符合规则要求，请输入6-20个字母、数字、下划线或减号，以字母开头!";
                    break;
                }
            } else if (validators[i].toLowerCase() == "ajax") {
                $.ajax({
                    url: field.data("ajax") + encodeURIComponent(field.val()),
                    async: false,
                    dataType: "json",
                    method: "get",
                    success: function(result) {
                        if (result) {
                            if (result.error) {
                                valid = false;
                                msg += result.error;
                            }
                        }
                    }
                });
                if (!valid) {
                    break;
                }
            } else {
                alert("TODO:// validation: " + validators[i]);
            }
        }
        // 反馈校验结果到UI
        var parentWrapper = field.parent();
        parentWrapper.find(".form-control-feedback").remove();
        parentWrapper.find(".help-block").remove();
        // 组合组件不加校验结果图标
        if (parentWrapper.hasClass("input-group")) {
            needIcon = false;
        }
        if (valid) {
            parentWrapper.removeClass("has-error").addClass("has-feedback has-success");
            if (needIcon) {
                var icon = $("<i style='display: block;' class='form-control-feedback fa fa-check'></i>");
                parentWrapper.append(icon);
            }
            if(parentWrapper.next('div.has-error')){
                parentWrapper.next('div.has-error').remove()
            }
        } else {
            parentWrapper.removeClass("has-success").addClass("has-feedback has-error");
            if (needIcon) {
                var icon = $("<i style='display: block;' class='form-control-feedback fa fa-warning'></i>");
                parentWrapper.append(icon);
            }
            var info = $("<small style='display: block;' class='help-block'>" + msg + "</small>");
            if (parentWrapper.hasClass("input-group")) {
                if(parentWrapper.next('div.has-error').length<1){
                    parentWrapper.after('<div class="has-error"></div>');
                }
                parentWrapper.next('div.has-error').empty().append(info)
            }else{
                parentWrapper.append(info);
            }
        }

        return valid;
    };
})(jQuery);