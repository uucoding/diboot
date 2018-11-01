package com.diboot.web.model;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;

import org.hibernate.validator.constraints.Length;

/**
* 编辑器文本 Model对象定义
* @author Mazc@dibo.ltd
* @version 2018-07-02
* Copyright © www.dibo.ltd
*/
public class EditableText extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = -433448961259942982L;


	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		relObjType = "relObjType",
		relObjId = "relObjId",
		field = "field",
		srcContent = "srcContent"

	;}

	/** 关联类型 */
    @Length(max = 100, message = "关联类型长度超出了最大限制！")
    private String relObjType;

	/** 关联对象 */
    private Long relObjId;

	/** 字段 */
    @Length(max = 100, message = "字段长度超出了最大限制！")
    private String field;

	/** 源数据 */
    @Length(max = 5096, message = "源数据长度超出了最大限制！")
    private String srcContent;


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

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

	public String getSrcContent() {
		return srcContent;
	}
	public void setSrcContent(String srcContent) {
		this.srcContent = srcContent;
	}
	public String getShortSrcContent() {
    	return S.cut(srcContent);
    }
	@Override
	public String getModelName(){
		return "编辑器文本";
	}
}