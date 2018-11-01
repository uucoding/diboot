<div class="table-responsive">
    <table class="table table-striped">
	<tbody>
		<tr>
	  <td class="td-label">任务id</td>
	  <td>
            <#if (model.id)??>
				${model.id}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">业务类型</td>
	  <td>
			${(model.businessType)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">业务id</td>
	  <td>
            <#if (model.businessId)??>
				${model.businessId}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">任务类型</td>
	  <td>
			${(model.type)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">定时时间</td>
	  <td>
            <#if (model.scheduleTime)??>
				${model.scheduleTime?datetime}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">状态</td>
	  <td>
			${(model.statusLabel)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">开始时间</td>
	  <td>
            <#if (model.beginTime)??>
				${model.beginTime?datetime}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">结束时间</td>
	  <td>
            <#if (model.endTime)??>
				${model.endTime?datetime}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">进度</td>
	  <td>
            <#if (model.progress)??>
				${model.progress}%
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">备注</td>
	  <td>
			${(model.comment)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">是否有效</td>
	  <td>
		  ${model.active?string('是','否')}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">创建人</td>
	  <td>
            <#if (model.createBy)??>
				${model.createBy}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">创建时间</td>
	  <td>
            <#if (model.createTime)??>
				${model.createTime?datetime}
            </#if>
	  </td>
	</tr>
	</tbody>
</table>
</div>

