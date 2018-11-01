<form class="form-horizontal" method="post">
	<div class="form-group">
		<label class="col-sm-2 control-label" for="roleId">当前角色</label>
		<div class="col-sm-9">
			<input type="text" id="role" name="role" class="form-control" placeholder="角色" value="${roleMap["k"]}" disabled="disabled">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="menuId">操作权限</label>
		<div class="col-sm-9">
			<#if menuListMap?? && menuListMap?size gt 0>
			    <div class="nav-tabs-custom">
                    <ul class="nav nav-tabs">
						<#list menuListMap?keys as key>
                        <li class="<#if key_index==0>active</#if>"><a href="#tab${key_index}" data-toggle="tab" aria-expanded="<#if key_index==0>true<#else>false</#if>">${key} 权限</a></li>
                        </#list>
                    </ul>
                    <div class="tab-content">
						<#list menuListMap?keys as key>
                        <div class="tab-pane <#if key_index==0>active</#if>" id="tab${key_index}">
							<#if menuListMap[key]??>
								<#list menuListMap[key] as menu>
									<#if menu.children??>
									<label class="bold">${menu.name} :</label>
									<div class="padding-b-10">
										<#list menu.children as c>
											<label for="c${c.id}" class="label-checkbox" style="font-weight: normal !important;">
												<#assign check = false>
												<#if modelList?? && modelList?size gt 0>
													<#list modelList as rm>
														<#if (rm.menuId)?? && rm.menuId == c.id>
															<#assign check = true>
														</#if>
													</#list>
												</#if>
												<input id="c${c.id}" name="menuId" type="checkbox" value="${menu.id}_${c.id}" <#if check>checked="checked"</#if> />
												<span></span>
												${c.name}
											</label>
										</#list>
									</div>
									<#else>
									<label for="m${menu.id}" class="label-checkbox">
										<#assign check = false>
										<#if modelList??>
											<#list modelList as rm>
												<#if (rm.menuId)?? && rm.menuId == menu.id>
													<#assign check = true>
												</#if>
											</#list>
										</#if>
										<input id="m${menu.id}" name="menuId" type="checkbox" value="${menu.id}" <#if check>checked="checked"</#if> />
										<span></span>
										${menu.name}
									</label>
									<br>
									</#if>
								</#list>
							</#if>
                        </div>
						</#list>
                    </div>
                </div>
			</#if>
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