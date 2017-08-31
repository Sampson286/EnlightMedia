package com.enlight.android.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by admin on 2017/8/31.
 * 基础的fragment
 */

public abstract class BaseFragment extends Fragment {
    /** 布局内容视图*/
    protected View mContentView;
    protected Activity currentActivity;
    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if(layoutId != 0){
            mContentView=inflater.inflate(layoutId,container,false);
        }else {
            throw new IllegalStateException("layoutId can not be 0 ");
        }
        unbinder = ButterKnife.bind(this, mContentView);
        return mContentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity=getActivity();
        initialized();
    }

    @Override
    public void onDestroy() {
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
