package com.diboot.components.model;

import com.diboot.framework.model.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
* Excel列定义
* @author Mazc@dibo.ltd
* @version 2017-09-18
* Copyright @ www.dibo.ltd
*/
public class ExcelColumn extends BaseModel {
	private static final long serialVersionUID = -1539079350889067812L;

	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		modelClass = "modelClass",
		modelField = "modelField",
		colName = "colName",
		colIndex = "colIndex",
		dataType = "dataType",
		validation = "validation"
	;}
	
    @NotNull(message = "Java对象类不能为空！")
    @Length(max = 50, message = "Java对象类长度超出了最大限制！")  
    private String modelClass; // Java对象类
	
    @NotNull(message = "Java对象属性不能为空！")
    @Length(max = 50, message = "Java对象属性长度超出了最大限制！")  
    private String modelField; // Java对象属性
	
    @NotNull(message = "列标题不能为空！")
    @Length(max = 50, message = "列标题长度超出了最大限制！")  
    private String colName; // 列标题
	
    @NotNull(message = "列索引不能为空！")
    private Integer colIndex; // 列索引
	
    @Length(max = 20, message = "数据类型长度超出了最大限制！")  
    private String dataType; // 数据类型
	
    @Length(max = 50, message = "校验长度超出了最大限制！")  
    private String validation; // 校验
	
	public String getModelClass() {
		return modelClass;
	}
	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}

	public String getModelField() {
		return modelField;
	}
	public void setModelField(String modelField) {
		this.modelField = modelField;
	}

    
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}

    
	public Integer getColIndex() {
		return colIndex;
	}
	public void setColIndex(Integer colIndex) {
		this.colIndex = colIndex;
	}

    
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

    
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}

}