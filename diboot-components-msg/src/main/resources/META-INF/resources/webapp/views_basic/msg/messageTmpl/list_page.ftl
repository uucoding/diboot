<form method="get" action="${ctx.contextPath}/msg/messageTmpl/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>模板编码</th>
    	<th>标题</th>
    	<th>内容</th>
    	<th>创建人</th>
    	<th>创建时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
                <input type="text" class="form-control search-field " name="code" value="${(criteria.code)!""}">
    	</td>
    	<td>
                <input type="text" class="form-control search-field " name="title" value="${(criteria.title)!""}">
    	</td>
    	<td>
                -
    	</td>
    	<td>
                -
    	</td>
    	<td>
                <input type="text" class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
    	</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/msg/messageTmpl/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			${model.code}
	    </td>
    	<td>
			<#if (model.title)??>${model.title}</#if>
	    </td>
    	<td>
			<#if (model.content)??>
				<@tooltip content="${model.content}" />
			</#if>
	    </td>
    	<td>
			${(model.ext1)!""}
	    </td>
    	<td>
			${model.createTime?datetime}
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/msg/messageTmpl/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="${ctx.contextPath}/msg/messageTmpl/update/${model.id}" class="btn btn-default btn-xs" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D") && !model.system>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/msg/messageTmpl/delete/${model.id}">
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