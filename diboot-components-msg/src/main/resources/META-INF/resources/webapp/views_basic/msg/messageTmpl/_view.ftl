<div class="table-responsive">
    <table class="table table-striped">
	<tbody>
		<tr>
	  <td class="td-label">ID</td>
	  <td>
            <#if (model.id)??>
				${model.id}
            </#if>
	  </td>
	</tr>
	<#--
	<tr>
	  <td class="td-label">类型</td>
	  <td>
			${(model.type)!""}
	  </td>
	</tr>-->
	<tr>
	  <td class="td-label">模板编码</td>
	  <td>
			${(model.code)!""}
	  </td>
	</tr>
	<#--
	<tr>
	  <td class="td-label">适用单位</td>
	  <td>
            <#if (model.orgId)??>
				${model.orgId}
            </#if>
	  </td>
	</tr>-->
	<tr>
	  <td class="td-label">标题</td>
	  <td>
			${(model.title)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">内容</td>
	  <td>
			${(model.contentHtml)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">备注</td>
	  <td>
			${(model.comment)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">有效</td>
	  <td>
            <#if (model.active)??>
				${model.active?string('是','否')}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">创建人</td>
	  <td>
            <#if (model.ext1)??>
				${model.ext1}
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