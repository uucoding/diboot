package com.diboot.wechat.open.service.impl;

import com.diboot.components.file.FileHelper;
import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.utils.JSON;
import com.diboot.framework.utils.V;
import com.diboot.wechat.open.model.WxMemberCardInfo;
import com.diboot.wechat.open.service.WxMpMemberCardService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpCardServiceImpl;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;

import java.io.File;
import java.util.Map;
import java.util.UUID;

public class WxMpMemberCardServiceImpl extends WxMpCardServiceImpl implements WxMpMemberCardService {

    private WxMpService thisWxMpService = null;

    private static String HOST_NAME = BaseConfig.getProperty("wechat.host.name");

    public WxMpMemberCardServiceImpl(WxMpService wxMpService) {
        super(wxMpService);
        this.thisWxMpService = wxMpService;
    }

    @Override
    public boolean createCard(WxMemberCardInfo model) throws WxErrorException {
        downloadImgToLocal(model);
        String cardInfoStr = buildCreateCardInfoStr(model);
        String responseContent = thisWxMpService.post(CARD_CREATE, cardInfoStr);
        Map<String, Object> responseMap = JSON.toMap(responseContent);
        if (responseMap == null || !V.equal(responseMap.get("errcode"), 0) || V.isEmpty(responseMap.get("card_id"))){
            return false;
        }
        model.setCardId(String.valueOf(responseMap.get("card_id")));
        return true;
    }

    @Override
    public boolean updateCard(WxMemberCardInfo model) throws WxErrorException {
        downloadImgToLocal(model);
        String cardInfoStr = buildUpdateCardInfoStr(model);
        String responseContent = thisWxMpService.post(CARD_UPDATE, cardInfoStr);
        Map<String, Object> responseMap = JSON.toMap(responseContent);
        if (responseMap == null || !V.equal(responseMap.get("errcode"), 0)){
            return false;
        }
        if (V.notEmpty(responseMap.get("send_check")) && V.equal(String.valueOf(responseMap.get("send_check")), "true")){
            model.setSendCheck(true);
        } else {
            model.setSendCheck(false);
        }

        return true;
    }

    @Override
    public String getMessageContent(String cardId) throws WxErrorException {
        String postDataStr = "{\"card_id\":\""+ cardId + "\"}";
        String responseContent = thisWxMpService.post(CARD_MSG_CONTENT, postDataStr);
        Map<String, Object> responseMap = JSON.toMap(responseContent);
        if (responseMap == null || !V.equal(responseMap.get("errcode"), 0)){
            return null;
        }
        return String.valueOf(responseMap.get("content"));
    }

    /***
     * 因为上传的图片地址必须为安全域名下的域名地址，所以需要将其他域名下的图片下载到本地
     * @param model
     */
    private void downloadImgToLocal(WxMemberCardInfo model) throws WxErrorException {
        if (V.notEmpty(model.getBackgroundPicUrl()) && model.getBackgroundPicUrl().contains("http")){
            model.setBackgroundPicUrlLocal(httpUrl2wxCdnUrl(model.getBackgroundPicUrl()));
        }
        if (V.notEmpty(model.getLogoUrl()) && model.getLogoUrl().contains("http")){
            model.setLogoUrlLocal(httpUrl2wxCdnUrl(model.getLogoUrl()));
        }
        if (V.notEmpty(model.getTextImageUrl()) && model.getTextImageUrl().contains("http")){
            model.setTextImageUrlLocal(httpUrl2wxCdnUrl(model.getTextImageUrl()));
        }
    }

    /***
     * 创建出创建会员卡所需的json字符串
     * @param model
     * @return
     */
    private String buildCreateCardInfoStr(WxMemberCardInfo model){
        String textImageListStr = "";
        if (V.notEmpty(model.getTextImageUrlLocal()) && V.notEmpty(model.getTextImageUrlLocal())){
            textImageListStr = ",\"text_image_list\": [\n" +
//				"                   {\n" +
//				"                       \"image_url\": \"http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sj piby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0\",\n" +
//				"                       \"text\": \"此菜品迎合大众口味，老少皆宜，营养均衡\"\n" +
//				"                   },\n" +
                    "                   {\n" +
                    "                       \"image_url\": \""+ model.getTextImageUrlLocal()+"\",\n" +
                    "                       \"text\": \""+ model.getTextImageText() +"\"\n" +
                    "                   }\n" +
                    "               ]\n";
        }

        // 创建有效日期信息
        String dateInfoStr = "\"date_info\": {\n" +
                "                    \"type\": \"DATE_TYPE_PERMANENT\"\n" +
                "                },\n";
        if (WxMemberCardInfo.DATE_TYPE.CUSTOM.name().equalsIgnoreCase(model.getDateType()) && model.getDateBeginTimestamp() != null && model.getDateEndTimeStamp() != null){            dateInfoStr =
            dateInfoStr = "\"date_info\": {\n" +
            "                    \"type\": \"DATE_TYPE_FIX_TIME_RANGE\",\n" +
            "                    \"begin_timestamp\": " + model.getDateBeginTimestamp() + ",\n" +
            "                    \"end_timestamp\": " + model.getDateEndTimeStamp() + "\n" +
            "                },\n";

        }


        return "{\n" +
                "    \"card\": {\n" +
                "        \"card_type\": \"MEMBER_CARD\",\n" +
                "        \"member_card\": {\n" +
                "            \"background_pic_url\": \"" + model.getBackgroundPicUrlLocal() + "\",\n" +
                "            \"base_info\": {\n" +
                "                \"logo_url\": \"" + model.getLogoUrlLocal() + "\",\n" +
                "                \"brand_name\": \"" + model.getBrandName() + "\",\n" +
                "                \"code_type\": \"CODE_TYPE_ONLY_QRCODE\",\n" +
                "                \"title\": \""+ model.getTitle() +"\",\n" +
                "                \"color\": \""+ model.getColor() +"\",\n" +
                "                \"notice\": \""+ model.getNotice() +"\",\n" +
                "                \"service_phone\": \""+ model.getServicePhone() +"\",\n" +
                "                \"description\": \""+ model.getDescription()+"\",\n" +
                "                " + dateInfoStr +
                "                \"sku\": {\n" +
                "                    \"quantity\": 50000000\n" +
                "                },\n" +
                "                \"get_limit\": 1,\n" +
                "                \"use_custom_code\": false,\n" +
                "                \"can_give_friend\": true\n" +
//				"                \"location_id_list\": [\n" +
//				"                    123,\n" +
//				"                    12321\n" +
//				"                ],\n" +
//				"                \"custom_url_name\": \"立即使用\",\n" +
//				"                \"custom_url\": \"http://weixin.qq.com\",\n" +
//				"                \"custom_url_sub_title\": \"6个汉字tips\",\n" +
//				"                \"promotion_url_name\": \"营销入口1\",\n" +
//				"                \"promotion_url\": \"http://www.qq.com\",\n" +
//				"                \"need_push_on_view\": true\n" +
                "            },\n" +
                "             \"advanced_info\": {\n" +
                "             \"abstract\": {\n" +
//				"                   \"abstract\": \"微信餐厅推出多种新季菜品，期待您的光临\",\n" +
//				"                   \"icon_url_list\": [\n" +
//				"                       \"http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sj\n" +
//				"  piby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0\"\n" +
//				"                   ]\n" +
                "               }\n" +
				"               " + textImageListStr +
                "           },\n" +
                "            \"supply_bonus\": false,\n" +
                "            \"supply_balance\": false,\n" +
                "            \"prerogative\": \""+ model.getPrerogative() +"\",\n" +
                "            \"auto_activate\": true,\n" +
                "            \"discount\": 10\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }

    /***
     * 创建更新会员卡所需的json字符串
     * @param model
     * @return
     */
    private String buildUpdateCardInfoStr(WxMemberCardInfo model){
        return "{\n" +
                "    \"card_id\": \""+model.getCardId()+"\",\n" +
                "    \"member_card\": {\n" +
                "        \"background_pic_url\": \""+ model.getBackgroundPicUrlLocal() +"\",\n" +
                "        \"base_info\": {\n" +
                "            \"title\": \""+ model.getTitle() +"\",\n" +
                "            \"logo_url\": \""+ model.getLogoUrlLocal() +"\",\n" +
                "            \"color\": \""+ model.getColor() +"\",\n" +
                "            \"notice\": \""+ model.getNotice() +"\",\n" +
                "            \"service_phone\": \""+ model.getServicePhone() +"\",\n" +
                "            \"description\": \""+ model.getDescription() +"\"\n" +
//                "            \"location_id_list\": [\n" +
//                "                123,\n" +
//                "                12321,\n" +
//                "                345345\n" +
//                "            ]\n" +
                "        },\n" +
//                "        \"bonus_cleared\": \"aaaaaaaaaaaaaa\",\n" +
//                "        \"bonus_rules\": \"aaaaaaaaaaaaaa\",\n" +
                "        \"supply_bonus\": false,\n" +
                "        \"supply_balance\": false,\n" +
                "        \"prerogative\": \"" + model.getPrerogative() + "\",\n" +
                "        \"auto_activate\": true,\n" +
                "        \"discount\": 10\n" +
                "    }\n" +
                "}";
    }

    /***
     * http链接转换为微信cdn链接
     * @param url
     * @return
     * @throws WxErrorException
     */
    private String httpUrl2wxCdnUrl(String url) throws WxErrorException {
        String storagePath = FileHelper.getFileStorageDirectory();
        String filePath = FileHelper.getImageStoragePath(UUID.randomUUID().toString().replaceAll("-", "")) + ".jpg";
        filePath = filePath.replace("upload", "download");
        String fullPath = storagePath + filePath;
        boolean susccess = FileHelper.downloadRemoteFile(url, fullPath);
        if (susccess){
            File file = new File(fullPath);
            WxMediaImgUploadResult res = this.thisWxMpService.getMaterialService().mediaImgUpload(file);
            return res.getUrl();
        }
        return "";
    }

}
