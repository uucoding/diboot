<form class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-md-2 control-label" for="realname">姓名 <span class="font-red">*</span></label>
        <div class="col-md-9">
            <div class="input-icon right">
                <input type="text" id="realname" name="realname" class="form-control" placeholder="姓名"
                       value="<#if (model.realname)??>${model.realname}</#if>"
                       data-fv="NotNull,Length(-255)">
            </div>
        </div>
    </div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="username">用户名 <@required/></label>
		<div class="col-md-9">
			<div class="input-icon right">
			<input type="text" id="username" name="username" class="form-control" placeholder="用户名" 
				value="<#if (model.username)??>${model.username}</#if>" required="true" 
		  		data-fv="NotNull,Length(-45)">
		  	</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="password">密码 <span class="font-red">*</span></label>
		<div class="col-md-9">
			<#if model?? && model.id??>
				<a id="changepwdBtn" href="#" class="btn btn-link">修改密码</a>
				<div class="input-group hide" id="password_div">
					<input type="password" id="password" name="password" class="form-control" placeholder="新密码 (6位及以上，包含大小写字母及数字)"
						   value="<#if (model.password)??>${model.password}</#if>" data-original="${(model.password)!''}" required="true" data-fv="NotNull,Password">
                    <span class="input-group-addon">
						<a  href="#" class="password-btn"><i class="fa fa-eye-slash"></i></a>
					</span>
				</div>
			<#else>
				<div class="input-group">
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码 (6位及以上，包含大小写字母及数字)"
                           value="<#if (model.password)??>${model.password}</#if>" data-original="${(model.password)!''}" required="true" data-fv="NotNull,Password">
                    <span class="input-group-addon">
						<a  href="#" class="password-btn"><i class="fa fa-eye-slash"></i></a>
					</span>
				</div>
			</#if>
		</div>
	</div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="phone">手机号码 <span class="font-red">*</span></label>
        <div class="col-md-9">
            <div class="input-icon right">
                <input type="text" id="phone" name="phone" class="form-control" placeholder="手机号码"
                       value="<#if (model.phone)??>${model.phone}</#if>"
                       data-fv="Phone,NotNull,Length(-20)">
            </div>
        </div>
    </div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="wechat">微信帐号</label>
		<div class="col-md-9">
			<div class="input-icon right">
			<input type="text" id="wechat" name="wechat" class="form-control" placeholder="微信帐号" 
				value="<#if (model.wechat)??>${model.wechat}</#if>"  
		  		data-fv="Length(-50)">
		  	</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="email">Email </label>
		<div class="col-md-9">
			<div class="input-icon right">
			<input type="text" id="email" name="email" class="form-control" placeholder="Email邮箱" 
				value="<#if (model.email)??>${model.email}</#if>" data-fv="Email">
			</div>
		</div>
	</div>
    <div class="form-group">
        <label class="col-md-2 control-label" for="role">用户角色 <span class="font-red">*</span></label>
        <div class="col-md-9">
            <div class="input-icon right">
                <select name="roleList" class="form-control select2" placeholder="用户角色" data-fv="NotNull" multiple>
                <#if roles??>
                    <#list roles as role>
                        <#assign selected=false />
                        <#if (model.roleList)?? && model.roleList?size gt 0>
                            <#list model.roleList as r>
                                <#if r == role.itemValue>
                                    <#assign selected=true />
                                </#if>
                            </#list>
                        </#if>
                        <option value="${role.itemValue}" <#if selected>selected</#if>>${role.itemName}</option>
                    </#list>
                    </#if>
                </select>
            </div>
        </div>
    </div>
	<div class="form-group">
		<label class="col-md-2 control-label" for="enabled">账号状态 <span class="font-red">*</span></label>
		<div class="col-md-9">
			<select id="enabled" name="enabled" class="form-control">
				<option value="0" <#if (model.enabled)?? && !model.enabled>selected="selected"</#if>> 锁定 (不可登录)</option>
				<option value="1" <#if (model.enabled)?? && model.enabled>selected="selected"</#if>> 正常(可登录) </option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-md-offset-2 col-md-2">
			<#if model??>
			<button class="btn btn-primary btn-block" type="submit"> 提交更新 </button>
			<#else>
			<button class="btn btn-success btn-block" type="submit"> 提交 </button>			
			</#if>
		</div>
	</div>
</form>