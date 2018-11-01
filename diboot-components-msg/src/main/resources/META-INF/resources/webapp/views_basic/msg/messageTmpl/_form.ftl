<form class="form-horizontal" method="post" id="app" style="display: none;">
<#--
<div class="form-group">
    <label class="col-md-2 control-label" for="type">类型 <@required /></label>
    <div class="col-md-9">
        <input type="text" id="type" name="type" class="form-control " placeholder="类型"
           value="<#if (model.type)??>${model.type}</#if>" required="true"
           data-fv="NotNull,Length(-50)">
    </div>
</div>-->
    <div class="form-group">
        <label class="col-md-2 control-label" for="code">模板编码 <@required /></label>
        <div class="col-md-9">
		<#if (model.system)?? && model.system>
            <input type="text" id="code" class="form-control" placeholder="模板编码"
                   value="<#if (model.code)??>${model.code}</#if>" disabled>
            <input name="code" type="hidden" value="<#if (model.code)??>${model.code}</#if>" />
            <input name="system" type="hidden" value="true" />
		<#else>
            <select @change="getTmplVaribles" v-model="tmplCode" name="code" id="code" class="form-control">
                <option value="">-请选择模板-</option>
                <#if (tmplCodeList)??>
					<#list tmplCodeList as opt>
				<option value="${(opt.itemValue)!''}"<#if (model.code)?? && model.code==opt.itemValue> selected</#if>>${(opt.itemName)!''}</option>
					</#list>
				</#if>
            </select>
		</#if>
        </div>
    </div>
<#--
<div class="form-group">
    <label class="col-md-2 control-label" for="orgId">适用单位 <@required /></label>
    <div class="col-md-9">
        <input type="text" id="orgId" name="orgId" class="form-control " placeholder="适用单位"
           value="<#if (model.orgId)??>${model.orgId}</#if>" required="true"
           data-fv="NotNull">
    </div>
</div>-->
    <div class="form-group">
        <label class="col-md-2 control-label" for="title">标题 <@required /></label>
        <div class="col-md-9">
            <input type="text" id="title" name="title" class="form-control " placeholder="标题"
                   value="<#if (model.title)??>${model.title}</#if>"
                   data-fv="NotNull,Length(-255)">
        </div>
    </div>
    <div class="form-group" v-if="varibles.length > 0">
        <label class="col-md-2 control-label">模板变量</label>
        <div class="col-md-9">
            <div class="row">
                <div class="col-md-12">
                    <a v-for="v in varibles" @click="addVarible(v)" href="javascript:;" class="btn default blue-stripe margin-r-5">{{ v }}</a>
                    <a href="javascript:;" class="btn default green-stripe">提示：点击左侧标签添加模板变量</a>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="content">内容 <@required /></label>
        <div class="col-md-9">
			<textarea v-model="content" id="content" name="content" class="form-control" placeholder="内容" required="true"
                      data-fv="NotNull,Length(-512)"><#if (model.content)??>${model.content}</#if></textarea>
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