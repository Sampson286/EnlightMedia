package com.enlight.android.common.exception;

/**
 * 自定义无网络异常
 * @author zyc
 *
 */
public class NetworkNotException extends ElightException {

	private static final long serialVersionUID = 1L;

	public NetworkNotException() {
		super("NetworkNotAvailableException");
	}

}
