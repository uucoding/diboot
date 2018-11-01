<#if model??>
    <div class="alert alert-info">
        <#if (model.updateTime)??>${model.updateTime?datetime} </#if>上传Excel文件 "${(model.name)!''}" (<strong>${(model.dataCount)!'0'}</strong> 条数据).
    </div>
</#if>

<#if errors??>
<div class="note note-warning">
    <b>读取Excel解析数据出错: </b><br>${errors}
</div>
<#elseif isPreview?? && isPreview>
    <#if dataList??>
    <div class="alert alert-info">
        Excel文件解析成功，共有 <strong>${dataList?size-1}</strong> 条数据可上传.
    </div>
    <#else>
    <div class="alert alert-warning">
        解析Excel文件异常，无任何可上传数据.
    </div>
    </#if>
</#if>

<#if dataList??>
<div class="table-responsive">
    <table class="table table-striped">
        <thead>
            <#list dataList as row>
                <#if row_index==0>
                <tr>
                    <th>行号</th>
                    <#list row as cell>
                        <th>${cell}</th>
                    </#list>
                </tr>
                    <#break>
                </#if>
            </#list>
        </thead>
        <tbody>
            <#list dataList as row>
                <#if row_index gt 0>
                <tr>
                    <td>${row_index+1}</td>
                    <#list row as cell>
                        <td>${cell}</td>
                    </#list>
                </tr>
                </#if>
            </#list>
        </tbody>
    </table>
</div>
</#if>