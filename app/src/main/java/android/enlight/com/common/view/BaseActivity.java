package android.enlight.com.common.view;

import android.enlight.com.common.application.ApplicationData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zyc on 2017/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /** 布局转换器*/
    public LayoutInflater mInflater;
    /** 布局内容视图*/
    protected View mContentView;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
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
