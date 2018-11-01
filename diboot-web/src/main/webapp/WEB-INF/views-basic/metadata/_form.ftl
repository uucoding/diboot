<form class="form-horizontal" method="post">
	<div class="form-group">
		<label class="col-md-2 control-label" for="itemName">元数据类型名称 <@required /></label>
		<div class="col-md-9">
			<div class="input-icon right">
			<input type="text" id="itemName" name="itemName" class="form-control" placeholder="元数据类型名称" 
				value="<#if (model.itemName)??>${model.itemName}</#if>"  
		  		data-fv="NotNull,Length(-255)">
			<input type="hidden" name="parentId" value="0">
			</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="type">元数据类型编码 <@required /></label>
		<div class="col-md-9">
			<#if (model.type)??>
			<div class="input-icon right">
			<input type="text" class="form-control" disabled="disabled" value="${model.type}">
			<input type="hidden" id="type" name="type" value="${model.type}">
			</div>
			<#else>
			<div class="input-icon right">
			<input type="text" id="type" name="type" class="form-control" placeholder="元数据类型编码(大写英文字母)" 
				value="<#if (model.type)??>${model.type}</#if>" required="true" 
		  		data-fv="NotNull,Length(-50)">			
		  	</div>
			</#if>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="active">包含子项 <@required /></label>
		<div class="col-md-5">
			<div id="itemWrapper">
			<#if (model.children)??>
				<#list model.children as item>
				<div class='alert alert-info'>
					<#if !item.system>
					<button aria-hidden='true' data-dismiss='alert' class='close' type='button'>×</button>
					</#if>
					${item.itemName} <#if item.itemValue??>(${item.itemValue})</#if>
					<input type='hidden' name='items' value='${item.id}_${item.itemName}_${(item.itemValue)!""}' />
				</div>
				</#list>
			</#if>
			</div>
			<div class="input-group row">
				<div class="col-md-8">
                	<input type="text" id="newitemname" class="form-control" placeholder="子项名称">
                </div>
                <div class="col-md-4">
                	<input type="text" id="newitemvalue" class="form-control" placeholder="子项编码">
                </div>
                <div class="input-group-btn">
                	<button type="button" class="btn btn-primary btn-additem">添加子项</button>
                </div>
              </div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="comment">备注 </label>
		<div class="col-md-9">
			<div class="input-icon right">
			<input type="text" id="comment" name="comment" class="form-control" placeholder="备注" 
				value="<#if (model.comment)??>${model.comment}</#if>"  
		  		data-fv="Length(-255)">
		  	</div>
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