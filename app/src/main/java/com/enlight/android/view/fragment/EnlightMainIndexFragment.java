package com.enlight.android.view.fragment;

import android.enlight.com.R;

import com.enlight.android.common.retrofitnetwork.ProgressSubscriber;
import com.enlight.android.common.retrofitnetwork.RequestBase;
import com.enlight.android.common.retrofitnetwork.api.HttpRequestApi;
import com.enlight.android.common.retrofitnetwork.modle.BaseData;
import com.enlight.android.common.retrofitnetwork.modle.IndexData;
import com.enlight.android.common.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zyc on 2017/8/30.
 * E网首页的Fragment
 */

public class EnlightMainIndexFragment extends BaseFragment {
    @BindView(R.id.main_index_fragment_banner)
    Banner main_index_fragment_banner;
    //banner图数据
    private List<String> images=new ArrayList<String>();
    @Override
    protected int getLayoutId() {
        return R.layout.main_index_fragment_layout;
    }

    @Override
    protected void initialized() {
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
