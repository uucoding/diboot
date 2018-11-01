package com.diboot.wechat.mp.service;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import org.springframework.stereotype.Component;

@Component
public interface WxMpImgService {

    /***
     * 微信公众号接口下载图片列表
     * @param images
     * @param model
     * @param user
     * @return
     */
    String downloadImgList(String[] images, BaseModel model, BaseUser user) throws Exception;

    /***
     * 微信公众号接口下载图片
     * @param mediaId
     * @param fileName
     * @param model
     * @return
     */
    String downloadImg(String mediaId, String fileName, BaseModel model) throws Exception;
}
