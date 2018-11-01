<div class="table-responsive">
    <table class="table table-striped">
        <tbody>
        <tr>
            <td class="td-label">编号</td>
            <td >
                <#if (model.id)??>${model.id}</#if>
            </td>
            <td class="td-label">是否有效</td>
            <td >
                ${model.active?string('是','否')}
            </td>
        </tr>
        <tr>
            <td class="td-label">标题</td>
            <td>
                ${(model.title)!""}
            </td>
            <td class="td-label">创建人ID</td>
            <td>
                <#if (model.createBy)??>${model.createBy}</#if>
            </td>
        </tr>
        <tr>
            <td class="td-label">内容</td>
            <td colspan="3">
                ${(model.content)!""}
            </td>
            <td class="td-label">内容2</td>
            <td colspan="3">
                ${(model.content2)!""}
            </td>
        </tr>
        <tr>
            <td class="td-label">创建时间</td>
            <td >
                <#if (model.createTime)??>${model.createTime?datetime}</#if>
            </td>
            <td class="td-label">更新时间</td>
            <td >
                <#if (model.updateTime)??>${model.updateTime?datetime}</#if>
            </td>
        </tr>
        </tbody>
    </table>
</div>

