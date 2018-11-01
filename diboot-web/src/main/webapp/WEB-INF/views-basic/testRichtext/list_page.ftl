<form method="get" action="${ctx.contextPath}/testRichtext/">
<div class="table-responsive">
	<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th>编号</th>
    	<th>标题</th>
    	<th>内容</th>
    	<th>内容2</th>
    	<th>是否有效</th>
    	<th>创建时间</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
    	<td>
		<input type="text" class="form-control search-field " name="id" value="${(criteria.id)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="title" value="${(criteria.title)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="content" value="${(criteria.content)!""}">
		</td>
    	<td>
		<input type="text" class="form-control search-field " name="content2" value="${(criteria.content2)!""}">
		</td>
    	<td>
		<select name="active" class="form-control search-field">
			<option value="">- 不限 -</option>
			<option value="1" <#if (criteria.active)?? && criteria.active=='1'>selected</#if>>是</option>
			<option value="0" <#if (criteria.active)?? && criteria.active=='0'>selected</#if>>否</option>
		</select>
		</td>
    	<td>
		<input type="text" class="form-control search-field datepicker" name="createTime" value="${(criteria.createTime)!""}">
		</td>
		<td>
			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
	    	<a href="${ctx.contextPath}/testRichtext/" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
		</td>
    </tr>
    
    <#if modelList??>
    <#list modelList as model>
    <tr>
    	<td>
			<#if (model.id)??>${model.id}</#if>
	    </td>
    	<td>
			<#if (model.title)??>${model.title}</#if>
	    </td>
    	<td>
			<#if (model.content)??>
				<@tooltip content="${(model.contentText)!''}"></@tooltip>
			</#if>
	    </td>
    	<td>
			<#if (model.content2)??>
				<@tooltip content="${(model.content2Text)!''}"></@tooltip>
			</#if>
	    </td>
    	<td>
			${model.active?string('是','否')}
	    </td>
    	<td>
			<#if (model.createTime)??>${model.createTime?datetime}</#if>
	    </td>
      	<td>
			<#if features?seq_contains("R")>
				<a href="${ctx.contextPath}/testRichtext/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
      		</#if>
      		<#if features?seq_contains("U")>
				<a href="${ctx.contextPath}/testRichtext/update/${model.id}" class="btn btn-default btn-xs" title="更新"><i class="fa fa-edit"></i></a>
      		</#if>
            <#if features?seq_contains("D")>
				<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/testRichtext/delete/${model.id}">
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