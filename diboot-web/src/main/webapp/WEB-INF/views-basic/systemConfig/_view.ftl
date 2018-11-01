<div class="table-responsive">
    <table class="table table-striped">
	<tbody>
		<tr>
	  <td class="td-label">编号</td>
	  <td>
            <#if (model.id)??>
				${model.id}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">关联对象和ID</td>
	  <td>
			${(model.relObjType)!""}<#if (model.relObjId)??> : ${model.relObjId}</#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">类别</td>
	  <td>
          <span class="label label-sm label-info">${(model.category)!"-"}</span>
		  -
          <span class="label label-sm label-info">${(model.subcategory)!"-"}</span>
	  </td>
	</tr>
	<tr>
		<td class="td-label">配置属性</td>
		<td>
			<#if (model.extdataMap)??>
				<#list model.extdataMap?keys as key>
					<b>${key}</b> = ${model.extdataMap[key]} <br>
				</#list>
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
	<tr>
	  <td class="td-label">更新时间</td>
	  <td>
            <#if (model.updateTime)??>
				${model.updateTime?datetime}
            </#if>
	  </td>
	</tr>
	</tbody>
</table>
</div>

