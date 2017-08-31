package com.enlight.android.common.view;

import android.app.Activity;
import com.enlight.android.common.application.ApplicationData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zyc on 2017/8/17.
 */

public abstract class BaseActivity extends Activity {
    /** 布局转换器*/
    public LayoutInflater mInflater;
    /** 布局内容视图*/
    protected View mContentView;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //添加到集合用于退出时销毁
        ApplicationData.globalContext.addActivityToMap(this);
        mInflater = LayoutInflater.from(this);
        int layoutId = getLayoutId();
        if(layoutId != 0){
            setContentView(layoutId);
        } else if(null != mContentView){
            setContentView(mContentView);
        } else {
            throw new IllegalStateException("layoutId can not be 0 or contentView should be setted");
        }
        unbinder= ButterKnife.bind(this);
        initialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 布局文件ID
     * @return 布局ID
     */
    protected abstract int getLayoutId();
    /**
     * 初始化方法
     */
    protected abstract void initialized();
}
