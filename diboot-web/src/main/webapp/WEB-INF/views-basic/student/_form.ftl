<form class="form-horizontal" method="post" enctype="multipart/form-data">
    <@portletBody>
		<div class="form-group">
        <label class="col-md-2 control-label" for="name">姓名 <@required /></label>
        <div class="col-md-9">
			<input type="text" id="name" name="name" class="form-control " placeholder=""
                   value="<#if (model.name)??>${model.name}</#if>" required="true"
		   data-fv="NotNull,Length(-100)">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="gendar">性别 </label>
        <div class="col-md-9">
            <#-- 引用元数据: GENDER -->
			<select name="gendar" class="form-control select2">
                <option value="">- 选择 -</option>
                <#if gendarOpts??>
                    <#list gendarOpts as opt>
                        <option value="${(opt.v)!''}"<#if (model.gendar)?? && (opt.v)?? && model.gendar == opt.v> selected</#if>>${(opt.k)!''}</option>
                    </#list>
                </#if>
            </select>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="village">特长生 </label>
        <div class="col-md-9">
			<select id="village" name="village" class="form-control">
                <option value="1" <#if (model.village)?? && model.village>selected="selected"</#if>> 是 </option>
                <option value="0" <#if (model.village)?? && !model.village>selected="selected"</#if>> 否 </option>
            </select>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="testuid1">测试 </label>
        <div class="col-md-9">
			<input type="text" id="testuid1" name="testuid1" class="form-control " placeholder=""
                   value="<#if (model.testuid1)??>${model.testuid1}</#if>" 
		   data-fv="Number">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="phone">电话 </label>
        <div class="col-md-9">
			<input type="text" id="phone" name="phone" class="form-control " placeholder=""
                   value="<#if (model.phone)??>${model.phone}</#if>" 
		   data-fv="Length(-15)">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="wechat">微信 </label>
        <div class="col-md-9">
			<input type="text" id="wechat" name="wechat" class="form-control " placeholder=""
                   value="<#if (model.wechat)??>${model.wechat}</#if>" 
		   data-fv="Length(-100)">
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="teacherId">班主任 </label>
        <div class="col-md-9">
            <#-- teacherId = teacher.id, 字段显示为: teacherName -->
				<select name="teacherId" class="form-control <#if !(teacherIdOpts??)> select2-ajax<#else> select2</#if>" data-url="${ctx.contextPath}/student/smartSearch" data-model="Teacher" data-field="id" data-label="teacherName" data-textfield="teacherName" data-text="${(model.teacherName)!''}">
                    <option value="">- 选择 -</option>
                    <#if teacherIdOpts??>
                        <#list teacherIdOpts as opt>
                            <option value="${(opt.v)!''}"<#if (model.teacherId)?? && (opt.v)?? && model.teacherId == opt.v> selected</#if>>${(opt.k)!''}</option>
                        </#list>
                    </#if>
                </select>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="entryDate">入职日期 </label>
        <div class="col-md-9">
			<input type="text" id="entryDate" name="entryDate" class="form-control datepicker" placeholder=""
                   value="<#if (model.entryDate)??>${model.entryDate?date}</#if>" 
				   >
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="examTime">测评时间 <@required /></label>
        <div class="col-md-9">
			<input type="text" id="examTime" name="examTime" class="form-control datetimepicker" placeholder=""
                   value="<#if (model.examTime)??>${model.examTime?datetime}</#if>" required="true"
				   >
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="avatar">头像 </label>
        <div class="col-md-9">
	  		<#if (model.avatar)??>
                <input type="hidden" name="avatar" value="${(model.avatar)!''}">
                <div class="thumbnail"
                     style="width: 120px; height: 120px; line-height: 20px; margin-right: 20px;">
                    <img src="${(model.avatar)!''}" id="avatar" width="120" height="120">
                </div>
                <div>
                    <button class="btn btn-default btn-file"> <span
                            class="fileinput-new"><i class="fa fa-plus"></i>更换封面
									(120*120)</span> <input type="file" class="imgUpload" data-target="avatar" name="avatar_file"></button>
                </div>
                <#else>
                    <div class="fileinput-preview thumbnail" data-trigger="fileinput"
                         style="width: 120px; height: 120px; margin-right: 20px;">
                        <img src="http://placehold.it/120x120/" id="avatar" width="120" height="120"
                             alt="...">
                    </div>
                    <div>
                        <button class="btn btn-default btn-file"> <span
                                class="fileinput-new"><i class="fa fa-plus"></i>上传封面
					(120*120)</span> <input type="file" class="imgUpload" data-target="avatar" name="avatar_file"></button>
                    </div>
            </#if>
        </div>
            </div>
		<div class="form-group">
        <label class="col-md-2 control-label" for="examtest">测试23 </label>
        <div class="col-md-9">
			<input type="text" id="examtest" name="examtest" class="form-control " placeholder=""
                   value="<#if (model.examtest)??>${model.examtest}</#if>" 
		   data-fv="Length(-100)">
        </div>
            </div>
	    <div class="form-group">
            <label class="col-md-2 control-label" for="description">描述 </label>
            <div class="col-md-10">
						<div class="editormd">
                            <textarea class="editormd-markdown-textarea" name="Md">${(editableTextMap['description']['srcContent'])!''}</textarea>
                            <#-- html textarea 需要开启配置项 saveHTMLToTextarea == true -->
                            <textarea class="editormd-html-textarea" name="description"></textarea>
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