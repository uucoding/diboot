package com.diboot.wechat.cp.service.impl;

import com.diboot.common.service.FileService;
import com.diboot.components.file.FileHelper;
import com.diboot.framework.model.BaseFile;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseOrg;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;
import com.diboot.wechat.cp.config.Cons;
import com.diboot.wechat.cp.factory.WxCpServiceFactory;
import com.diboot.wechat.cp.service.WxCpImgService;
import me.chanjar.weixin.cp.api.WxCpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WxCpImgServiceImpl implements WxCpImgService {

    private static final Logger logger = LoggerFactory.getLogger(WxCpImgServiceImpl.class);

    @Autowired
    private FileService fileService;

    @Autowired
    private WxCpServiceFactory wxCpServiceFactory;

    @Override
    public String downloadImgList(String[] images, BaseModel model, BaseUser user, String wxConfigCategory) throws Exception{
        if (images == null || images.length == 0){
            return null;
        }
        List<String> imageUrlList = new ArrayList<String>();
        List<BaseFile> fileObjs = new ArrayList<BaseFile>();
        for (int i=0; i<images.length; i++){
            String image = images[i];

            if (V.isEmpty(image)){
                continue;
            }

            if (image.contains(".")){
                imageUrlList.add(image);
                continue;
            }

            // 保存到file表中
            String fileName = image;
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String ext = "jpg";
            String newFileName = uuid + "." + ext;
            String link = "/file/download/" + D.getYearMonth() + "_" + newFileName;
            String path = downloadImg(image, newFileName, model, wxConfigCategory);
            if (V.isEmpty(path)){
                logger.warn("下载微信图片失败");
                imageUrlList.add(image);
                continue;
            }
            BaseFile fileObj = new BaseFile();
            fileObj.setUuid(uuid);
            fileObj.setName(fileName);
            fileObj.setRelObjType(model.getClass().getSimpleName());
            fileObj.setRelObjId(model.getId());
            fileObj.setFileType(ext);
            fileObj.setLink(link);
            fileObj.setPath(path);
            fileObj.setCreateBy(user.getId());
            fileObj.setCreatorName(user.getRealname());
            fileObjs.add(fileObj);

            imageUrlList.add(path);
        }
        if (V.notEmpty(fileObjs)){
            boolean success = fileService.batchCreateModels(fileObjs);
            if (!success){
                logger.error("添加文件记录失败");
            }
        }

        return S.join(imageUrlList, ",");
    }

    @Override
    public String downloadImg(String mediaId, String fileName, BaseModel model, String wxConfigCategory) throws Exception{
        if (V.isEmpty(wxConfigCategory)){
            wxConfigCategory = Cons.WX_CONFIG_CATEGORY.DEFAULT.name();
        }
        WxCpService wxCpService = wxCpServiceFactory.getCpService(BaseOrg.class.getSimpleName(), 0L, wxConfigCategory);
        File file = wxCpService.getMediaService().download(mediaId);
        return FileHelper.saveImage(file, fileName, false);
    }

}
