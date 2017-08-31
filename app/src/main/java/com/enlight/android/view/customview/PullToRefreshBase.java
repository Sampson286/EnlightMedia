package com.enlight.android.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.enlight.com.R;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

/**
 * 下拉刷新的实现类
 * 
 * @author zyc
 */
public abstract class PullToRefreshBase<T extends View> extends LinearLayout {
	/** 摩擦系数 */
	private static final float FRICTION = 2.0f;

	/** 下拉刷新的状态 */
	private static final int PULL_TO_REFRESH = 0x0;
	private static final int RELEASE_TO_REFRESH = 0x1;
	private static final int REFRESHING = 0x2;
	private static final int MANUAL_REFRESHING = 0x3;

	/** 下拉刷新的模式 */
	public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
	public static final int MODE_BOTH = 0x3;
	/** 手指移动最小距离 */
	private int mTouchSlop;
	/** 手指按下的位置 */
	private float mInitialMotionY;
	private float mLastMotionX;
	private float mLastMotionY;
	/** 是否被拖拽 */
	private boolean mIsBeingDragged = false;

	private int mState = PULL_TO_REFRESH;
	private int mMode = MODE_PULL_DOWN_TO_REFRESH;
	private int mCurrentMode;

	/** 刷新中是否可以滚动 */
	private boolean mDisableScrollingWhileRefreshing = true;

	/** 当前的view对象 */
	protected T mRefreshableView;
	/** 下拉刷新是否启动 */
	private boolean mIsPullToRefreshEnabled = true;

	/** 顶部下拉刷新的布局 */
	private PullToRefreshHeadLayout mHeaderLayout;
	/** 下拉刷新布局的高度 */
	private int mHeaderHeight;

	private final Handler mHandler = new Handler();

	/** 刷新的监听 */
	private PullToRefreshListener mOnRefreshListener;

	/** 控制当前滑动收缩的线程 */
	private SmoothScrollRunnable mCurrentSmoothScrollRunnable;

	public PullToRefreshBase(Context context) {
		super(context);
		init(context, null);
	}

	public PullToRefreshBase(Context context, int mode) {
		super(context);
		mMode = mode;
		init(context, null);
	}

	public PullToRefreshBase(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	/**
	 * 得到当前刷新的view
	 * 
	 * @return 返回刷新的视图
	 */
	public final T getRefreshableView() {
		return mRefreshableView;
	}

	/**
	 * 是否能下拉刷新
	 * 
	 * @return 是否可以下拉刷新
	 */
	public final boolean isPullToRefreshEnabled() {
		return mIsPullToRefreshEnabled;
	}

	/**
	 * 刷新的时候是否能滚动
	 * 
	 * @return 是否能滚动
	 */
	public final boolean isDisableScrollingWhileRefreshing() {
		return mDisableScrollingWhileRefreshing;
	}

	/**
	 * 设置刷新中的时候是否能滚动
	 * 
	 * @param disableScrollingWhileRefreshing
	 *            是否能滚动
	 */
	public final void setDisableScrollingWhileRefreshing(
			boolean disableScrollingWhileRefreshing) {
		mDisableScrollingWhileRefreshing = disableScrollingWhileRefreshing;
	}

	/**
	 * 是否正在刷新
	 * 
	 * @return 是否正在刷新
	 */
	public final boolean isRefreshing() {
		return mState == REFRESHING || mState == MANUAL_REFRESHING;
	}

	/**
	 * 添加刷新时间
	 * 
	 * @param time
	 *            时间
	 */
	public void setRefreshTime(String time) {
		if (null != time) {
			mHeaderLayout.setRefreshTime(time);
		}
	}

	/**
	 * 下拉，开始刷新
	 */
	public final void onRefreshStart() {
		if (!isRefreshing()) {
			mCurrentMode = MODE_PULL_DOWN_TO_REFRESH;
			setRefreshingInternal(true);
			mState = MANUAL_REFRESHING;
			mOnRefreshListener.onPull();
		}
	}

	/**
	 * 刷新完成
	 */
	public final void onRefreshComplete() {
		if (mState != PULL_TO_REFRESH) {
			resetHeader();
		}
	}

	/**
	 * 刷新的监听
	 * 
	 * @param listener
	 *            刷新监听
	 */
	public final void setPullToRefreshListener(PullToRefreshListener listener) {
		mOnRefreshListener = listener;
	}

	/**
	 * 设置是否能下拉刷新
	 * 
	 * @param enable
	 *            能否刷新
	 */
	public final void setPullToRefreshEnabled(boolean enable) {
		mIsPullToRefreshEnabled = enable;
	}

	/**
	 * 设置释放时的文字
	 * 
	 * @param releaseLabel
	 *            释放时的文字
	 */
	public void setReleaseLabel(String releaseLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setReleaseLabel(releaseLabel);
		}
	}

	/**
	 * 设置下拉时的文字
	 * 
	 * @param pullLabel
	 *            下拉时的文字
	 */
	public void setPullLabel(String pullLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setPullLabel(pullLabel);
		}
	}

	/**
	 * 设置刷新时的文字
	 * 
	 * @param refreshingLabel
	 *            刷新时的文字
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		if (null != mHeaderLayout) {
			mHeaderLayout.setRefreshingLabel(refreshingLabel);
		}
	}

	/**
	 * 重置头部回到初始状态
	 */
	protected void resetHeader() {
		mState = PULL_TO_REFRESH;
		mIsBeingDragged = false;

		if (null != mHeaderLayout) {
			mHeaderLayout.reset();
		}
		smoothScrollTo(0);
	}

	/**
	 * 设置刷新
	 * 
	 * @param doScroll
	 *            是否滚动
	 */
	protected void setRefreshingInternal(boolean doScroll) {
		mState = REFRESHING;

		if (null != mHeaderLayout) {
			mHeaderLayout.refreshing();
		}
		if (doScroll) {
			smoothScrollTo(mCurrentMode == MODE_PULL_DOWN_TO_REFRESH ? -mHeaderHeight
					: mHeaderHeight);
		}
	}

	/**
	 * 设置头部滚动
	 * 
	 * @param y
	 *            滚动的位置
	 */
	protected final void setHeaderScroll(int y) {
		scrollTo(0, y);
	}

	/**
	 * 滚动到某个位置
	 * 
	 * @param y
	 *            滚动的位置
	 */
	private final void smoothScrollTo(int y) {
		if (null != mCurrentSmoothScrollRunnable) {
			mCurrentSmoothScrollRunnable.stop();
		}

		if (getScrollY() != y) {
			mCurrentSmoothScrollRunnable = new SmoothScrollRunnable(mHandler,
					getScrollY(), y);
			mHandler.post(mCurrentSmoothScrollRunnable);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mIsPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && mDisableScrollingWhileRefreshing) {
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN
				&& event.getEdgeFlags() != 0) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE: {
			if (mIsBeingDragged) {
				mLastMotionY = event.getY();
				pullEvent();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				mOnRefreshListener.onPull();
				mLastMotionY = mInitialMotionY = event.getY();
				return true;
			}
			break;
		}

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (mIsBeingDragged) {
				mIsBeingDragged = false;

				if (mState == RELEASE_TO_REFRESH && null != mOnRefreshListener) {
					setRefreshingInternal(true);
					mOnRefreshListener.onRefresh();
				} else {
					smoothScrollTo(0);
					mState = PULL_TO_REFRESH;
				}
				return true;
			}
			break;
		}
		}

		return false;
	}

	@Override
	public final boolean onInterceptTouchEvent(MotionEvent event) {

		if (!mIsPullToRefreshEnabled) {
			return false;
		}

		if (isRefreshing() && mDisableScrollingWhileRefreshing) {
			return true;
		}

		final int action = event.getAction();

		if (action == MotionEvent.ACTION_CANCEL
				|| action == MotionEvent.ACTION_UP) {
			mIsBeingDragged = false;
			return false;
		}

		if (action != MotionEvent.ACTION_DOWN && mIsBeingDragged) {
			return true;
		}

		switch (action) {
		case MotionEvent.ACTION_MOVE: {
			if (isReadyForPull()) {

				final float y = event.getY();
				final float dy = y - mLastMotionY;
				final float yDiff = Math.abs(dy);
				final float xDiff = Math.abs(event.getX() - mLastMotionX);

				if (yDiff > mTouchSlop && yDiff > xDiff) {
					if ((mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH)
							&& dy >= 0.0001f && isReadyForPullDown()) {
						mLastMotionY = y;
						mIsBeingDragged = true;
						if (mMode == MODE_BOTH) {
							mCurrentMode = MODE_PULL_DOWN_TO_REFRESH;
						}
					}
				}
			}
			break;
		}
		case MotionEvent.ACTION_DOWN: {
			if (isReadyForPull()) {
				mLastMotionY = mInitialMotionY = event.getY();
				mLastMotionX = event.getX();
				mIsBeingDragged = false;
				mOnRefreshListener.onPull();
			}
			break;
		}
		}

		return mIsBeingDragged;
	}

	/**
	 * 添加当前刷新的view
	 * 
	 * @param context
	 * @param refreshableView
	 *            刷新的视图
	 */
	protected void addRefreshableView(Context context, T refreshableView) {
		addView(refreshableView, new LayoutParams(
				LayoutParams.FILL_PARENT, 0, 1.0f));
	}

	/**
	 * 创建刷新的视图
	 * 
	 * @param context
	 * @param attrs
	 *            属性
	 * @return 刷新的视图
	 */
	protected abstract T createRefreshableView(Context context,
			AttributeSet attrs);

	/**
	 * 得到当前状态
	 * 
	 * @return 当前的状态
	 */
	protected final int getCurrentMode() {
		return mCurrentMode;
	}

	/**
	 * 得到头部layout布局
	 * 
	 * @return 头部layout
	 */
	protected final PullToRefreshHeadLayout getHeaderLayout() {
		return mHeaderLayout;
	}

	/**
	 * 得到头部布局的高度
	 * 
	 * @return 头部布局高度
	 */
	protected final int getHeaderHeight() {
		return mHeaderHeight;
	}

	/**
	 * 得到刷新的模式
	 * 
	 * @return 刷新的模式
	 */
	protected final int getMode() {
		return mMode;
	}

	protected abstract boolean isReadyForPullDown();

	protected abstract boolean isReadyForPullUp();

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param attrs
	 *            属性列表
	 */
	private void init(Context context, AttributeSet attrs) {

		setOrientation(LinearLayout.VERTICAL);

		mTouchSlop = ViewConfiguration.getTouchSlop();

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.PullToRefreshWebView);
		if (a.hasValue(R.styleable.PullToRefreshWebView_mode)) {
			mMode = a.getInteger(R.styleable.PullToRefreshWebView_mode,
					MODE_PULL_DOWN_TO_REFRESH);
		}

		mRefreshableView = createRefreshableView(context, attrs);
		addRefreshableView(context, mRefreshableView);

		String pullLabel = context
				.getString(R.string.pull_to_refresh_pull_label);
		String refreshingLabel = context
				.getString(R.string.pull_to_refresh_refreshing_label);
		String releaseLabel = context
				.getString(R.string.pull_to_refresh_release_label);

		if (mMode == MODE_PULL_DOWN_TO_REFRESH || mMode == MODE_BOTH) {
			mHeaderLayout = new PullToRefreshHeadLayout(context,
					MODE_PULL_DOWN_TO_REFRESH, releaseLabel, pullLabel,
					refreshingLabel);
			addView(mHeaderLayout, 0, new LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			measureView(mHeaderLayout);
			mHeaderHeight = mHeaderLayout.getMeasuredHeight();
		}
		if (a.hasValue(R.styleable.PullToRefreshWebView_headerTextColor)) {
			final int color = a.getColor(
					R.styleable.PullToRefreshWebView_headerTextColor, Color.BLACK);
			if (null != mHeaderLayout) {
				mHeaderLayout.setTextColor(color);
			}
		}
		if (a.hasValue(R.styleable.PullToRefreshWebView_headerBackground)) {
			setBackgroundResource(a.getResourceId(
					R.styleable.PullToRefreshWebView_headerBackground, Color.WHITE));
		}
		if (a.hasValue(R.styleable.PullToRefreshWebView_adapterViewBackground)) {
			mRefreshableView.setBackgroundResource(a.getResourceId(
					R.styleable.PullToRefreshWebView_adapterViewBackground,
					Color.WHITE));
		}
		a.recycle();

		switch (mMode) {
		case MODE_BOTH:
			setPadding(0, -mHeaderHeight, 0, -mHeaderHeight);
			break;
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			setPadding(0, -mHeaderHeight, 0, 0);
			break;
		}

		if (mMode != MODE_BOTH) {
			mCurrentMode = mMode;
		}
	}

	/**
	 * 测量视图
	 * 
	 * @param child
	 *            子视图
	 */
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 下拉事件
	 * 
	 * @return 是否消费触摸事件
	 */
	private boolean pullEvent() {

		final int newHeight;
		final int oldHeight = getScrollY();

		switch (mCurrentMode) {
		case MODE_PULL_DOWN_TO_REFRESH:
		default:
			newHeight = Math.round(Math.min(mInitialMotionY - mLastMotionY, 0)
					/ FRICTION);
			break;
		}

		setHeaderScroll(newHeight);

		if (newHeight != 0) {
			if (mState == PULL_TO_REFRESH
					&& mHeaderHeight < Math.abs(newHeight)) {
				mState = RELEASE_TO_REFRESH;

				switch (mCurrentMode) {
				case MODE_PULL_DOWN_TO_REFRESH:
					mHeaderLayout.releaseToRefresh();
					break;
				}

				return true;

			} else if (mState == RELEASE_TO_REFRESH
					&& mHeaderHeight >= Math.abs(newHeight)) {
				mState = PULL_TO_REFRESH;

				switch (mCurrentMode) {
				case MODE_PULL_DOWN_TO_REFRESH:
					mHeaderLayout.pullToRefresh();
					break;
				}

				return true;
			}
		}

		return oldHeight != newHeight;
	}

	/**
	 * 是否下拉准备就绪
	 * 
	 * @return 是否就绪
	 */
	private boolean isReadyForPull() {
		switch (mMode) {
		case MODE_PULL_DOWN_TO_REFRESH:
			return isReadyForPullDown();
		case MODE_BOTH:
			return isReadyForPullUp() || isReadyForPullDown();
		}
		return false;
	}

	@Override
	public void setLongClickable(boolean longClickable) {
		getRefreshableView().setLongClickable(longClickable);
	}

	/**
	 * 控制滑动收缩的线程
	 */
	private final class SmoothScrollRunnable implements Runnable {

		static final int ANIMATION_DURATION_MS = 190;
		static final int ANIMATION_FPS = 1000 / 60;
		/** 定义动画的变化率 */
		private final Interpolator mInterpolator;
		/** 滚动到Y的距离 */
		private final int mScrollToY;
		/** 从Y轴滚动的距离 */
		private final int mScrollFromY;
		private final Handler mHandler;

		/** 是否继续滑动 */
		private boolean mContinueRunning = true;
		/** 开始时间 */
		private long mStartTime = -1;
		/** 当前Y坐标 */
		private int mCurrentY = -1;

		/**
		 * 页面滑动的线程
		 * 
		 * @param handler
		 * @param fromY
		 * @param toY
		 */
		public SmoothScrollRunnable(Handler handler, int fromY, int toY) {
			mHandler = handler;
			mScrollFromY = fromY;
			mScrollToY = toY;
			mInterpolator = new AccelerateDecelerateInterpolator();
		}

		@Override
		public void run() {
			if (mStartTime == -1) {
				mStartTime = System.currentTimeMillis();
			} else {
				long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime))
						/ ANIMATION_DURATION_MS;
				normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);

				final int deltaY = Math.round((mScrollFromY - mScrollToY)
						* mInterpolator
								.getInterpolation(normalizedTime / 1000f));
				mCurrentY = mScrollFromY - deltaY;
				setHeaderScroll(mCurrentY);
			}

			if (mContinueRunning && mScrollToY != mCurrentY) {
				mHandler.postDelayed(this, ANIMATION_FPS);
			}
		}

		/**
		 * 停止滑动线程
		 */
		public void stop() {
			mContinueRunning = false;
			mHandler.removeCallbacks(this);
		}
	}

}