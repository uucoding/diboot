<div class="table-responsive">
    <table class="table table-striped">
        <tbody>
			<tr>
		<td class="td-label">编号</td>
		<td>
            <#if (model.id)??>${model.id}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">姓名</td>
		<td>
            ${(model.name)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">性别</td>
		<td>
            <#if model.gendar?? && gendarMap[model.gendar]??>${gendarMap[model.gendar]}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">特长生</td>
		<td>
            ${model.village?string('是','否')}
        </td>
            </tr>
		<tr>
		<td class="td-label">测试</td>
		<td>
            <#if (model.testuid1)??>${model.testuid1}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">电话</td>
		<td>
            ${(model.phone)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">微信</td>
		<td>
            ${(model.wechat)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">班主任</td>
		<td>
            ${(model.teacherName)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">入职日期</td>
		<td>
            <#if (model.entryDate)??>${model.entryDate?date}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">测评时间</td>
		<td>
            <#if (model.examTime)??>${model.examTime?datetime}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">头像</td>
		<td>
            <a class="example-image-link" href="${(model.avatar)!''}" data-lightbox="example-set" data-title="头像">
                <img src="${(model.avatar)!''}" alt="头像" style="width: auto; height: 120px;">
            </a>
        </td>
            </tr>
		<tr>
		<td class="td-label">测试23</td>
		<td>
            ${(model.examtest)!""}
        </td>
            </tr>
			<tr>
                <td class="td-label">描述</td>
                <td colspan="1">
					${(model.description)!""}
                </td>
            </tr>
		<tr>
		<td class="td-label">是否有效</td>
		<td>
            ${model.active?string('是','否')}
        </td>
            </tr>
		<tr>
		<td class="td-label">创建人ID</td>
		<td>
            <#if (model.createBy)??>${model.createBy}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">创建时间</td>
		<td>
            <#if (model.createTime)??>${model.createTime?datetime}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">更新时间</td>
		<td>
            <#if (model.updateTime)??>${model.updateTime?datetime}</#if>
        </td>
            </tr>
        </tbody>
    </table>
</div>

