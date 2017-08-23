package android.enlight.com.view.customview;

import android.content.Context;
import android.enlight.com.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新 顶部的layout
 * 
 */
public class PullToRefreshHeadLayout extends FrameLayout {

	/** 动画持续的时间 */
	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	/** 下拉刷新的箭头图标 */
	private final ImageView mHeaderImage;
	/** 下拉刷新进度条 */
	private final ProgressBar mHeaderProgress;
	/** 下拉刷新的文字提示和更新时间 */
	public final TextView mHeaderText, mHeaderUpdateTimeText;

	/** 下拉的文字 */
	private String mPullLabel;
	/** 刷新中的文字 */
	private String mRefreshingLabel;
	/** 释放文字 */
	private String mReleaseLabel;

	/** 下拉刷新的动画 */
	private final Animation mRotateAnimation, mResetRotateAnimation;

	/**
	 * LoadingLayout 的构造函数 初始化控件
	 * 
	 * @param context
	 *            上下文
	 * @param mode
	 *            刷新的方式
	 * @param releaseLabel
	 *            释放刷新
	 * @param pullLabel
	 *            下拉刷新
	 * @param refreshingLabel
	 *            刷新中
	 */
	public PullToRefreshHeadLayout(Context context, final int mode,
								   String releaseLabel, String pullLabel, String refreshingLabel) {
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(
				R.layout.pull_to_refresh_header, this);
		mHeaderText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		mHeaderUpdateTimeText = (TextView) header
				.findViewById(R.id.pull_to_refresh_update_time);
		mHeaderImage = (ImageView) header
				.findViewById(R.id.pull_to_refresh_image);
		mHeaderProgress = (ProgressBar) header
				.findViewById(R.id.pull_to_refresh_progress);

		final Interpolator interpolator = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, -180,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateAnimation.setInterpolator(interpolator);
		mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(-180, 0,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mResetRotateAnimation.setInterpolator(interpolator);
		mResetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);
		mReleaseLabel = releaseLabel;
		mPullLabel = pullLabel;
		mRefreshingLabel = refreshingLabel;
		mHeaderUpdateTimeText.setVisibility(View.GONE);
		switch (mode) {
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
		default:
			mHeaderImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
			break;
		}
	}

	/**
	 * 重新设置 回到开始的状态
	 */
	public void reset() {
		mHeaderText.setText(mPullLabel);
		mHeaderImage.setVisibility(View.VISIBLE);
		mHeaderProgress.setVisibility(View.GONE);
	}

	/**
	 * 释放时文字和动画的设置
	 */
	public void releaseToRefresh() {
		mHeaderText.setText(mReleaseLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mRotateAnimation);
	}

	/**
	 * 设置刷新时间
	 * 
	 * @param time
	 *            时间
	 */
	public void setRefreshTime(String time) {
		if (time == null) {// 第一次下拉刷新不显示更新时间
			mHeaderUpdateTimeText.setVisibility(View.GONE);
		} else {
			mHeaderUpdateTimeText.setVisibility(View.VISIBLE);
			mHeaderUpdateTimeText.setText("最后更新：" + time);
		}
	}

	/**
	 * 设置下拉时的文字
	 * 
	 * @param pullLabel
	 *            下拉时的文字
	 */
	public void setPullLabel(String pullLabel) {
		mPullLabel = pullLabel;
	}

	/**
	 * 刷新时文字、图标、和进度条的设置
	 */
	public void refreshing() {
		mHeaderText.setText(mRefreshingLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.setVisibility(View.INVISIBLE);
		mHeaderProgress.setVisibility(View.VISIBLE);
	}

	/**
	 * 设置刷新中时的文字
	 * 
	 * @param refreshingLabel
	 *            刷新中的文字
	 */
	public void setRefreshingLabel(String refreshingLabel) {
		mRefreshingLabel = refreshingLabel;
	}

	/**
	 * 设置释放时的文字标签
	 * 
	 * @param releaseLabel
	 *            释放时的文字
	 */
	public void setReleaseLabel(String releaseLabel) {
		mReleaseLabel = releaseLabel;
	}

	/**
	 * 下拉刷新
	 */
	public void pullToRefresh() {
		mHeaderText.setText(mPullLabel);
		mHeaderImage.clearAnimation();
		mHeaderImage.startAnimation(mResetRotateAnimation);
	}

	/**
	 * 设置文字颜色
	 * 
	 * @param color
	 *            颜色值
	 */
	public void setTextColor(int color) {
		mHeaderText.setTextColor(color);
	}

}