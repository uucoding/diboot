<form class="form-horizontal" method="post">
<@portletBody>
    <div class="form-group">
        <label class="col-md-2 control-label" for="teacherId">老师 </label>
        <div class="col-md-9">
            <input type="text" id="teacherId" name="teacherId" class="form-control " placeholder=""
                   value="<#if (model.teacherId)??>${model.teacherId}</#if>"
                   data-fv="Number">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="gendar">性别 <@required /></label>
        <div class="col-md-9">
            <input type="text" id="gendar" name="gendar" class="form-control " placeholder=""
                   value="<#if (model.gendar)??>${model.gendar}</#if>" required="true"
                   data-fv="NotNull,Length(-1)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="testAdd">测试 </label>
        <div class="col-md-9">
            <input type="text" id="testAdd" name="testAdd" class="form-control " placeholder=""
                   value="<#if (model.testAdd)??>${model.testAdd}</#if>"
                   data-fv="Length(-100)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="idcard">身份证号 </label>
        <div class="col-md-9">
            <input type="text" id="idcard" name="idcard" class="form-control " placeholder=""
                   value="<#if (model.idcard)??>${model.idcard}</#if>"
                   data-fv="Length(-18)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="cellphone">手机号码 </label>
        <div class="col-md-9">
            <input type="text" id="cellphone" name="cellphone" class="form-control " placeholder=""
                   value="<#if (model.cellphone)??>${model.cellphone}</#if>"
                   data-fv="Length(-20)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="realname">姓名 <@required /></label>
        <div class="col-md-9">
            <input type="text" id="realname" name="realname" class="form-control " placeholder=""
                   value="<#if (model.realname)??>${model.realname}</#if>" required="true"
                   data-fv="NotNull,Length(-50)">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="status">状态 </label>
        <div class="col-md-9">
            <input type="text" id="status" name="status" class="form-control " placeholder=""
                   value="<#if (model.status)??>${model.status}</#if>"
                   data-fv="Length(-20)">
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-9 col-md-offset-2">
            <div id="summernote" class="rich-text"></div>
        </div>
    </div>
</@portletBody>
<@portletFooter>
    <div class="col-md-offset-2 col-md-2">
        <#if (model.id)??>
            <button class="btn btn-primary btn-block" type="submit"> 提交更新</button>
        <#else>
            <button class="btn btn-success btn-block" type="submit"> 提交</button>
        </#if>
    </div>
</@portletFooter>
</form>