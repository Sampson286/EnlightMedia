package android.enlight.com.common.exception;

/**
 * 自定义异常基类
 * @author zyc
 *
 */
public class ElightException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public ElightException() {
		super();
	}
	public ElightException(String message) {
		super(message);
	}
	public ElightException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ElightException(Throwable throwable) {
		super(throwable);
	}
	
}
