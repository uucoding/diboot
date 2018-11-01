<table class="table table-striped table-hover">
    <thead>
    <tr>
    	<th width="10%">角色</th>
    	<th>可访问菜单</th>
    	<th class="col-operation">操作</th>
    </tr>
    </thead>
    <tbody>
    <#if roleMapList??>
    <#list roleMapList as roleMap>
    <tr>
    	<td>${roleMap["k"]}</td>
    	<td>
    		<#if roleMap["v"] == "ADMIN">
    			<b>所有菜单</b>
    		<#elseif roleMap["menus"]??>
    			<#list roleMap["menus"] as menu>
    				<b>${menu.name} </b>
    				<#if menu.children??>
    					: 
    					<#list menu.children as c>
    					${c.name}  <#if (c_index+1) lt menu.children?size>,</#if>
    					</#list>
    				</#if>
    				<br>
    			</#list>
    		<#else>
    			无
    		</#if>
    	</td>
      	<td>
      		<#if roleMap["v"] != "ADMIN">
      		<a href="${ctx.contextPath}/rolemenu/config/${roleMap["v"]}" class="btn btn-default btn-sm" title="配置操作权限"><i class="fa fa-cogs"></i> 配置权限</a>
      		<#else>
  			-
      		</#if>
      	</td>
    </tr>
    </#list>
    </#if>
	</tbody>
</table>