<@dialog title="添加新角色" id="dialog" action="${ctx.contextPath}/rolemenu/role/create">
<div class="form-body">
    <div class="form-group">
        <label class="col-md-3 control-label">角色名称 <@required /></label>
        <div class="col-md-7">
            <input class="form-control" name="name" placeholder="角色的中文名称" type="text" data-fv="NotNull">
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 control-label">角色编码 <@required /></label>
        <div class="col-md-7">
            <input class="form-control" name="role" placeholder="角色编码(大写英文)" type="text" data-fv="NotNull">
        </div>
    </div>
</div>
</@dialog>