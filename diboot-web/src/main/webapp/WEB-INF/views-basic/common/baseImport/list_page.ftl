<form method="get" action="${ctx.contextPath}/${indexPageUrl}/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>文件名</th>
    	<th>大小</th>
    	<th>数据量</th>
        <th>备注</th>
    	<th>状态</th>
    	<th>上传时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
            <input type="text" class="form-control search-field " name="name" value="${(criteria.name)!""}">
    	</td>
    	<td>
             -
    	</td>
    	<td>
            -
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
			<button type="submit" class="btn btn-info btn-sm loading-animation">查询</button>
	    	<a href="${ctx.contextPath}/${indexPageUrl}/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			<a href="${ctx.contextPath}${model.link}">${(model.name)!""}</a>
	    </td>
    	<td>
			<#if (model.size)??>${(model.size)/1024} KB<#else>-</#if>
	    </td>
    	<td>
			${model.dataCount!0}
	    </td>
        <td>
			${(model.comment)!"-"}
        </td>
    	<td>
			${(model.statusLabel)!"-"}
	    </td>
    	<td>
			<#if (model.createTime)??>${model.createTime?datetime}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/${indexPageUrl}/view/${model.uuid}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-eye"></i></a>
      		</#if>
      	</td>
    </tr>
    </#list>
    </#if>
	</tbody>
</table>
</div>
</form>