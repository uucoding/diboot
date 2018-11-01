package com.diboot.web.controller;

import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.TimerTask;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.TimerTaskService;
import com.diboot.framework.timer.TaskExecutor;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.ContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/***
 * 定时任务相关操作Controller
 * @author Mazc@dibo.ltd
 * @version 2018-01-20
 * © 2017 www.dibo.ltd
 */
@Controller
@RequestMapping("/timerTask")
public class TimerTaskController extends BaseCrudController {
	private static final Logger logger = LoggerFactory.getLogger(TimerTaskController.class);
	
	@Autowired
	private TimerTaskService timerTaskService;


	@Override
	protected String getViewPrefix() {
		return "timerTask";
	}

	/***
	 * 根路径/
	 * @return view
	 * @throws Exception
	 */
    @Override
	@GetMapping("/")
	public String index(HttpServletRequest request, ModelMap modelMap) throws Exception {
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
		// 封装查询条件
		/* 列表页面不建议关联子对象，如需要请打开注释
		Map<String, Object> criteria = super.buildQueryCriteria(request, pageIndex);
		List<TimerTask> modelList = timerTaskService.getModelList(criteria, pageIndex);
		// 绑定关联对象
		modelMap.addAttribute("modelList", modelList);
		*/
		return super.listPaging(pageIndex, request, modelMap);
	}

	/***
	 * 显示查看详细页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/view/{id}")
    public String viewPage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		TimerTask model = (TimerTask)timerTaskService.getModel(id);
		// 设置关联对象
		modelMap.put("model", model);
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
    public String create(@Valid TimerTask model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception {
		// 设置任务类型
		if(model.getType() == null){
			List<TaskExecutor> taskExecutorList = ContextHelper.getBeans(TaskExecutor.class);
			for(TaskExecutor executor : taskExecutorList){
				if(executor.getExecutor().equals(model.getExecutor())){
					model.setType(executor.getType());
					break;
				}
			}
		}
		// 保存
        super.create(model, result, request, modelMap);
		// 返回列表页
		return redirectTo("/timerTask/list/1");
    }
	
	/***
	 * 显示更新页面
	 * @return view
	 * @throws Exception
	 */
	@GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id")Long id, HttpServletRequest request, ModelMap modelMap) throws Exception {
		return super.updatePage(id, request, modelMap);
    }
	
	/***
	 * 更新的后台处理
	 * @return view
	 * @throws Exception
	 */
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id")Long id, @Valid TimerTask model, BindingResult result, HttpServletRequest request, ModelMap modelMap) throws Exception{
		return super.update(id, model, result, request, modelMap);
	}

	/***
	 * 删除的后台处理
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete/{id}")
	@ResponseBody
	public Map<String, Object> delete(@PathVariable("id")Long id, HttpServletRequest request) throws Exception{
		return super.delete(id, request);
	}

	@Override
    protected String beforeDelete(BaseModel model) {
		TimerTask task = (TimerTask) model;
		BaseUser currentUser = BaseHelper.getCurrentUser();
    	boolean hasPermission = (currentUser != null && (currentUser.isAdmin() || currentUser.getId().equals(model.getCreateBy())));
		boolean canDelete = (hasPermission && TimerTask.STATUS.NEW.name().equals(task.getStatus()));
    	return canDelete? null : Status.FAIL_OPERATION.label();
	}

	@Override
	protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception{
		// 获取所有的定时任务实现类
		List<TaskExecutor> taskExecutorList = ContextHelper.getBeans(TaskExecutor.class);
		modelMap.put("taskExecutorList", taskExecutorList);
	}

	@Override
	protected void attachMore4View(Object id, BaseModel model, ModelMap modelMap) throws Exception {
	}

	@Override
	protected BaseService getService() {
		return timerTaskService;
	}	
}