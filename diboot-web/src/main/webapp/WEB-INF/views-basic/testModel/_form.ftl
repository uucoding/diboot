<form class="form-horizontal" method="post" enctype="multipart/form-data">
    <@portletBody>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalLng">常规长整数 </label>
        <div class="col-md-9">
			<input type="text" id="normalLng" name="normalLng" class="form-control " placeholder=""
                   value="<#if (model.normalLng)??>${model.normalLng}</#if>" 
		   data-fv="Number">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="referenceLng">关联表长整数 </label>
        <div class="col-md-9">
            <#-- referenceLng = student.id, 字段显示为: name -->
				<select name="referenceLng" class="form-control <#if !(referenceLngOpts??)> select2-ajax<#else> select2</#if>" data-url="${ctx.contextPath}/testModel/smartSearch" data-model="Student" data-field="id" data-label="name" data-textfield="name" data-text="${(model.name)!''}">
                    <option value="">- 选择 -</option>
                    <#if referenceLngOpts??>
                        <#list referenceLngOpts as opt>
                            <option value="${(opt.v)!''}"<#if (model.referenceLng)?? && (opt.v)?? && model.referenceLng == opt.v> selected</#if>>${(opt.k)!''}</option>
                        </#list>
                    </#if>
                </select>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalInt">常规数字 </label>
        <div class="col-md-9">
			<input type="text" id="normalInt" name="normalInt" class="form-control " placeholder=""
                   value="<#if (model.normalInt)??>${model.normalInt}</#if>" 
		   data-fv="Number">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="metaBool">元数据布尔 </label>
        <div class="col-md-9">
            <#-- 引用元数据: AUDIT_STATUS -->
			<select name="metaBool" class="form-control select2">
                <option value="">- 选择 -</option>
                <#if metaBoolOpts??>
                    <#list metaBoolOpts as opt>
                        <option value="${(opt.v)!''}"<#if (model.metaBool)?? && (opt.v)?? && model.metaBool?string == opt.v> selected</#if>>${(opt.k)!''}</option>
                    </#list>
                </#if>
            </select>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalDate">常规日期 </label>
        <div class="col-md-9">
			<input type="text" id="normalDate" name="normalDate" class="form-control datepicker" placeholder=""
                   value="<#if (model.normalDate)??>${model.normalDate?date}</#if>" 
				   >
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalDatetime">常规时间 <@required /></label>
        <div class="col-md-9">
			<input type="text" id="normalDatetime" name="normalDatetime" class="form-control datetimepicker" placeholder=""
                   value="<#if (model.normalDatetime)??>${model.normalDatetime?datetime}</#if>" required="true"
				   >
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalDouble">浮点数字 </label>
        <div class="col-md-9">
			<input type="text" id="normalDouble" name="normalDouble" class="form-control " placeholder=""
                   value="<#if (model.normalDouble)??>${model.normalDouble}</#if>" 
		   data-fv="Number">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="normalStr">常规文本域 </label>
        <div class="col-md-9">
			<input type="text" id="normalStr" name="normalStr" class="form-control " placeholder=""
                   value="<#if (model.normalStr)??>${model.normalStr}</#if>" 
		   data-fv="Length(-200)">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="metaStr">元数据域 </label>
        <div class="col-md-9">
            <#-- 引用元数据: ROLE -->
			<select name="metaStr" class="form-control select2">
                <option value="">- 选择 -</option>
                <#if metaStrOpts??>
                    <#list metaStrOpts as opt>
                        <option value="${(opt.v)!''}"<#if (model.metaStr)?? && (opt.v)?? && model.metaStr == opt.v> selected</#if>>${(opt.k)!''}</option>
                    </#list>
                </#if>
            </select>
        </div>
            </div>
			<div class="form-group">
                <label class="col-md-2 control-label" for="textareaStr">多内容域 </label>
                <div class="col-md-10">
                        <textarea id="textareaStr" name="textareaStr" class="form-control" placeholder="" 
						data-fv="Length(-500)"><#if (model.textareaStr)??>${model.textareaStr}</#if></textarea>
                </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="imgUploadStr">图片上传域 </label>
        <div class="col-md-9">
	  		<#if (model.imgUploadStr)??>
                <input type="hidden" name="imgUploadStr" value="${(model.imgUploadStr)!''}">
                <div class="thumbnail"
                     style="width: 120px; height: 120px; line-height: 20px; margin-right: 20px;">
                    <img src="${(model.imgUploadStr)!''}" id="imgUploadStr" width="120" height="120">
                </div>
                <div>
                    <button class="btn btn-default btn-file"> <span
                            class="fileinput-new"><i class="fa fa-plus"></i>更换封面
									(120*120)</span> <input type="file" class="imgUpload" data-target="imgUploadStr" name="imgUploadStr_file"></button>
                </div>
                <#else>
                    <div class="fileinput-preview thumbnail" data-trigger="fileinput"
                         style="width: 120px; height: 120px; margin-right: 20px;">
                        <img src="http://placehold.it/120x120/" id="imgUploadStr" width="120" height="120"
                             alt="...">
                    </div>
                    <div>
                        <button class="btn btn-default btn-file"> <span
                                class="fileinput-new"><i class="fa fa-plus"></i>上传封面
					(120*120)</span> <input type="file" class="imgUpload" data-target="imgUploadStr" name="imgUploadStr_file"></button>
                    </div>
            </#if>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="fileUploadStr">文件上传域 </label>
        <div class="col-md-9">
			<#if (model.fileUploadStr)??>
                <input type="hidden" name="fileUploadStr" value="${(model.fileUploadStr)!''}">
                <a href="${(model.fileUploadStr)!''}" class="btn btn-default">下载文件</a>
                <input type="file" name="fileUploadStr_file" style="display: inline-block; margin-left: 20px;">
                <#else>
                    <input type="file" name="fileUploadStr_file">
            </#if>
        </div>
            </div>
	    <div class="form-group">
            <label class="col-md-2 control-label" for="richtextStr">富文本域 </label>
            <div class="col-md-10">
						<div class="editormd">
                            <textarea class="editormd-markdown-textarea" name="Md">${(editableTextMap['richtextStr']['srcContent'])!''}</textarea>
                            <#-- html textarea 需要开启配置项 saveHTMLToTextarea == true -->
                            <textarea class="editormd-html-textarea" name="richtextStr"></textarea>
                        </div>
            </div>
        </div>
    </@portletBody>
    <@portletFooter>
        <div class="col-md-offset-2 col-md-2">
            <#if (model.id)??>
                <button class="btn btn-primary btn-block" type="submit"> 提交更新 </button>
                <#else>
                    <button class="btn btn-success btn-block" type="submit"> 提交 </button>
            </#if>
        </div>
    </@portletFooter>
</form>