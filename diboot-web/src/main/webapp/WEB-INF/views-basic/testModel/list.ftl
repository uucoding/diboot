<#assign plugins = ['editormd', 'lightbox', 'datetimepicker']/>
<#assign features=["C","R","U","D","L","Batch","Export"] />
<#include "../include/head.ftl">
<body class="${bodyClass}">
<@wrapper>
	<@location loc1="测试模型管理" loc1url="${ctx.contextPath}/testModel/" loc2="测试模型记录" />
<div class="content">
	<@portlet>
		<@portletTitle title="测试模型记录">
			<#if features?seq_contains("C")>
				<@actions>
					<a class="btn btn-sm blue btn-modal-box" href="javascript:;" data-url="${ctx.contextPath}/testModel/create" data-title="新建测试模型">
                        <i class="fa fa-plus"></i> 新建测试模型
                    </a>
					<div class="btn-group dropdown">
						<button type="button" class="btn btn-sm blue dropdown-toggle" data-toggle="dropdown"> 操作
							<i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right" role="menu">
							<li>
								<a class="font-blue btn-batch" href="#" data-action="${ctx.contextPath}/testModel/batch">
									<i class="fa fa-edit font-blue"></i> 勾选操作
								</a>
							</li>
                            <li>
                                <a class="font-blue" href="${ctx.contextPath}/testModel/export${(pagination["params"])!''}">
                                    <i class="fa fa-download font-blue"></i> 导出Excel
                                </a>
                            </li>
							<li class="divider"> </li>
						</ul>
					</div>
				</@actions>
			</#if>
		</@portletTitle>
		<@portletBody>
			<#include "list_page.ftl">
			<#include "../include/pagination.ftl">
		</@portletBody>
	</@portlet><#-- END PAGE BODY -->
</div><#-- END CONTENT BODY -->
</@wrapper>
<@modal width="720px"></@modal>
</body>
</html>