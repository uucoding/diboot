<form class="form-horizontal" method="post" id="form">
<@portletBody>
	<div class="form-group">
		<label class="col-md-2 control-label">关联对象 <@required /></label>
		<div class="col-md-4">
            <input type="text" v-model="config.relObjType" class="form-control" placeholder="关联对象(Model类名)"
				  required="true">
		</div>
        <label class="col-md-2 control-label">关联对象ID <@required /></label>
        <div class="col-md-4">
            <input type="text" v-model="config.relObjId" class="form-control" placeholder="关联对象ID(0为通用)"
                  required="true">
		</div>
	</div>
	<div class="form-group">
		<label class="col-md-2 control-label">类别 <@required /></label>
		<div class="col-md-4">
			<input type="text" v-model="config.category"  class="form-control" placeholder="类别(大写英文字母)"
				  required="true">
		</div>
		<label class="col-md-2 control-label">子类别 <@required /></label>
		<div class="col-md-4">
			<input type="text"  v-model="config.subcategory" class="form-control" placeholder="子类别(大写英文字母)"
				   required="true">
		</div>
	</div>
    <div style="width: 100%;height: 1px;background-color: #eef1f5;margin: 20px 0"></div>
	<div class="form-group" v-for="(item,index) in changeList" :key="index">
        <label class="col-md-2 control-label">
		  <span v-if="index == 0">配置参数</span>
		  <span v-else></span>
		</label>
		<div class="col-md-4">
          <input type="text" class="form-control"  placeholder="参数名" v-model="item.n">
		</div>
		<div class="col-md-4">
          <input type="text" class="form-control" placeholder="参数值" v-model="item.v">
		</div>
		<div class="col-md-2">
		  <button title="添加" @click="addNewParameter(index)" class="btn btn-info btn-sm" v-if="index == (changeList.length-1)" type="button">
			<i class="fa fa-plus"></i>
		  </button>
		  <button title="删除" @click="deleteOneParameter(index)" class="btn btn-default btn-sm" type="button">
            <i class="fa fa-trash"></i>
		  </button>
		</div>
	</div>
</@portletBody>
<@portletFooter>
    <div class="col-md-offset-2 col-md-2">
        <#if (model.id)??>
          <input type="hidden" id="modelId" value="${model.id}">
          <button class="btn btn-primary btn-block" @click="submitForm" type="button"> 提交更新 </button>
            <#else>
            <button class="btn btn-success btn-block" @click="submitForm" type="button"> 提交 </button>
        </#if>
    </div>
</@portletFooter>
</form>