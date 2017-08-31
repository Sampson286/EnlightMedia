package com.enlight.android.view.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.enlight.com.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enlight.android.common.retrofitnetwork.ProgressSubscriber;
import com.enlight.android.common.retrofitnetwork.RequestBase;
import com.enlight.android.common.retrofitnetwork.api.HttpRequestApi;
import com.enlight.android.common.retrofitnetwork.modle.BaseData;
import com.enlight.android.common.retrofitnetwork.modle.IndexData;
import com.enlight.android.common.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyc on 2017/8/30.
 * E网首页的Fragment
 */

public class EnlightMainIndexFragment extends Fragment {
    Banner main_index_fragment_banner;
    //banner图数据
    private List<String> images=new ArrayList<String>();
    private Activity currentActivity;
    private View enlightMainIndexView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(),"测试",Toast.LENGTH_SHORT).show();
        enlightMainIndexView =inflater.inflate(R.layout.main_index_fragment_layout,container,false);
        main_index_fragment_banner=(Banner)enlightMainIndexView.findViewById(R.id.main_index_fragment_banner);
        return enlightMainIndexView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity=getActivity();
        initData();

    }
    private void initData(){
        RequestBase requestBase= RequestBase.getInstance();
        HttpRequestApi httpRequestApi=requestBase.createReq(HttpRequestApi.class);
        httpRequestApi.getIndexList().compose(requestBase.<BaseData<IndexData>>applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<IndexData>>(currentActivity,false) {
            @Override
            public void onNext(BaseData<IndexData> indexDataBaseData) {
                List<IndexData.ListBean.AppEwHomeFocusBean> appEwHomeFocusBeen= indexDataBaseData.getData().getList().getApp_ew_home_focus();
                for (int i=0;i<appEwHomeFocusBeen.size();i++) {
                    images.add(appEwHomeFocusBeen.get(i).getImg());
                }
                //设置图片加载器
                main_index_fragment_banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                main_index_fragment_banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                main_index_fragment_banner.start();
            }
        });
    }
}
