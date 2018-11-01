package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户的组织单位基础信息
 * @author Mazc@dibo.ltd
 * @version 2017/4/18
 * Copyright @ www.dibo.ltd
 */
public class BaseOrg extends BaseTreeModel<BaseOrg> {
    private static final long serialVersionUID = 204L;

    @NotNull
    @Length(max = 255, message = "电话长度超出了最大限制！")
    private String name; // 名称

    @NotNull
    @Length(max = 50, message = "电话长度超出了最大限制！")
    private String shortName; // 短名称

    private String logo; // logo 图片路径
    private String address; // 地址

    @Length(max = 20, message = "电话长度超出了最大限制！")
    private String telephone; //电话

    @Length(max = 50, message = "Email长度超出了最大限制！")
    private String email; // email

    private String fax; // 传真
    private String website; // 网址
    private String comment; //备注

    /**
     * 构建查询条件所需参数定义
     */
    public static class F extends BaseTreeModel.F{ public static final String
        name = "name",
        shortName = "shortName",
        telephone = "telephone",
        logo = "logo",
        address = "address",
        email = "email",
        fax = "fax",
        website = "website",
        comment = "comment"
    ;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getModelName(){
        return "单位";
    }
}
