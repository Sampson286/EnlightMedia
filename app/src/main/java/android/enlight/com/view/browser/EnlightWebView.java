package android.enlight.com.view.browser;

import android.content.Context;
import android.enlight.com.view.customview.PullToRefreshBase;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by zyc on 2017/8/22.
 */

public class EnlightWebView extends PullToRefreshBase<WebView> {
    public EnlightWebView(Context context) {
        this(context,null);
    }
    public EnlightWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRefreshableView.getSettings().setJavaScriptEnabled(true);
        mRefreshableView.getSettings().setSaveFormData(false);
        mRefreshableView.getSettings().setSavePassword(false);
        mRefreshableView.getSettings().setBuiltInZoomControls(false);
        requestFocusFromTouch();
        mRefreshableView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mRefreshableView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // mRefreshableView.clearCache(true);// 清除缓存
        // mRefreshableView.clearHistory();// 清除浏览历史
        // mRefreshableView.clearFormData();// 清除表单数据
        // 处理webview显示网页白边问题 设置滚动条在内部网页内部还是外部显示 外部显示会占用一定空间 导致白边
        mRefreshableView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//		mRefreshableView.getSettings().setUseWideViewPort(true);
//		mRefreshableView.getSettings().setLoadWithOverviewMode(true);
//		mRefreshableView.getSettings().setBuiltInZoomControls(true);
//		mRefreshableView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		mRefreshableView.getSettings().setSavePassword(true);
//		mRefreshableView.getSettings().setSaveFormData(true);
//		mRefreshableView.getSettings().setGeolocationEnabled(true);
//		mRefreshableView.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage
        mRefreshableView.getSettings().setDomStorageEnabled(true);
//		mRefreshableView.requestFocus();
    }

    @Override
    protected WebView createRefreshableView(Context context, AttributeSet attrs) {
        return new WebView(context,attrs);
    }

    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullUp() {
        return mRefreshableView.getScrollY() >= (mRefreshableView
                .getContentHeight() - mRefreshableView.getHeight());
    }
}
