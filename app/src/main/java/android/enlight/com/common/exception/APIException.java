package android.enlight.com.common.exception;


import android.enlight.com.common.network.model.ResponseInfo;

/**
 * 服务器返回的状态码异常类
 */
public class APIException extends TDException {

	private static final long serialVersionUID = 1L;
	/** 返回的状态码 */
	private ResponseInfo responseInfo;
	/** 返回的对象 */
	private Object object;

	public APIException(ResponseInfo responseInfo) {
		this.responseInfo = responseInfo;
	}

	public APIException(ResponseInfo responseInfo, Object object) {
		this.responseInfo = responseInfo;
		this.object = object;
	}

	/**
	 * 获取服务端返回的信息
	 * 
	 * @return 联网状态信息
	 */
	public ResponseInfo getresponseInfo() {
		return responseInfo;
	}

	/**
	 * 获得对象
	 * 
	 * @return 返回对象
	 */
	public Object getObject() {
		return object;
	}

}
