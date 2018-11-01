<@portletBody>
<form class="form-horizontal" method="post" action="${ctx.contextPath}/timerTask/create">
	<div class="form-group">
		<label class="col-md-1 col-md-offset-1 control-label" for="executor">任务类型 <@required /></label>
		<div class="col-md-2">
            <select id="executor" name="executor" class="form-control" placeholder="任务类型" data-fv="NotNull">
                <option value="">- 选择 -</option>
                <#if taskExecutorList??>
					<#list taskExecutorList as task>
					<#if task.visible>
                	<option value="${task.executor}" <#if (criteria.executor)?? && criteria.executor==task.executor>selected</#if>>${task.type}</option>
					</#if>
					</#list>
				</#if>
            </select>
		</div>
		<label class="col-md-1 control-label" for="scheduleTime">定时时间 <@required /></label>
		<div class="col-md-2">
			<input type="text" id="scheduleTime" name="scheduleTime" class="form-control datetimepicker" placeholder=""
				   value="<#if (model.scheduleTime)??>${model.scheduleTime?date}</#if>" required="true">
		</div>
		<button class="btn btn-success col-md-2" type="submit"> 预约执行 </button>
	</div>
</form>
</@portletBody>
