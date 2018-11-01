package com.diboot.web.controller;

import com.diboot.common.service.UserService;
import com.diboot.framework.config.Status;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.JsonResult;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.ContextHelper;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.V;
import com.diboot.web.utils.AppHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 * 基础功能Controller
 * @author Mazc@dibo.ltd
 */
@Controller("commonController")
public class CommonController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private UserService userService;

	@Override
	protected String getViewPrefix(){
		return "common";
	}

	/***
	 * 欢迎页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/welcome")
	public String welcome(HttpServletRequest request, ModelMap model) throws Exception{
		SecurityManager securityManager = (SecurityManager) ContextHelper.getBean("securityManager");
		SecurityUtils.setSecurityManager(securityManager);
		BaseUser user = AppHelper.getCurrentUser();
		model.addAttribute("AP", D.getAmPm());
		return view(request, model, "welcome");
	}

	/***
	 * Keep Alive action
	 */
	@ResponseBody
	@GetMapping("/ping")
	public String ping(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return "";
	}

	@ResponseBody
	@PostMapping("/common/upload")
	public JsonResult uploadImg(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String url = super.saveFiles(request, null);
		if (V.notEmpty(url)){
			Map<String, String> data = new HashMap<>();
			data.put("url", url);
			return new JsonResult(data);
		}
		// 失败
		return new JsonResult(Status.FAIL_OPERATION, "上传图片/文件失败");
	}

	/***
	 * 显示修改密码页面
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/user/changepwd")
    public String changepwdPage(HttpServletRequest request, ModelMap modelMap) throws Exception {
		BaseUser user = AppHelper.getCurrentUser();
		modelMap.put("model", user);
		return view(request, modelMap, "changepwd");
    }

	/**
	 * 修改密码的后台处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/user/changepwd")
	public String changepwdAction(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String newPassword2 = request.getParameter("newPassword2");

		if(!isValidPwd(oldPassword) || !isValidPwd(newPassword) || !isValidPwd(newPassword2)){
			super.bindError(modelMap, "密码长度不符合要求，请设置6-16位的字母数字组合作为密码！");
            return view(request, modelMap, "changePwd");
		}
		if(!newPassword.equals(newPassword2)){
			super.bindError(modelMap, "两次输入的新密码不一样，请重新设置！");
            return view(request, modelMap, "changePwd");
		}

		// 更新密码
		Long userId = AppHelper.getCurrentUserId();
		boolean success = userService.updateUserPwd(userId, oldPassword, newPassword);
		if(!success){
			super.bindError(modelMap, "更新密码失败，旧密码输入错误！");
            return view(request, modelMap, "changePwd");
		}
		else{
			logger.info("用户:"+userId+" 更新了密码！");
		}
		String msg = "密码修改成功，请牢记新密码！";
		addResultMsg(request, success, msg);

		return redirectTo("/user/changepwd");
	}

	/***
	 * 密码是否符合要求
	 * @param password
	 * @return
	 */
	private boolean isValidPwd(String password){
		return password != null && password.length() >= 6 && password.length() <= 16;
	}

	/**
	 * 错误信息页面
	 * @return
	 */
	@RequestMapping("/error")
	public String error(HttpServletRequest request, ModelMap model) throws Exception{
		String errorInfo = BaseHelper.buildExceptionInfo(request);
		if (this.asyncLogger != null) {
			this.asyncLogger.saveErrorLog(null, errorInfo);
		}
		return view(request, model, "error");
	}

}