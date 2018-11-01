<form method="get" action="${ctx.contextPath}/metadata/list">
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
            	<th>类型名称</th>
            	<th>类型编码</th>
            	<th>备注</th>
                <th>创建时间</th>
                <th class="col-operation">操作</th>
			</tr>
            </thead>
            <tbody>
            <tr>
            	<td>
        	    	<input class="form-control search-field" name="itemName" value="${(criteria.itemName)!""}">
            	</td>
            	<td>
        	    	<input class="form-control search-field" name="type" value="${(criteria.type)!""}">
            	</td>
            	<td>
        	    	<input class="form-control search-field" name="comment" value="${(criteria.comment)!""}">
            	</td>
				<td>
					-
				</td>
        		<td>
        			<button type="submit" class="btn btn-info btn-sm search-btn">查询</button>
        	    	<a href="${ctx.contextPath}/metadata/list" class="btn btn-default btn-sm loading-animation" title="重置"><i class="fa fa-refresh"></i></a>
        		</td>
            </tr>
            
            <#if modelList??>
            <#list modelList as model>
            <tr>
				<td>${(model.itemName)!""}</td>
				<td>${(model.type)!""}</td>
				<td>${(model.comment)!""}</td>
				<td><#if (model.createTime)??>${model.createTime?date}</#if></td>
              	<td>
              		<a href="${ctx.contextPath}/metadata/view/${model.id}" class="btn btn-default btn-xs" title="查看"><i class="fa fa-search-plus"></i></a>
					<#if features?seq_contains("U")>
					<a href="${ctx.contextPath}/metadata/update/${model.id}" class="btn btn-default btn-xs" title="修改"><i class="fa fa-edit"></i></a>
              		</#if>
					<#if features?seq_contains("D") && !model.system>
              		<a href="#" class="action-confirm btn btn-default btn-xs" title="删除" data-confirm="您确认要删除该项吗？" data-url="${ctx.contextPath}/metadata/delete/${model.id}">
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