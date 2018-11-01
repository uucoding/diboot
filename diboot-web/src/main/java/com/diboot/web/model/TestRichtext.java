package com.diboot.web.model;

import com.diboot.framework.model.BaseModel;
import com.diboot.framework.utils.S;
import com.diboot.framework.utils.V;

import org.hibernate.validator.constraints.Length;

/**
* 测试富文本 Model对象定义
* @author Mazc
* @version 2018-08-21
* Copyright © www.dibo.ltd
*/
public class TestRichtext extends com.diboot.framework.model.BaseModel{
	private static final long serialVersionUID = -2189284599952580911L;


	/**
	 * 构建查询条件所需参数定义
	 */
	public static class F extends BaseModel.F{ public static final String
		title = "title",
		content = "content",
		content2 = "content2"

	;}

	/** 标题 */
    @Length(max = 100, message = "标题长度超出了最大限制！")
    private String title;

	/** 内容 */
    @Length(max = 500, message = "内容长度超出了最大限制！")
    private String content;

	/** 内容2 */
    @Length(max = 500, message = "内容2长度超出了最大限制！")
    private String content2;


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/** 富文本html对应的文字内容 **/
	public String getContentText(){
		if (V.notEmpty(content)){
			return S.cut(S.html2text(content));
		}
		return content;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	/** 富文本html对应的文字内容 **/
	public String getContent2Text(){
		if (V.notEmpty(content2)){
			return S.cut(S.html2text(content2));
		}
		return content2;
	}
	@Override
	public String getModelName(){
		return "测试富文本";
	}
}