package android.enlight.com.common.exception;

/**
 * 联网取消异常类
 * @author zyc
 *
 */
public class NetworkForceCloseException extends ElightException {

	private static final long serialVersionUID = 1L;

	public NetworkForceCloseException() {
		super();
	}
	
	public NetworkForceCloseException(String message) {
		super(message);
	}
	
}
