package com.diboot.framework.model;

import com.diboot.framework.config.BaseCons;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/***
 * Dibo File-文件
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class BaseFile extends BaseModel{
	private static final long serialVersionUID = 201L;

	@NotNull(message = "编号不能为空！")
	@Length(max = 32, message = "编号长度超出了最大限制！")
	private String uid = null; // uuid

    @NotNull(message = "关联对象类型不能为空！")
    @Length(max = 50, message = "关联对象类型长度超出了最大限制！")  
    private String relObjType = null; // 关联对象类型
	
    @NotNull(message = "关联对象ID不能为空！")
    private Long relObjId; // 关联对象ID
	
    @NotNull(message = "文件名不能为空！")
    @Length(max = 255, message = "文件名长度超出了最大限制！")  
    private String name; // 文件名
	
    @NotNull(message = "文件链接不能为空！")
    @Length(max = 255, message = "文件链接长度超出了最大限制！")  
    private String link; // 文件链接

    @NotNull(message = "文件路径不能为空！")
    @Length(max = 255, message = "文件路径长度超出了最大限制！")  
    private String path; // 文件链接
	
    @Length(max = 255, message = "文件类型长度超出了最大限制！")  
    private String fileType; // 文件类型

	private int dataCount = 0; //数据数量，用于excel等

    private Long size; // 大小

	@Length(max = 1, message = "状态长度超出了最大限制！")
	private String status = BaseCons.OPERATE_STATUS.S.name(); // 状态(S:成功，F：失败)

	@Length(max = 255, message = "备注长度超出了最大限制！")
    private String comment; // 备注

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		name = "name",
		link = "link",
		path = "path",
        relObjType = "relObjType",
        relObjId = "relObjId",
		dataCount = "dataCount",
		status = "status"
	;}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getRelObjType() {
		return relObjType;
	}
	public void setRelObjType(String relObjType) {
		this.relObjType = relObjType;
	}
	public Long getRelObjId() {
		return relObjId;
	}
	public void setRelObjId(Long relObjId) {
		this.relObjId = relObjId;
	}
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
    public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
    public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
    public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLabel() {
		return BaseCons.OPERATE_STATUS.getLabel(status);
	}

	/***
	 * ID生成类型，用于保存前生成所需id
	 * 默认数据库自增类型
	 * @return
	 */
	@Override
	public PK_TYPE getPkType(){
		return PK_TYPE.UUID;
	}

	@Override
	public String getModelName(){
		return "文件";
	}
}