package com.diboot.components.controller;

import com.diboot.framework.controller.BaseCrudController;
import com.diboot.framework.service.BaseService;
import com.diboot.components.config.MsgCons;
import com.diboot.components.model.Message;
import com.diboot.components.model.MessageTmpl;
import com.diboot.components.service.MessageService;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.Query;
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
import java.util.Map;

/***
 * 消息相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2017-09-14
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/msg/message")
public class MessageController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;

	@Override
	protected String getViewPrefix() {
		return "msg/message";
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
		Query query = new Query(MessageTmpl.F.system, false);
		modelMap.addAttribute("criteria", query.toMap());
		return super.listPaging(pageIndex, request, modelMap);
	}
	
	/***
	 * 显示查看详细页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
    @Override
	public String viewPage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.viewPage(id, request, modelMap);
    }
	
	/***
	 * 显示创建页面
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
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/create")
    public String create(@Valid Message model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
        return super.create(model, result, request, modelMap);
    }
	
	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
	@Override
    public String updatePage(@PathVariable("id")Object id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Object id, @Valid Message model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}
	
	/***
	 * 删除的后台处理
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete/{id}")
	@ResponseBody
	@Override
	public Map<String, Object> delete(@PathVariable("id")Object id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
    protected String beforeDelete(BaseModel model) {
		BaseUser currentUser = BaseHelper.getCurrentUser();
    	boolean success = (currentUser != null && (currentUser.isAdmin() || currentUser.getId().equals(model.getCreateBy())));
		return success? null : Status.FAIL_NO_PERMISSION.label();
	}

	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
		// 关联的系统设置字段
		Message message = (Message) model;
		String[] keyList = MsgCons.MP_MSG_KEYS.get(message.getMsgType());
		modelMap.addAttribute("keyList", keyList);
		modelMap.addAttribute("model", model);
		modelMap.addAttribute("keyValueMap", getKeyValueMap(message, keyList));
		modelMap.addAttribute("mpMsgTypeMap", MsgCons.MP_MSG_TYPE_LABELS);
		modelMap.addAttribute("mpMsgKeys", MsgCons.MP_MSG_KEYS);
		attachMore(null, modelMap);
	}

	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
	}

	/**
	 * 获取key与value相对应的map
	 * @param model
	 * @param keyList
	 * @return
	 */
	private Map<String, String> getKeyValueMap(Message model, String[] keyList) {
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

	@Override
	protected BaseService getService() {
		return messageService;
	}	
}