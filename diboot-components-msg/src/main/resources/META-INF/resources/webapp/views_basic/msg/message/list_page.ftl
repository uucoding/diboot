<form method="get" action="${ctx.contextPath}/msg/message/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>接收号码</th>
    	<th>短信模板</th>
        <th>内容</th>
    	<th>发送状态</th>
    	<th>发送人</th>
    	<th>发送时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
                <input type="text" class="form-control search-field " name="receiver" value="${(criteria.receiver)!""}">
    	</td>
    	<td>
                <input type="text" class="form-control search-field " name="title" value="${(criteria.title)!""}">
    	</td>
        <td>
            -
        </td>
    	<td>
                <input type="text" class="form-control search-field " name="status" value="${(criteria.status)!""}">
    	</td>
    	<td>
                -
    	</td>
    	<td>
                <input type="text" class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
    	</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/msg/message/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			${model.receiver}
	    </td>
    	<td>
			<#if (model.title)??>${model.title}</#if>
	    </td>
        <td>
			<@tooltip content="${model.content}" />
        </td>
    	<td>
			${model.statusLabel}
	    </td>
    	<td>
			${model.creatorName!"-"}
	    </td>
    	<td>
			${model.createTime?datetime}
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/msg/message/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="${ctx.contextPath}/msg/message/update/${model.id}" class="btn btn-default btn-xs" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D")>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/msg/message/delete/${model.id}">
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