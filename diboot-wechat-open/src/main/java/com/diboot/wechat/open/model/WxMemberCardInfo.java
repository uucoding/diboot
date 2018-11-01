package com.diboot.wechat.open.model;

import java.io.Serializable;

public class WxMemberCardInfo implements Serializable {

    private static final long serialVersionUID = -5264484886462847016L;

    public enum DATE_TYPE{
        DATE_TYPE_PERMANENT, // 永久有效
        CUSTOM // 自定义
    }

    private String cardId;
    private String backgroundPicUrl = "";
    private String backgroundPicUrlLocal = "";
    private String logoUrl = "";
    private String logoUrlLocal = "";
    private String brandName = "";
    private String codeType = "CODE_TYPE_ONLY_QRCODE";
    private String title = "";
    private String color = "Color010";
    private String notice = "";
    private String servicePhone = "";
    private String description = "";
    private String prerogative = "";
    private String textImageUrl = "";
    private String textImageUrlLocal = "";
    private String textImageText = "";

    private String dateType = DATE_TYPE.DATE_TYPE_PERMANENT.name();
    private Long dateBeginTimestamp = 0L;
    private Long dateEndTimeStamp = 0L;

    private boolean sendCheck = true;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBackgroundPicUrl() {
        return backgroundPicUrl;
    }

    public void setBackgroundPicUrl(String backgroundPicUrl) {
        this.backgroundPicUrl = backgroundPicUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrerogative() {
        return prerogative;
    }

    public void setPrerogative(String prerogative) {
        this.prerogative = prerogative;
    }

    public String getTextImageUrl() {
        return textImageUrl;
    }

    public void setTextImageUrl(String textImageUrl) {
        this.textImageUrl = textImageUrl;
    }

    public String getTextImageText() {
        return textImageText;
    }

    public void setTextImageText(String textImageText) {
        this.textImageText = textImageText;
    }

    public String getBackgroundPicUrlLocal() {
        return backgroundPicUrlLocal;
    }

    public void setBackgroundPicUrlLocal(String backgroundPicUrlLocal) {
        this.backgroundPicUrlLocal = backgroundPicUrlLocal;
    }

    public String getLogoUrlLocal() {
        return logoUrlLocal;
    }

    public void setLogoUrlLocal(String logoUrlLocal) {
        this.logoUrlLocal = logoUrlLocal;
    }

    public String getTextImageUrlLocal() {
        return textImageUrlLocal;
    }

    public void setTextImageUrlLocal(String textImageUrlLocal) {
        this.textImageUrlLocal = textImageUrlLocal;
    }

    public boolean isSendCheck() {
        return sendCheck;
    }

    public void setSendCheck(boolean sendCheck) {
        this.sendCheck = sendCheck;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Long getDateBeginTimestamp() {
        return dateBeginTimestamp;
    }

    public void setDateBeginTimestamp(Long dateBeginTimestamp) {
        this.dateBeginTimestamp = dateBeginTimestamp;
    }

    public Long getDateEndTimeStamp() {
        return dateEndTimeStamp;
    }

    public void setDateEndTimeStamp(Long dateEndTimeStamp) {
        this.dateEndTimeStamp = dateEndTimeStamp;
    }
}
