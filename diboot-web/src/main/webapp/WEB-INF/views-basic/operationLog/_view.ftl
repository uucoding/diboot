<div class="row">
    <div class="col-md-12">
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <tbody>
                <tr>
                    <td class="td-label">来源</td>
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
                </tr>
                <tr>
                    <td class="td-label">用户</td>
                    <td>${(model.userType)!""}:${(model.userId)!""}</td>
                </tr>
                <tr>
                    <td class="td-label">操作</td>
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
                </tr>
                <tr>
                    <td class="td-label">备注</td>
                    <td>${(model.comment)!""}</td>
                </tr>
                <tr>
                    <td class="td-label">操作对象</td>
                    <td>${(model.relObjType)!""}:${(model.relObjId)!""}</td>
                </tr>
                <tr>
                    <td class="td-label">操作时间</td>
                    <td><#if (model.createTime)??>${(model.createTime)?datetime}</#if></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>