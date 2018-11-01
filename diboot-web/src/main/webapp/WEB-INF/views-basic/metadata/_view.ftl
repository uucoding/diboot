<div class="table-responsive">
	<table class="table table-striped">
		<tbody>
			<tr>
		  <td class="td-label">元数据类型名称</td>
		  <td>
		  	${(model.itemName)!""} 		
		  </td>
		</tr>
		<tr>
		  <td class="td-label">元数据类型编码</td>
		  <td>
		  	${model.type}	  	  			  		
		  </td>
		</tr>
		<tr>
		  <td class="td-label">包含元数据子项</td>
		  <td>
		  	<#if (model.children)??>
				<#list model.children as item>
				<button class="btn btn-default">${item.itemName}<#if item.itemValue??> (${item.itemValue})</#if></button>
				</#list>
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
			<td class="td-label">是否系统预置</td>
			<td>
			${(model.system)?string('是','否')!""}
			</td>
		</tr>
		<tr>
		  <td class="td-label">是否有效</td>
		  <td>
		  	${(model.active)?string('是','否')!""}	  			  		
		  </td>
		</tr>
		<tr>
			<td class="td-label">创建时间</td>
            <td><#if (model.createTime)??>${model.createTime?datetime}</#if>
			</td>
		</tr>
		</tbody>
	</table>
</div>