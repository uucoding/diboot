<form class="form-horizontal" method="post">
	<div class="form-group">
		<label class="col-md-2 control-label" for="name">名称 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="name" name="name" class="form-control " placeholder="名称" 
				value="<#if (model.name)??>${model.name}</#if>" required="true" 
		  		data-fv="NotNull,Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="shortName">简称 <@required /></label>
		<div class="col-md-9">
			<input type="text" id="shortName" name="shortName" class="form-control " placeholder="简称" 
				value="<#if (model.shortName)??>${model.shortName}</#if>" required="true" 
		  		data-fv="NotNull,Length(-50)">
		</div>
	</div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="parentId">上级单位</label>
        <div class="col-md-9">
            <select class="form-control select2" name="parentId">
                <option value="0">- 无 -</option>
			<#if options??>
				<#list options as opt>
					<#if !(model.id)?? || model.id != opt["v"]>
                        <option value="${opt["v"]}" <#if (model.parentId)?? && model.parentId==opt["v"]>selected</#if>>${opt["k"]}</option>
					</#if>
				</#list>
			</#if>
            </select>
        </div>
    </div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="logo">Logo </label>
		<div class="col-md-9">
			<input type="text" id="logo" name="logo" class="form-control " placeholder="Logo" 
				value="<#if (model.logo)??>${model.logo}</#if>"  
		  		data-fv="Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="address">地址 </label>
		<div class="col-md-9">
			<input type="text" id="address" name="address" class="form-control " placeholder="地址" 
				value="<#if (model.address)??>${model.address}</#if>"  
		  		data-fv="Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="telphone">电话 </label>
		<div class="col-md-9">
			<input type="text" id="telphone" name="telphone" class="form-control " placeholder="电话" 
				value="<#if (model.telphone)??>${model.telphone}</#if>"  
		  		data-fv="Length(-20)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="email">Email </label>
		<div class="col-md-9">
			<input type="text" id="email" name="email" class="form-control " placeholder="Email" 
				value="<#if (model.email)??>${model.email}</#if>"  
		  		data-fv="Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="fax">传真 </label>
		<div class="col-md-9">
			<input type="text" id="fax" name="fax" class="form-control " placeholder="传真" 
				value="<#if (model.fax)??>${model.fax}</#if>"  
		  		data-fv="Length(-50)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="website">网址 </label>
		<div class="col-md-9">
			<input type="text" id="website" name="website" class="form-control " placeholder="网址" 
				value="<#if (model.website)??>${model.website}</#if>"  
		  		data-fv="Length(-255)">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="comment">备注 </label>
		<div class="col-md-9">
			<input type="text" id="comment" name="comment" class="form-control " placeholder="备注" 
				value="<#if (model.comment)??>${model.comment}</#if>"  
		  		data-fv="Length(-255)">
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