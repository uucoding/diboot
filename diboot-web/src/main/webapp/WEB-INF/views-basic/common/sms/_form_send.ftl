<#-- Modal -->
<div class="modal fade" id="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">发送短信</h4>
            </div>
            <form class="form-horizontal" role="form" action="${ctx.contextPath}/msg/sms/send" method="post">
                <div class="modal-body">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="col-md-3 control-label">短信模板</label>
                            <div class="col-md-7">
                                <select id="tmplId" name="tmplId" class="form-control">
                                <option value="">- 选择短信模板 -</option>
                                <#if tmplList??>
                                    <#list tmplList as tmpl>
                                        <option value="${tmpl.id}" data-content="${tmpl.content}"><#if tmpl.code??>${tmpl.code} - </#if>${tmpl.title}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">短信内容 <@required /></label>
                            <div class="col-md-7">
                                <textarea id="content" class="form-control" name="content" data-fv="NotNull"></textarea>
                            </div>
                            <#-- 选中的model ID -->
                            <input type="hidden" name="modelIds" id="selectedModelIds" value="" data-bindid="${(model.id)!""}" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn dark btn-outline" data-dismiss="modal"> 取消 </button>
                    <button type="button" class="btn green send"> 发送 </button>
                </div>
            </form>
        </div>
    </div>
</div>