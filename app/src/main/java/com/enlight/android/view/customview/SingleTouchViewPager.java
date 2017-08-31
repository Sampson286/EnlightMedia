package com.enlight.android.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by zyc on 2017/8/30.
 * 单点击ViewPager
 */

public class SingleTouchViewPager extends ViewPager {
    /**
     * 手指触摸时按下的X,Y坐标
     */
    private int touchDownX, touchDownY;
    /**
     * 是否移动了
     */
    private boolean isMoved;
    /**
     * 触摸移动最小值
     */
    private int touchSlop = 0;
    /**
     * 是否是在向左滑动
     */
    private boolean isFillingLeft = true;
    /**
     * 手势识别类
     */
    private GestureDetector gestureDetector;
    /**
     * 垂直方向最小距离
     */
    private int verticalMinDistance = 10;
    /**
     * 最小滑动速度
     */
    private int minVelocity = 0;
    /**
     * 单击事件监听器
     */
    private OnSingleTouchListener onSingleTouchListener;

    public SingleTouchViewPager(Context context) {
        super(context);
        initData(context);
    }

    public SingleTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    /**
     * 初始化数据
     *
     * @param context
     */
    private void initData(Context context) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        gestureDetector = new GestureDetector(new GuideViewTouch());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 每次进行onTouch事件都记录当前的按下的坐标
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownX = x;
            touchDownY = y;
            isMoved = false;
            if (isFillingLeft) {
                // 通知父控件此操纵让子控件自己处理，父控件不要干扰
                if (getCurrentItem() == getAdapter().getCount() - 1) {//当前view为最后一个view
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(touchDownX - x) > touchSlop
                    || Math.abs(touchDownY - y) > touchSlop) {
                isMoved = true;
            }
            if (isFillingLeft) {
                // 通知父控件此操纵让子控件自己处理，父控件不要干扰
                if (getCurrentItem() == getAdapter().getCount() - 1) {//当前view为最后一个view
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (!isMoved) {
                onSingleTouch();
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 单击事件
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * 创建点击事件接口
     *
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    /**
     * 设置单击事件
     *
     * @param onSingleTouchListener
     */
    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    /**
     * 手势识别监听器 用于监听识别滑动方向
     *
     */
    private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (e1.getX() - e2.getX() > verticalMinDistance
                    && Math.abs(distanceX) > minVelocity) {
                isFillingLeft = true;
            } else if (e2.getX() - e1.getX() > verticalMinDistance
                    && Math.abs(distanceX) > minVelocity) {
                isFillingLeft = false;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
