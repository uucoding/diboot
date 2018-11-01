package com.diboot.wechat.cp.service;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import org.springframework.stereotype.Component;

@Component
public interface WxCpImgService {

    /***
     * 企业微信接口下载图片列表
     * @param images
     * @param model
     * @param user
     * @return
     */
    String downloadImgList(String[] images, BaseModel model, BaseUser user, String wxConfigCategory) throws Exception;

    /***
     * 企业微信接口下载图片
     * @param mediaId
     * @param fileName
     * @param model
     * @return
     */
    String downloadImg(String mediaId, String fileName, BaseModel model, String wxConfigCategory) throws Exception;
}
