package com.diboot.web.controller;

import com.diboot.framework.model.Metadata;
import com.diboot.framework.service.BaseService;
import com.diboot.framework.service.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 操作日志管理
 * @author Mazc@dibo.ltd
 * @version 2017/4/27
 *  Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/operationLog")
public class OperationLogController extends BaseCrudController {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogController.class);

    @Autowired
    private OperationLogService operationLogServiceImpl;

    @Override
    protected String getViewPrefix() {
        return "operationLog";
    }

    /***
     * 默认路径/
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
        return listPaging(1, request, modelMap);
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
        attachMore(request, modelMap);
        return super.viewPage(id, request, modelMap);
    }

    /***
     * 附加更多信息
     * @param request
     * @param modelMap
     * @throws Exception
     */
    @Override
    protected void attachMore(HttpServletRequest request, ModelMap modelMap) throws Exception {
        // TODO: com.diboot.framework.jar更新后，Type改为使用枚举
        // 操作日志--来源
        List<Metadata> sourceList = metadataService.getChildrenByType("LOG_SOURCE");
        modelMap.addAttribute("sources", sourceList);

        // 操作日志--操作类型
        List<Metadata> operationList = metadataService.getChildrenByType("LOG_OPERATION");
        modelMap.addAttribute("operations", operationList);
    }

    @Override
    protected BaseService getService() {
        return operationLogServiceImpl;
    }

}