package android.enlight.com.view.customview;

/**
 * 下拉刷新监听
 * 
 * @author zyc
 * 
 */
public interface PullToRefreshListener {

	/**
	 * 开始刷新刷新
	 */
	public abstract void onRefresh();

	/**
	 * 下拉监听
	 */
	public abstract void onPull();
}
