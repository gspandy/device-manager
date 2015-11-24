package com.yykj.base.exception;

import com.yykj.base.exception.BusinessException;

/**
 * 业务异常类
 * @author hankqin
 * 2012-12-28
 */
public class BusinessException extends RuntimeException implements
		IBusinessException {
	public static BusinessException nonLoginException() {
		return new BusinessException("尚未登录或长时间没有操作，请重新登录！", true);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		popup = false;
		relogin = false;
	}

	public BusinessException(String message, Throwable cause, boolean relogin) {
		super(message, cause);
		popup = false;
		this.relogin = false;
		this.relogin = relogin;
	}

	public BusinessException(String message) {
		super(message);
		popup = false;
		relogin = false;
	}

	public BusinessException(String message, boolean relogin) {
		super(message);
		popup = false;
		this.relogin = false;
		this.relogin = relogin;
	}

	public boolean isPopup() {
		return popup;
	}

	public boolean isRelogin() {
		return relogin;
	}

	private static final long serialVersionUID = -4077393888379762050L;
	private boolean popup;
	private boolean relogin;

}
