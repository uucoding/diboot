<form class="form-horizontal" method="POST" >
    <@portletBody>
    <div class="form-group">
        <label class="col-md-2 control-label" for="title">标题 </label>
        <div class="col-md-4">
			<input type="text" id="title" name="title" class="form-control " placeholder=""
                   value="<#if (model.title)??>${model.title}</#if>" 
            data-fv="Length(-100)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="content">内容 </label>
        <div class="col-md-10">
            <div id="contentEditor" class="rich-text" data-field="#content"></div>
            <textarea class="hidden" id="content" name="content">${(model.content)!""}</textarea>
        </div>
        <label class="col-md-2 control-label" for="content2">内容2 </label>
        <div class="col-md-10">
            <div id="content2Editor" class="rich-text" data-field="#content2"></div>
            <textarea class="hidden" id="content2" name="content2">${(model.content2)!""}</textarea>
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