package android.enlight.com.common.network.model;

import java.io.Serializable;

/**
 * 联网返回的状态信息
 * @author zyc
 *
 */
public class ResponseInfo implements Serializable {
	
	private static final long serialVersionUID = 2L;	
	/** 服务器返回的状态码 */
	private int status;
	/** 服务器返回的消息 */
	private String message;
	
	/**
	 * 获取服务器返回的状态码
	 * @return 服务器返回的状态码
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * 设置服务器返回的状态码
	 * @param status 服务器返回的状态码
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 获取服务器返回的信息
	 * @return 服务器返回的信息
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置服务器返回的信息
	 * @param message 服务器返回的信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
