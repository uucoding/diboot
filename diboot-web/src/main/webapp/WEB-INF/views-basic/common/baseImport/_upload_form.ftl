<form class="form-horizontal" id="uploadForm" action="" method="post" enctype="multipart/form-data">
    <div class="form-group">
        <div class="col-md-4 col-md-offset-2">
            <input type="file" id="attachedFiles" name="attachedFiles" style="margin-top:5px" data-fv="NotNull">
        </div>
        <div class="col-md-4">
            <input type="text" name="comment" class="form-control" placeholder="备注信息" value="">
        </div>
    </div>
    <#if errors?? || (RequestParameters.errors)??>
    <div class="form-group">
        <div class="col-md-10 col-md-offset-1">
            <div class="alert alert-default alert-dismissable">
                <h4><i class="icon fa fa-warning"></i> 读取解析Excel出错!</h4>
                <#if errors??>${errors}<#elseif (RequestParameters.errors)??>${RequestParameters.errors}</#if>
            </div>
        </div>
    </div>
    </#if>
    <hr>
    <div class="form-group">
        <div class="col-md-2 col-md-offset-4">
            <button class="btn btn-primary btn-block btn-action" data-action="preview" type="button"><i class="fa fa-find"></i> 预览数据 </button>
        </div>
        <div class="col-md-2">
            <button class="btn btn-success btn-block btn-action" data-action="upload" type="button"><i class="fa fa-upload"></i> 上传数据</button>
        </div>
    </div>
</form>
