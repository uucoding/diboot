<form method="get" action="${ctx.contextPath}/student/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>编号</th>
    	<th>姓名</th>
    	<th>性别</th>
    	<th>特长生</th>
    	<th>测试</th>
    	<th>电话</th>
    	<th>微信</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
		<input type="text" class="form-control search-field " name="id" value="${(criteria.id)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="name" value="${(criteria.name)!""}">
		</td>
    	<td>
		<#-- 引用元数据: GENDER -->
		<select class="form-control search-field select2" name="gendar" style="width: 100%;">
			<option value="">- 不限 -</option>
			<#if gendarOpts??><#list gendarOpts as opt>
			<option value="${(opt.v)!''}"<#if (criteria.gendar)?? && criteria.gendar == opt.v> selected</#if>>${(opt.k)!''}</option>
			</#list></#if>
		</select>
		</td>
    	<td>
		<select name="village" class="form-control search-field">
			<option value="">- 不限 -</option>
			<option value="1" <#if criteria.village?? && criteria.village=='1'>selected</#if>>是</option>
			<option value="0" <#if criteria.village?? && criteria.village=='0'>selected</#if>>否</option>
		</select>
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="testuid1" value="${(criteria.testuid1)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="phone" value="${(criteria.phone)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="wechat" value="${(criteria.wechat)!""}">
		</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/student/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			<#if (model.id)??>${model.id}</#if>
	    </td>
    	<td>
			<#if (model.name)??>${model.name}</#if>
	    </td>
    	<td>
		    <#if model.gendar?? && gendarMap[model.gendar]??>${gendarMap[model.gendar]}</#if>
	    </td>
    	<td>
			${model.village?string('是','否')}
	    </td>
    	<td>
			<#if (model.testuid1)??>${model.testuid1}</#if>
	    </td>
    	<td>
			<#if (model.phone)??>${model.phone}</#if>
	    </td>
    	<td>
			<#if (model.wechat)??>${model.wechat}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/student/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="${ctx.contextPath}/student/update/${model.id}" class="btn btn-default btn-xs" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D")>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/student/delete/${model.id}">
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