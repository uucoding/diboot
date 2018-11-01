<form method="get" action="${ctx.contextPath}/systemConfig/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>关联对象</th>
        <th>对象ID</th>
    	<th>类别</th>
        <th>子类别</th>
    	<th>配置参数</th>
    	<th>更新时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
			<input type="text" class="form-control search-field " name="relObjType" value="${(criteria.relObjType)!""}">
    	</td>
    	<td>
            <input type="text" class="form-control search-field " name="relObjId" value="${(criteria.relObjId)!""}">
    	</td>
    	<td>
            <input type="text" class="form-control search-field " name="category" value="${(criteria.category)!""}">
    	</td>
        <td>
            <input type="text" class="form-control search-field " name="subcategory" value="${(criteria.subcategory)!""}">
        </td>
        <td>
            -
        </td>
    	<td>
			<input type="text" class="form-control search-field datepicker" name="updateTime" value="${(criteria.updateTime)!""}">
    	</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/systemConfig/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			<#if (model.relObjType)??>${model.relObjType}</#if>
	    </td>
    	<td>
            <#if (model.relObjId)??>${model.relObjId}</#if>
	    </td>
    	<td>
			<#if (model.category)??>${model.category}</#if>
	    </td>
        <td>
			<#if (model.subcategory)??>${model.subcategory}</#if>
        </td>
        <td>
			<#if (model.configItemKeys)??>
				<@tooltip content="${model.configItemKeys}" />
			</#if>
        </td>
    	<td>
			<#if (model.updateTime)??>${model.updateTime?datetime}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/systemConfig/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="${ctx.contextPath}/systemConfig/update/${model.id}" class="btn btn-default btn-xs" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D")>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/systemConfig/delete/${model.id}">
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