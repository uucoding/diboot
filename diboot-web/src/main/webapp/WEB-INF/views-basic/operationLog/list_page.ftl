<form method="get" action="${ctx.contextPath}/operationLog/list/">
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>来源</th>
                <th>用户</th>
                <th>操作</th>
                <th>备注</th>
                <th>操作对象</th>
                <th>操作时间</th>
                <th class="col-operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <select name="source" class="form-control search-field">
                        <option value="">-选择-</option>
                        <#if sources??>
                            <#list sources as source>
                                <option value='${source.itemValue}' <#if (criteria.source)?? && criteria.source == source.itemValue>selected='selected'</#if>>${source.itemName}</option>
                            </#list>
                        </#if>
                    </select>
                </td>
                <td>
                    <input class="form-control search-field" name="userId" value="${(criteria.userId)!""}">
                </td>
                <td>
                    <select name="operation" class="form-control search-field">
                        <option value="">-选择-</option>
                        <#if operations??>
                            <#list operations as operation>
                                <option value='${operation.itemValue}' <#if (criteria.operation)?? && criteria.operation == operation.itemValue>selected='selected'</#if>>${operation.itemName}</option>
                            </#list>
                        </#if>
                    </select>
                </td>
                <td>
                    <input class="form-control search-field" name="comment" value="${(criteria.comment)!""}">
                </td>
                <td>
                    <input class="form-control search-field" name="relObjType" value="${(criteria.relObjType)!""}">
                </td>
                <td>
                    <input class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
                </td>
                <td>
                    <button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
                    <a href="${ctx.contextPath}/operationLog/list/" class="btn btn-default btn-sm loading-animation" title="重置查询条件"><i class="fa fa-refresh"></i></a>
                </td>
            </tr>
            <#if modelList??>
                <#list modelList as model>
                <tr>
                    <td>
                        <#if (model.source)??>
                            <#if sources??>
                                <#list sources as source>
                                    <#if model.source == source.itemValue>
                                        ${source.itemName!""}
                                    </#if>
                                </#list>
                            <#else>
                                ${model.source}
                            </#if>
                        </#if>
                    </td>
                    <td>${(model.userType)!""}:${(model.userId)!""}</td>
                    <td>
                        <#if (model.operation)??>
                            <#if operations??>
                                <#list operations as operation>
                                    <#if model.operation == operation.itemValue>
                                        ${operation.itemName!""}
                                    </#if>
                                </#list>
                            <#else>
                                ${model.operation}
                            </#if>
                        </#if>
                    </td>
                    <td><@tooltip content=(model.comment)!'' /></td>
                    <td>${(model.relObjType)!""}:${(model.relObjId)!""}</td>
                    <td><#if (model.createTime)??>${model.createTime?datetime}</#if></td>
                    <td>
                        <a href="${ctx.contextPath}/operationLog/view/${model.id}" class="btn btn-default btn-xs" title="查看详细"><i class="fa fa-search-plus"></i> 查看</a>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</form>