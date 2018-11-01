<#if pagination["totalCount"] gt 0>
<div class="box-footer clearfix">
    <nav class="paginationNav" data-params="${(pagination["params"])!''}">
        <#-- 当前显示记录数/总数 -->
        <#if !(showPageCount?? && !showPageCount)>
            <div class="pagination pull-left">
                <p style="font-size: 14px;height: 34px;line-height: 34px" >【当前显示 <span style="color: #337ab7;">${(pagination["current"]-1)*(pagination["pageSize"])+1}</span>
                    -
                    <#if pagination["current"] < pagination["totalPages"]>
                        <span style="color: #337ab7;">${(pagination["current"])*(pagination["pageSize"])}</span>
                    <#else >
                        <span style="color: #337ab7;">${pagination["totalCount"]}</span>
                    </#if>
                    条 / 共
                    <span style="color: #337ab7;">${pagination["totalCount"]}</span> 条记录，每页显示
                    <select id="pageSizeSelector" name="pageSize" data-url="${ctx.contextPath}/${pagination["baseUrl"]}/1">
                        <option value="10" <#if pagination["pageSize"]?? && pagination["pageSize"]==10>selected</#if>>10</option>
                        <option value="20" <#if pagination["pageSize"]?? && pagination["pageSize"]==20>selected</#if>>20</option>
                        <option value="30" <#if pagination["pageSize"]?? && pagination["pageSize"]==30>selected</#if>>30</option>
                        <option value="50" <#if pagination["pageSize"]?? && pagination["pageSize"]==50>selected</#if>>50</option>
                        <option value="100" <#if pagination["pageSize"]?? && pagination["pageSize"]==100>selected</#if>>100</option>
                    </select>】
                </p>
            </div>
        </#if>
        <ul class="pagination pull-right">
		<#-- 首页/上一页 -->
			<#if pagination["current"] gt 1>
                <li class="">
                    <a href="${ctx.contextPath}/${pagination["baseUrl"]}/1${(pagination["params"])!''}" title="首页">
                        <span aria-hidden="true"> << </span>
                    </a>
                </li>
                <li class="">
					<#assign previousPage = pagination["current"]-1 />
					<#assign previousPageTitle = "上一页" />
					<#if (previousPage-9) gt 1>
						<#assign previousPage = previousPage-9 />
						<#assign previousPageTitle = "前十页" />
					</#if>
                    <a href="${ctx.contextPath}/${pagination["baseUrl"]}/${previousPage}${(pagination["params"])!''}" title="${previousPageTitle}">
                        <span aria-hidden="true"> < </span>
                    </a>
                </li>
			</#if>

		<#-- 中间页码 -->
			<#list pagination["pages"] as index>
                <li class="<#if pagination["current"] == index>active</#if>">
                    <a href="${ctx.contextPath}/${pagination["baseUrl"]}/${index}${(pagination["params"])!''}" title="第${index}页">
                        <span aria-hidden="true"> ${index} </span>
                    </a>
                </li>
			</#list>


		<#-- 末页/下一页 -->
			<#if pagination["current"] < pagination["totalPages"]>
                <li class="">
					<#assign nextPage = pagination["current"]+1 />
					<#assign nextPageTitle = "下一页" />
					<#if (nextPage+9) lt pagination["totalPages"]>
						<#assign nextPage = nextPage+9 />
						<#assign nextPageTitle = "后十页" />
					</#if>
                    <a href="${ctx.contextPath}/${pagination["baseUrl"]}/${nextPage}${(pagination["params"])!''}" title="${nextPageTitle}">
                        <span aria-hidden="true"> > </span>
                    </a>
                </li>
                <li class="">
                    <a href="${ctx.contextPath}/${pagination["baseUrl"]}/${pagination["totalPages"]}${(pagination["params"])!''}" title="末页">
                        <span aria-hidden="true"> >> </span>
                    </a>
                </li>
			</#if>
        </ul>
    </nav>
</div>
<#else>
<div class="alert alert-default alert-dismissable">
    <i class="icon fa fa-info-circle"></i> 没有匹配的记录！
</div>
</#if>