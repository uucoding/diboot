<div class="table-responsive">
    <table class="table table-striped">
	<tbody>
		<tr>
	  <td class="td-label">ID</td>
	  <td>
            <#if (model.id)??>
				${model.id}
            </#if>
	  </td>
	</tr>
	<tr>
	  <td class="td-label">消息类型</td>
	  <td>
			${(model.type)!""}
	  </td>
	</tr>
	<#if (model.businessId)??>
	<tr>
	  <td class="td-label">关联对象</td>
	  <td>
		<a href="${ctx.contextPath}/member/view/${model.businessId}">查看学员信息</a>
	  </td>
	</tr>
	</#if>
	<#if model.sender?? && model.sender!='0'>
	<tr>
		<td class="td-label">发送人</td>
		<td>
		${(model.sender)!""}
		</td>
	</tr>
	</#if>
	<tr>
	  <td class="td-label">接收号码</td>
	  <td>
			${(model.receiver)!""}
	  </td>
	</tr>
	<#if model.title??>
	<tr>
		<td class="td-label">短信类别</td>
		<td>
		${(model.title)!""}
		</td>
	</tr>
	</#if>
	<tr>
	  <td class="td-label">内容</td>
	  <td>
			${(model.contentHtml)!""}
	  </td>
	</tr>
	<#if model.url??>
	<tr>
		<td class="td-label">链接</td>
		<td>
		${(model.url)!""}
		</td>
	</tr>
	</#if>
	<tr>
	  <td class="td-label">发送状态</td>
	  <td>
			${(model.statusLabel)!""}
	  </td>
	</tr>
	<tr>
	  <td class="td-label">发送结果</td>
	  <td>
			${(model.response)!"-"}
	  </td>
	</tr>
	<#if (model.creatorName)??>
	<tr>
	  <td class="td-label">创建人</td>
	  <td>
	  	${model.creatorName}
	  </td>
	</tr>
	</#if>
	<tr>
	  <td class="td-label">创建时间</td>
	  <td>
            <#if (model.createTime)??>
				${model.createTime?datetime}
            </#if>
	  </td>
	</tr>
	</tbody>
</table>
</div>