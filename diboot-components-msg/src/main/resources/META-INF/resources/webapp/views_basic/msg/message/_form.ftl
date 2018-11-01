<form class="form-horizontal" method="post">
		<div class="form-group">
		<label class="col-md-2 control-label" for="type">消息类型 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="type" name="type" class="form-control " placeholder="消息类型"
			   value="<#if (model.type)??>${model.type}</#if>" required="true"
			   data-fv="NotNull,Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="tmplId">模板id <@required /></label>
		<div class="col-md-9">
			<input type="text" id="tmplId" name="tmplId" class="form-control " placeholder="模板id"
			   value="<#if (model.tmplId)??>${model.tmplId}</#if>" required="true"
			   data-fv="NotNull">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="businessType">关联业务类型 </label>
		<div class="col-md-9">
			<input type="text" id="businessType" name="businessType" class="form-control " placeholder="关联业务类型"
			   value="<#if (model.businessType)??>${model.businessType}</#if>" 
			   data-fv="Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="businessId">关联业务ID </label>
		<div class="col-md-9">
			<input type="text" id="businessId" name="businessId" class="form-control " placeholder="关联业务ID"
			   value="<#if (model.businessId)??>${model.businessId}</#if>" 
			   data-fv="">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="sender">发送人 </label>
		<div class="col-md-9">
			<input type="text" id="sender" name="sender" class="form-control " placeholder="发送人"
			   value="<#if (model.sender)??>${model.sender}</#if>" 
			   data-fv="Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="receiver">接收人 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="receiver" name="receiver" class="form-control " placeholder="接收人"
			   value="<#if (model.receiver)??>${model.receiver}</#if>" required="true"
			   data-fv="NotNull,Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="title">标题 </label>
		<div class="col-md-9">
			<input type="text" id="title" name="title" class="form-control " placeholder="标题"
			   value="<#if (model.title)??>${model.title}</#if>" 
			   data-fv="Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="content">内容 <@required /></label>
		<div class="col-md-9">
			<textarea id="content" name="content" class="form-control" placeholder="内容" required="true"
			data-fv="NotNull,Length(-512)"><#if (model.content)??>${model.content}</#if></textarea>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="url">链接 </label>
		<div class="col-md-9">
			<input type="text" id="url" name="url" class="form-control " placeholder="链接"
			   value="<#if (model.url)??>${model.url}</#if>" 
			   data-fv="Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="status">发送状态 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="status" name="status" class="form-control " placeholder="发送状态"
			   value="<#if (model.status)??>${model.status}</#if>" required="true"
			   data-fv="NotNull,Length(-20)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="sendType">发送类型 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="sendType" name="sendType" class="form-control " placeholder="发送类型"
			   value="<#if (model.sendType)??>${model.sendType}</#if>" required="true"
			   data-fv="NotNull,Length(-32)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="response">发送结果 </label>
		<div class="col-md-9">
			<input type="text" id="response" name="response" class="form-control " placeholder="发送结果"
			   value="<#if (model.response)??>${model.response}</#if>" 
			   data-fv="Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-2 col-md-2">
			<#if (model.id)??>
			<button class="btn btn-primary btn-block" type="submit"> 提交更新 </button>
			<#else>
			<button class="btn btn-success btn-block" type="submit"> 提交 </button>			
			</#if>
		</div>
	</div>
</form>