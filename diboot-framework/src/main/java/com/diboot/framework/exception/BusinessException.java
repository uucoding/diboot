package com.diboot.framework.exception;

/***
 * 业务处理异常
 * @author Mazc@dibo.ltd
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = -97L;

	public BusinessException(String msg) {
		super(msg);
	}
}