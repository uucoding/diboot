package com.diboot.framework.exception;

/***
 * 访问权限异常
 * @author Mazc@dibo.ltd
 */
public class PermissionException extends Exception {

	private static final long serialVersionUID = -99L;

	public PermissionException(String msg) {
		super(msg);
	}
}