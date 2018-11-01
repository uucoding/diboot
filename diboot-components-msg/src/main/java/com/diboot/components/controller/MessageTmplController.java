package com.diboot.components.controller;

import com.diboot.components.config.MsgCons;
import com.diboot.components.model.MessageTmpl;
import com.diboot.components.service.MessageTmplService;
import com.diboot.framework.config.Status;
import com.diboot.framework.controller.BaseCrudController;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.Query;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 消息模板相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2017-09-14
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/msg/messageTmpl")
public class MessageTmplController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(MessageTmplController.class);
	
	@Autowired
	private MessageTmplService messageTmplService;

	@Override
	protected String getViewPrefix() {
		return "msg/messageTmpl";
	}

	/***
	 * 根路径/
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/")
	public String home(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return list(request, modelMap);
	}
	
	/***
	 * 显示首页面
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/list")
	public String list(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 加载第一页
		return listPaging(1, request, modelMap);
	}
	
	/**
	 * 显示首页-分页
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/list/{pageIndex}")
	public String listPaging(@PathVariable("pageIndex")int pageIndex, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.listPaging(pageIndex, request, modelMap);
	}
	
	/***
	 * 显示查看详细页面
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/view/{id}")
    public String viewPage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.viewPage(id, request, modelMap);
    }
	
	/***
	 * 显示创建页面
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/create")
    public String createPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.createPage(request, modelMap);
    }
	
	/***
	 * 创建的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid MessageTmpl model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@Override
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		MessageTmpl model = messageTmplService.getModel(id);
		attachMore4View(id, model, modelMap);
		modelMap.addAttribute("model", model);
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Object id, @Valid MessageTmpl model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}

	/**
	 * 根据模板类型获取相应模板变量列表
	 * @param code
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping("/getTmplVaribles/{code}")
	public JsonResult getTmplVaribles(@PathVariable("code") String code, HttpServletRequest request) throws Exception{
		if (V.isEmpty(code)){
			return new JsonResult(Status.FAIL_OPERATION, new String[]{"消息模板类型不能为空"});
		}

		Query query = new Query();
		query.add(Metadata.F.type, MessageTmpl.METADATA_TYPE.MESSAGE_TMPL_VARIBLES.name());
		query.add(Metadata.F.itemName, code);
		List<Metadata> metadataList = metadataService.getModelList(query.build());
		if (V.isEmpty(metadataList)){
			return new JsonResult(Status.FAIL_OPERATION, new String[]{"该模板下还没有定义模板变量列表"});
		}

		Metadata opt = metadataList.get(0);
		String itemValue = opt.getItemValue();
		String[] varibles = S.split(itemValue, ",");

		if (varibles.length == 0){
			return new JsonResult(Status.FAIL_OPERATION, new String[]{"该模板下还没有定义模板变量列表"});
		}

		return new JsonResult(Status.OK, varibles, new String[]{"获取变量列表成功"});
	}
	
	/***
	 * 删除的后台处理
	 * @return
	 * @throws Exception
	 */
	@Override
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id")Object id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
	protected void beforeCreate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		MessageTmpl messageTmpl = (MessageTmpl) model;
		buildModel(messageTmpl, request);
		model.setCreatorName(BaseHelper.getCurrentUser().getRealname());
	}

	@Override
	protected void beforeUpdate(HttpServletRequest request, BaseModel model, ModelMap modelMap) throws Exception{
		MessageTmpl messageTmpl = (MessageTmpl) model;
		MessageTmpl oldModel = messageTmplService.getModel(model.getId());
		messageTmpl.setExtdata(oldModel.getExtdata());

		buildModel(messageTmpl, request);
	}

	@Override
    protected String beforeDelete(BaseModel model) {
		BaseUser currentUser = BaseHelper.getCurrentUser();
    	boolean hasPermission = (currentUser != null && (currentUser.isAdmin() || currentUser.getId().equals(model.getCreateBy())));
		// 有权限执行删除操作
    	if(hasPermission){
			// 不可删除系统模板
			MessageTmpl tmpl = (MessageTmpl)model;
			if(tmpl.isSystem()){
				return Status.FAIL_OPERATION.label() + ": 系统预置模板，不可删除！" ;
			}
		}
    	return hasPermission? null : Status.FAIL_NO_PERMISSION.label();
	}

	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
		// 关联的系统设置字段
		MessageTmpl messageTmpl = (MessageTmpl) model;
		String[] keyList = MsgCons.MP_MSG_KEYS.get(messageTmpl.getMsgType());
		modelMap.addAttribute("keyList", keyList);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("keyValueMap", getKeyValueMap(messageTmpl, keyList));
		attachMore(null, modelMap);
	}

	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
		List<Metadata> tmplCodeList = metadataService.getChildrenByType(MessageTmpl.METADATA_TYPE.MESSAGE_TMPL_CODE.name());
		modelMap.addAttribute("tmplCodeList", tmplCodeList);

		modelMap.addAttribute("mpMsgTypeMap", MsgCons.MP_MSG_TYPE_LABELS);
		modelMap.addAttribute("mpMsgKeys", MsgCons.MP_MSG_KEYS);
	}

	/**
	 * 获取key与value相对应的map
	 * @param model
	 * @param keyList
	 * @return
	 */
	private Map<String, String> getKeyValueMap(MessageTmpl model, String[] keyList) {
		if (V.isEmpty(keyList)){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		for (String key : keyList){
			String value = String.valueOf(model.getFromJson(key));
			if (V.isEmpty(value)){
				value = "";
			}
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 添加关联数据
	 * @param model
	 * @throws Exception
	 */
	private void buildModel(MessageTmpl model, HttpServletRequest request) throws Exception{
		// 添加模板数据
		if (V.notEmpty(model.getMsgType())){
			String[] keyList = MsgCons.MP_MSG_KEYS.get(model.getMsgType());
			if (V.notEmpty(keyList)){
				for (String key : keyList){
					String result = request.getParameter(key);
					if (V.isEmpty(result)){
						continue;
					}

					model.addToJson(key, result);
				}
			}
		}
	}

	@Override
	protected BaseService getService() {
		return messageTmplService;
	}	
}