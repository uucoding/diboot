<form method="get" action="${ctx.contextPath}/user/list/">
    <div class="table-responsive">
        <table class="table table-striped table-hover header-fixed">
            <thead>
            <tr>
                <th>姓名</th>
                <th>角色</th>
                <th data-field="username" class="field-sort">用户名</th>
                <th>电话</th>
                <th>创建时间</th>
                <th>是否可登录</th>
                <th class="col-operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <input class="form-control search-field" name="realname" value="${(criteria.realname)!""}">
                </td>
                <td>
                    <select name="roles" class="form-control search-field">
                        <option value="">-选择-</option>
                        <#if roles??>
                            <#list roles as role>
                                <option value='${role.itemValue}' <#if (criteria.roles)?? && criteria.roles == role.itemValue>selected='selected'</#if>>${role.itemName}</option>
                            </#list>
                        </#if>
                    </select>
                </td>
                <td>
                    <input class="form-control search-field" name="username" value="${(criteria.username)!""}">
                </td>
                <td>
                    <input class="form-control search-field" name="phone" value="${(criteria.phone)!""}">
                </td>
                <td>
                    <input class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
                </td>
                <td>
                    <select name="enabled" class="form-control search-field">
                        <option value="">-选择-</option>
                        <option value='1' <#if (criteria.enabled)?? && criteria.enabled=='1'>selected='selected'</#if>>是</option>
                        <option value='0' <#if (criteria.enabled)?? && criteria.enabled=='0'>selected='selected'</#if>>否</option>
                    </select>
                </td>
                <td>
                    <button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
                    <a href="${ctx.contextPath}/user/list/" class="btn btn-default btn-sm loading-animation" title="重置查询条件"><i class="fa fa-refresh"></i></a>
                </td>
            </tr>

            <#if modelList??>
                <#list modelList as model>
                <tr>
                    <td>${(model.realname)!""}</td>
                    <td>${(model.roleNames)!""}</td>
                    <td>${model.username}</td>
                    <td>${(model.phone)!""}</td>
                    <td>${(model.createTime)?datetime}</td>
                    <td>${model.enabled?string('是','否')}
                        <#if !model.enabled><i class="fa fa-lock text-yellow" title="该账号不可登录"></i></#if>
                    </td>
                    <td>
                        <a href="${ctx.contextPath}/user/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
                        <#if features?seq_contains("U")>
                        <a href="${ctx.contextPath}/user/update/${model.id}" class="btn btn-default btn-xs" title="更新用户信息"><i class="fa fa-edit"></i></a>
                        </#if>
                        <#if features?seq_contains("D") && (!(Session.user.id)?exists || Session.user.id!=model.id)>
                        <a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该用户吗？" data-url="${ctx.contextPath}/user/delete/${model.id}">
                            <i class="fa fa-close"></i>
                        </a>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</form>
