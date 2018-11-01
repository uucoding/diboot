<form method="get" action="${ctx.contextPath}/testModel/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
	<th><label class="label-checkbox"><input type="checkbox" id="selectAll" value="" /><span></span></label></th>
    	<th>编号</th>
    	<th>常规长整数</th>
    	<th>关联表长整数</th>
    	<th>常规数字</th>
    	<th>元数据布尔</th>
    	<th>常规日期</th>
    	<th>常规时间</th>
    	<th>浮点数字</th>
    	<th>常规文本域</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
		<td>#</td>
    	<td>
		<input type="text" class="form-control search-field " name="id" value="${(criteria.id)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="normalLng" value="${(criteria.normalLng)!""}">
		</td>
    	<td>
		<#-- referenceLng = student.id, 字段显示为: name -->
		<select name="referenceLng" class="form-control search-field<#if !(referenceLngOpts??)> select2-ajax<#else> select2</#if>" data-url="${ctx.contextPath}/testModel/smartSearch" data-model="Student" data-field="id" data-label="name" data-textfield="name" data-text="${(model.name)!''}">
			<option value="">- 不限 -</option>
			<#if referenceLngOpts??>
			<#list referenceLngOpts as opt>
			<option value="${opt.v}" <#if (criteria.referenceLng)?? && criteria.referenceLng==opt.v?string>selected</#if>>${(opt.k)!''}</option>
			</#list>
			</#if>
		</select>
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="normalInt" value="${(criteria.normalInt)!""}">
		</td>
    	<td>
		<select name="metaBool" class="form-control search-field">
			<option value="">- 不限 -</option>
			<option value="1" <#if criteria.metaBool?? && criteria.metaBool=='1'>selected</#if>>是</option>
			<option value="0" <#if criteria.metaBool?? && criteria.metaBool=='0'>selected</#if>>否</option>
		</select>
		</td>
    	<td>
		<input type="text" class="form-control search-field datepicker" name="normalDate" value="${(criteria.normalDate)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field datepicker" name="normalDatetime" value="${(criteria.normalDatetime)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="normalDouble" value="${(criteria.normalDouble)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="normalStr" value="${(criteria.normalStr)!""}">
		</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/testModel/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
		<td><label class="label-checkbox"><input type="checkbox" class="selectAll-child" value="${(model.id)!''}"><span></span></label></td>
    	<td>
			<#if (model.id)??>${model.id}</#if>
	    </td>
    	<td>
			<#if (model.normalLng)??>${model.normalLng}</#if>
	    </td>
    	<td>
			${(model.studentName)!""}
	    </td>
    	<td>
			<#if (model.normalInt)??>${model.normalInt}</#if>
	    </td>
    	<td>
		    <#if model.metaBool?? && metaBoolMap[model.metaBool?string]??>${metaBoolMap[model.metaBool?string]}</#if>
	    </td>
    	<td>
			<#if (model.normalDate)??>${model.normalDate?date}</#if>
	    </td>
    	<td>
			<#if (model.normalDatetime)??>${model.normalDatetime?datetime}</#if>
	    </td>
    	<td>
			<#if (model.normalDouble)??>${model.normalDouble}</#if>
	    </td>
    	<td>
			<#if (model.normalStr)??>${model.normalStr}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="javascript:;" class="btn btn-default btn-xs btn-modal-box" data-url="${ctx.contextPath}/testModel/view/${model.id}" data-title="查看" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="javascript:;" class="btn btn-default btn-xs btn-modal-box" data-url="${ctx.contextPath}/testModel/update/${model.id}" data-title="更新" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D")>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/testModel/delete/${model.id}">
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