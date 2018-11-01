package com.diboot.components.controller;

import com.diboot.components.file.FileHelper;
import com.diboot.framework.config.BaseCons;
import com.diboot.framework.config.Status;
import com.diboot.framework.controller.BaseController;
import com.diboot.framework.exception.BusinessException;
import com.diboot.framework.exception.PermissionException;
import com.diboot.framework.model.BaseFile;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.service.BaseFileService;
import com.diboot.framework.utils.BaseHelper;
import com.diboot.framework.utils.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Mazc@dibo.ltd
 * @version 2017/9/22
 * Copyright @ www.dibo.ltd
 */
@Controller
@RequestMapping("/file")
public class BaseFileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseFileController.class);

    @Autowired
    protected BaseFileService baseFileService;

    /***
     * 下载文件
     * @param request
     * @return view
     * @throws Exception
     */
    @GetMapping("/download/{uuid}")
    public void download(@PathVariable("uuid")String uuid, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding(BaseCons.CHARSET_UTF8);
        if(uuid.contains(".")){
            uuid = S.substringBeforeLast(uuid, ".");
        }
        if (uuid.contains("_")){
            uuid = S.substringAfterLast(uuid, "_");
        }
        // 获取文件上传记录
        BaseFile fileModel = baseFileService.getModel(uuid);
        if(fileModel != null){
            // 校验权限
            BaseUser user = BaseHelper.getCurrentUser();
            if(hasDownloadPermission(user, fileModel) == false){
                // 无权访问
                throw new PermissionException(Status.FAIL_NO_PERMISSION.label());
            }
            String downloadPath = FileHelper.getFullFilePath(fileModel.getPath());
            try{
                // 设置下载下载头
                String fileName = new String(fileModel.getName().getBytes(BaseCons.CHARSET_UTF8), BaseCons.CHARSET_ISO8859_1);
                File sourceFile = new File(downloadPath);
                if(!sourceFile.exists()){
                    logger.warn("文件不存在: uuid="+uuid+", path="+downloadPath);
                    throw new BusinessException("下载文件不存在: " + fileModel.getName());
                }
                // 下载文件
                response.setHeader("Content-Disposition", "attachment; filename="+ fileName);
                response.setContentType(FileHelper.getContextType(fileName));
                FileCopyUtils.copy(new FileInputStream(sourceFile), response.getOutputStream());
            }
            catch (Exception e) {
                logger.error("下载文件失败:"+uuid, e);
            }
        }
    }

    /***
     * 用户有管理权限 或者 自己上传的文件
     * @param user
     * @param fileModel
     * @return
     */
    protected boolean hasDownloadPermission(BaseUser user, BaseFile fileModel){
        return user.isAdmin()
                || user.getId().equals(fileModel.getCreateBy());
    }

    @Override
    protected String getViewPrefix() {
        return null;
    }
}
