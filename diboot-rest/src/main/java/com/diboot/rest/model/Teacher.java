package com.diboot.rest.model;

import java.util.Date;
import java.util.List;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
* 教师 Model对象定义
* @author Mazc
* @version 2018-08-22
* Copyright © www.dibo.ltd
*/
public class Teacher extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = 4717259835802837014L;


	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		name = "name"

	;}

	/** 老师姓名 */
    @Length(max = 100, message = "老师姓名长度超出了最大限制！")
    private String name;


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getModelName(){
		return "教师";
	}
}