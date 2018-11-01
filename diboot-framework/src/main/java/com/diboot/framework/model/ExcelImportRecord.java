package com.diboot.framework.model;

import org.hibernate.validator.constraints.Length;

/**
* 导入记录 Model对象定义
* @author Mazc@dibo.ltd
* @version 2018-06-11
* Copyright © www.dibo.ltd
*/
public class ExcelImportRecord extends BaseModel{
	private static final long serialVersionUID = -1207584493211061321L;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		fileUuid = "fileUuid",
		relObjType = "relObjType",
		relObjId = "relObjId",
		relObjUid = "relObjUid"
	;}

	/** 文件ID */
    @Length(max = 32, message = "文件ID长度超出了最大限制！")
    private String fileUuid;

	/** 关联类型 */
    @Length(max = 100, message = "关联类型长度超出了最大限制！")
    private String relObjType;

	/** 关联ID */
    private Long relObjId;

    /** 关联UUID */
    private String relObjUid;

	public String getFileUuid() {
		return fileUuid;
	}
	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
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

	public String getRelObjUid() {
		return relObjUid;
	}

	public void setRelObjUid(String relObjUid) {
		this.relObjUid = relObjUid;
	}

	@Override
	public String getModelName(){
		return "导入记录";
	}
}