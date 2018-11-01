<#-- Page Header Breadcrumb-->
<#macro bodyHeader menu url title>
<section class="content-header">
    <h1>${title}</h1>
    <ol class="breadcrumb">
        <li><a href="${ctx.contextPath}/"><i class="fa fa-home"></i>工作台</a></li>
        <#if menu?? && menu!=''>
            <li class="active"><a href="${url}">${menu}</a></li>
        </#if>
    </ol>
</section>
</#macro>

<#-- Main section content -->
<#macro sectionBody>
<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <#nested>
        </div><#-- /.col-xs-12 -->
    </div><#-- /.row -->
</section><#-- /.content -->
</#macro>

<#macro box>
<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box box-primary">
                <#nested>
            </div><#-- /.box -->
        </div><#-- /.col-xs-12 -->
    </div><#-- /.row -->
</section><#-- /.content -->
</#macro>

<#macro boxHeader>
<div class="box-header">
    <div class="box-tools">
        <#nested>
    </div>
</div><#-- /.box-header -->
</#macro>

<#macro boxBody>
<div class="box-body">
    <#nested>
</div><#-- /.box-body -->
</#macro>

<#macro boxFooter>
<div class="box-footer clearfix">
    <#nested>
</div><#-- /.box-footer -->
</#macro>

<#macro collapseButton>
    <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
    </button>
</#macro>

<#macro collapseTools>
    <div class="box-tools pull-right">
        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
        </button>
    </div>
    <div class="box-tools pull-right">
        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
        </button>
    </div>
</#macro>

<#macro location loc1="" loc1url="" loc2="" back=false>
<section class="content-header hidden-sm hidden-xs" style="height:24px;">
    <ol class="breadcrumb" style="left: 10px; right: auto; top: 2px;">
        <#if loc1!=''>
            <li>
                <#if loc1url!=''>
                    <a href="${loc1url}">${loc1}</a>
                <#else>
                    <a href="#">${loc1}</a>
                </#if>
            </li>
        </#if>
        <li><a href="#">${loc2}</a></li>
    </ol>
    <#if back && loc1url!=''>
        <div class="page-toolbar" style="margin-top: -11px;">
            <div class="btn-group pull-right">
                <a class="btn btn-sm btn-outline btn-blue" href="${loc1url}"  style="padding: 4px 12px"> 返回 </a>
            </div>
        </div>
    </#if>
</section>
</#macro>

<#-- portlet -->
<#macro portlet>
<div class="box box-primary">
    <#nested>
</div>
</#macro>

<#-- portlet title -->
<#macro portletTitle title="记录列表" icon="fa-list">
<div class="box-header with-border">
    <i class="fa ${icon}"></i>
    <h3 class="box-title">${title!""}</h3>
    <#nested>
</div>
</#macro>

<#macro actions btnname="操作">
<div class="box-tools">
    <#nested>
</div>
</#macro>

<#macro portletBody>
<div class="box-body">
    <#nested>
</div>
</#macro>

<#macro portletFooter>
<div class="box-footer">
    <#nested>
</div>
</#macro>

<#macro selector optionList selectedValue="" class="" name="parentId">
<select class="form-control select2 ${class!''}" id="${name}" name="${name}">
    <#if optionList??>
        <#list optionList as opt>
            <option value="${opt["v"]}">${opt["k"]}</option>
        </#list>
    </#if>
</select>
</#macro>

<#macro dialog title="" id="" action="">
<#-- Modal -->
<div class="modal fade" id="${id!''}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${title!"标题"}</h4>
            </div>
            <form class="form-horizontal" role="form" action="${action!''}" method="post">
                <div class="modal-body">
                    <#nested />
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal"> 取 消 </button>
                    <button type="submit" class="btn btn-primary" style="padding: 6px 50px"> 提 交 </button>
                </div>
            </form>
        </div>
    </div>
</div>
</#macro>


<#macro modalNofoot title="" id="" height="">
<#-- Modal -->
<div class="modal fade" id="${id!''}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${title!"标题"}</h4>
            </div>
             <div class="modal-body" style="height:${height!'400px'};">
                    <#nested />
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro required>
<span class="font-red">*</span>
</#macro>

<#macro tooltip content>
    <#if content??>
    <a class="" href="#" data-toggle="tooltip" data-placement="top" title="" data-original-title="${content}">${content}</a>
    </#if>
</#macro>

<#-- 自动初始化的select -->
<#macro select initurl="" binddom="" binddomvalue="" triggerurl="" target="" prompt="">
<select class="form-control select2 select-init" <#if initurl!="">data-initurl="${initurl}"</#if>
        <#if binddom!="">data-binddom="${binddom}"</#if>
        <#if triggerurl!="">data-triggerurl="${triggerurl}"</#if> <#if prompt!="">data-prompt="${prompt}"</#if>
        <#if target!="">data-target="${target}"</#if>>
</select>
</#macro>

<#macro wrapper>
<div class="wrapper">
    <#include "../include/page-header.ftl">
    <#include "../include/sidebar.ftl">
    <div class="content-wrapper">
        <#nested>
    </div><#-- END CONTENT -->
    <#include "../include/footer.ftl">
</div><#-- ./page-wrapper -->
    <#include "../include/scripts.ftl">
</#macro>

<#macro button id="" type="" url="" title="" icon="" text="">
    <#if type?? && type="modal">
    <a <#if id?? && id!="">id="${id}"</#if> class="btn-modal btn btn-default btn-xs" data-type="${type!''}" data-url="${url!''}" data-title="${title!''}">
        <i class="fa ${icon!''}"></i> ${text!''}
    </a>
    <#else>
    <a <#if id?? && id!="">id="${id}"</#if> class="btn btn-default btn-xs" href="${url}">
        <i class="fa ${icon!''}"></i> ${text!''}
    </a>
    </#if>
</#macro>

<#macro modal width="" height="" >
<div class="modal fade" id="modal">
    <div class="modal-dialog" style="width:${width!''};margin:30px auto 0">
        <div class="modal-content">
            <div class="modal-header" style="padding: 10px 20px">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="width:100%;height:${height!'500px'};padding:8px 10px">

            </div>
        </div>
    </div>
</div>
</#macro>