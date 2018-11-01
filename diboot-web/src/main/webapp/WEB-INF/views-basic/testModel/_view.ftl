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
		<td class="td-label">常规长整数</td>
		<td>
            <#if (model.normalLng)??>${model.normalLng}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">关联表长整数</td>
		<td>
            ${(model.studentName)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">常规数字</td>
		<td>
            <#if (model.normalInt)??>${model.normalInt}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">元数据布尔</td>
		<td>
            <#if model.metaBool?? && metaBoolMap[model.metaBool?string]??>${metaBoolMap[model.metaBool?string]}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">常规日期</td>
		<td>
            <#if (model.normalDate)??>${model.normalDate?date}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">常规时间</td>
		<td>
            <#if (model.normalDatetime)??>${model.normalDatetime?datetime}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">浮点数字</td>
		<td>
            <#if (model.normalDouble)??>${model.normalDouble}</#if>
        </td>
            </tr>
		<tr>
		<td class="td-label">常规文本域</td>
		<td>
            ${(model.normalStr)!""}
        </td>
            </tr>
		<tr>
		<td class="td-label">元数据域</td>
		<td>
            <#if model.metaStr?? && metaStrMap[model.metaStr]??>${metaStrMap[model.metaStr]}</#if>
        </td>
            </tr>
			<tr>
                <td class="td-label">多内容域</td>
                <td colspan="1">
					${(model.textareaStr)!""}
                </td>
            </tr>
		<tr>
		<td class="td-label">图片上传域</td>
		<td>
            <a class="example-image-link" href="${(model.imgUploadStr)!''}" data-lightbox="example-set" data-title="图片上传域">
                <img src="${(model.imgUploadStr)!''}" alt="图片上传域" style="width: auto; height: 120px;">
            </a>
        </td>
            </tr>
		<tr>
		<td class="td-label">文件上传域</td>
		<td>
            <a href="${(model.fileUploadStr)!''}" class="btn btn-default">下载文件</a>
        </td>
            </tr>
			<tr>
                <td class="td-label">富文本域</td>
                <td colspan="1">
					${(model.richtextStr)!""}
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

