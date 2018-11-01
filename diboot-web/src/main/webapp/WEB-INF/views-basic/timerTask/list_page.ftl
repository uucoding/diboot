<form method="get" action="${ctx.contextPath}/timerTask/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>ID</th>
    	<th>任务类型</th>
    	<th>定时时间</th>
    	<th>状态</th>
    	<th>开始时间</th>
        <th>结束时间</th>
		<th>备注</th>
    	<th>创建时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
             -
    	</td>
    	<td>
            <select  class="form-control search-field " name="executor">
                <option value="">- 选择 -</option>
				<#if taskExecutorList??>
					<#list taskExecutorList as task>
						<#if task.visible>
                	<option value="${task.executor}" <#if (criteria.executor)?? && criteria.executor==task.executor>selected</#if>>${task.type}</option>
						</#if>
					</#list>
				</#if>
            </select>
    	</td>
    	<td>
            <input type="text" class="form-control search-field datepicker" name="scheduleTime" value="${(criteria.scheduleTime)!""}">
    	</td>
    	<td>
			<select  class="form-control search-field " name="status">
				<option value="">- 选择 -</option>
                <option value="NEW" <#if (criteria.status)?? && criteria.status=="NEW">selected</#if>>待处理</option>
                <option value="DOING" <#if (criteria.status)?? && criteria.status=="DOING">selected</#if>>处理中</option>
                <option value="SUCCESS" <#if (criteria.status)?? && criteria.status=="SUCCESS">selected</#if>>成功</option>
                <option value="FAIL" <#if (criteria.status)?? && criteria.status=="FAIL">selected</#if>>失败</option>
			</select>
    	</td>
    	<td>
            <input type="text" class="form-control search-field datepicker" name="beginTime" value="${(criteria.beginTime)!""}">
    	</td>
        <td>
            <input type="text" class="form-control search-field datepicker" name="endTime" value="${(criteria.endTime)!""}">
        </td>
    	<td>
			<input type="text" class="form-control search-field " name="comment" value="${(criteria.comment)!""}">
    	</td>
    	<td>
			<input type="text" class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
    	</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/timerTask/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			<#if (model.id)??>${model.id}</#if>
	    </td>
    	<td>
			<#if (model.type)??>${model.type}</#if>
	    </td>
    	<td>
			<#if (model.scheduleTime)??>${model.scheduleTime?datetime}</#if>
	    </td>
    	<td>
			<#if (model.statusLabel)??>${model.statusLabel} <#if (model.progress)?? && model.progress gt 0 && model.status=="DOING">(${model.progress}%)</#if></#if>
	    </td>
        <td>
			<#if (model.beginTime)??>${model.beginTime?datetime}</#if>
        </td>
		<td>
			<#if (model.endTime)??>${model.endTime?datetime}</#if>
	    </td>
    	<td>
			<#if (model.comment)??>${model.comment}</#if>
	    </td>
    	<td>
			<#if (model.createTime)??>${model.createTime?datetime}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/timerTask/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
            <#if features?seq_contains("D") && (model.status)?? && model.status=="NEW">
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/timerTask/delete/${model.id}">
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