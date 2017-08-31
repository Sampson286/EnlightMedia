package com.enlight.android.common.exception;

/**
 * 网络连接错误
 * @author zyc
 *
 */
public class NetworkConnectException extends ElightException {
	private static final long serialVersionUID = 1L;
	
	public NetworkConnectException() {
		super("");
	}
	
	public NetworkConnectException(String string) {
		super(string);
	}
	
	public NetworkConnectException(Throwable throwable) {
		super(throwable);
	}
	
	public NetworkConnectException(String string, Throwable throwable) {
		super(string, throwable);
	}

}
