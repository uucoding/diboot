package com.diboot.web.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseTreeModel;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.MetadataService;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.V;
import com.diboot.web.utils.AppHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/***
 * Copyright 2016 www.Dibo.ltd
 * 元数据相关操作Controller
 * @author Mazc@dibo.ltd
 * @version v1.0, 2016/08/23
 */
@Controller
@RequestMapping("/metadata")
public class MetadataController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(MetadataController.class);
	
	@Autowired
	private MetadataService metadataService;
	
	@Override
	protected String getViewPrefix() {
		return "metadata";
	}
	
	/***
	 * 根路径/
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/")
	public String root(HttpServletRequest request, ModelMap modelMap) throws Exception {		
		return list(request, modelMap);
	}
	
	/***
	 * 显示首页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/list")
	@Override
	public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 加载第一页
		return super.list(request, modelMap);
	}
	
	/**
	 * 显示首页-分页
	 * @param pageIndex 分页
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/list/{pageIndex}")
	@Override
	public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		// 附加条件
		Query query = new Query();
		query.add(Metadata.F.parentId, 0);
		query.add(Metadata.F.editable, true);
		modelMap.addAttribute("criteria", query.build());
		return super.listPaging(pageIndex, request, modelMap);
	}
	
	/***
	 * 显示查看详细页面
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
    public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
		return super.viewPage(id, request, modelMap);
    }

	/***
	 * 显示创建页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/create")
	@Override
    public String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {  
		return super.createPage(request, modelMap);
    }
	
	/***
	 * 创建的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid Metadata model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
		model.setSystem(false);
		return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @param request
	 * @param modelMap
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {  
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @param model
	 * @param result
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Long id, @Valid Metadata model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}
	
	/***
	 * 删除的后台处理
	 */
	@ResponseBody
	@PostMapping("/delete/{id}")
	public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
	protected BaseService getService() {
		return metadataService;
	}

	@Override
	protected String beforeDelete(BaseModel model) {
		Metadata metadataItem = (Metadata)model;
		// 管理员可删除非系统元数据
		boolean canDelete = AppHelper.getCurrentUser().isAdmin() && !metadataItem.isSystem();
		if(!canDelete){
			return Status.FAIL_NO_PERMISSION.label();
		}
		return null;
	}

	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap){
		if(model == null){
			model = metadataService.getModel(id);
		}
		if(model != null){
			// 获取所有子元素
			Metadata metadata = (Metadata)model;
			List<Metadata> children = metadataService.getModelList(super.newCriteria(BaseTreeModel.F.parentId, id));
			metadata.setChildren(children);
		}
		modelMap.put("model", model);
	}

	@Override
	protected String afterCreated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		String[] items = request.getParameterValues("items");
    	if(items != null && items.length > 0){
    		Metadata metadataItem = (Metadata)model;
    		List<Metadata> modelList = new ArrayList<Metadata>();
    		for(String item : items){
    			Map<String, Object> map = JSON.toMap(item);
				Metadata c = new Metadata();
				c.setType(metadataItem.getType());
				c.setParentId((Long) metadataItem.getId());
				c.setItemName(String.valueOf(map.get(Metadata.F.itemName)));
				c.setItemValue(String.valueOf(map.get(Metadata.F.itemValue)));
				c.setSystem(false);
				modelList.add(c);
    		}
    		if(!modelList.isEmpty()){
    			metadataService.batchCreateModels(modelList);
    		}
    	}
		// 创建完成
    	return super.afterCreated(request, model, modelMap);
	}

	@Override
	protected String afterUpdated(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		String[] items = request.getParameterValues("items");
		Metadata metadataItem = (Metadata) metadataService.getModel(model.getId());
		Set<Long> deleteIdsSet = new HashSet<Long>();
		if(metadataItem.getChildren() != null){
			for(BaseModel bm : metadataItem.getChildren()){
				deleteIdsSet.add((Long)bm.getId());
			}
		}
		if(items != null && items.length > 0){
			List<Metadata> modelList = new ArrayList<Metadata>();
			for(String item : items){
				Map<String, Object> map = JSON.toMap(item);
				String idStr = String.valueOf(map.get(Metadata.F.id));
				if (!V.equal("0", idStr)){
					deleteIdsSet.remove(Long.parseLong(idStr));
				} else {
					Metadata c = new Metadata();
					c.setType(metadataItem.getType());
					c.setParentId((Long) metadataItem.getId());
					c.setItemName(String.valueOf(map.get(Metadata.F.itemName)));
					c.setItemValue(String.valueOf(map.get(Metadata.F.itemValue)));
					c.setSystem(false);
					modelList.add(c);
				}
			}
			for(Long id : deleteIdsSet){
				metadataService.deleteModel(id);
			}
			if(!modelList.isEmpty()){
				metadataService.batchCreateModels(modelList);
			}
		}else{
			//子级数据全部清空
			for(Long id : deleteIdsSet){
				metadataService.deleteModel(id);
			}
		}
		return super.afterUpdated(request, model, modelMap);
	}

}