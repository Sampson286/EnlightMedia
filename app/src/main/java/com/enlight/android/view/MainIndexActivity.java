package com.enlight.android.view;

import android.app.FragmentManager;
import android.enlight.com.R;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.enlight.android.common.view.BaseActivity;
import com.enlight.android.view.customview.EnlightTabHost;
import com.enlight.android.view.fragment.EnlightMainIndexFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainIndexActivity extends BaseActivity {
    //获取控件引用
    @BindView(R.id.main_tab_index)
    View main_tab_index;
    @BindView(R.id.main_tab_news)
    View main_tab_news;
    @BindView(R.id.main_tab_video)
    View main_tab_video;
    @BindView(R.id.main_tab_activity)
    View main_tab_activity;
    @BindView(R.id.main_tab_space)
    View main_tab_space;
    @BindView(android.R.id.tabhost)
    EnlightTabHost mTabhost;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    /** Fragment的管理 */
    private FragmentManager fragmentManager;
    /** 主页面Tab的标签 首页、新闻、视频、活动、我*/
    public enum TabTag {
        index, news, video, activity, space;
    }
    @Override
    protected void initialized() {
        fragmentManager=getFragmentManager();
        mTabhost.setup(MainIndexActivity.this, fragmentManager,
                R.id.main_tabcontent);
        for (int i=0;i<TabTag.values().length;i++){
            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(TabTag.values()[i].toString())
                    .setIndicator(TabTag.values()[i].toString());
            Bundle bundle = new Bundle();
            bundle.putString("TAG",TabTag.values()[i].toString());
            switch (i){
                case 0:
                    mTabhost.addTab(tabSpec, EnlightMainIndexFragment.class,bundle);
                    break;
                case 1:
                    mTabhost.addTab(tabSpec, EnlightMainIndexFragment.class,bundle);
                    break;
                case 2:
                    mTabhost.addTab(tabSpec, EnlightMainIndexFragment.class,bundle);
                    break;
                case 3:
                    mTabhost.addTab(tabSpec, EnlightMainIndexFragment.class,bundle);
                    break;
                case 4:
                    mTabhost.addTab(tabSpec, EnlightMainIndexFragment.class,bundle);
                    break;
            }
        }
        //设置顶部的TabWidget不显示
        mTabhost.getTabWidget().setVisibility(View.GONE);
        mTabhost.setCurrentTab(0);
        mTabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (TabTag.valueOf(tabId)) {
                    case index:
                        break;
                    case news:
                        break;
                    case video:
                        break;
                    case activity:
                        break;
                    case space:
                        break;
                }
            }
        });
        /*RequestBase requestBase=RequestBase.getInstance();
        HttpRequestApi httpRequestApi=requestBase.createReq(HttpRequestApi.class);
        httpRequestApi.getMovieList("4").compose(requestBase.<BaseData<MovieData>>applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<MovieData>>(MainIndexActivity.this,false) {
            @Override
            public void onNext(BaseData<MovieData> movielistBaseData) {
                movielistBaseData.getData().getTotal();
            }
        });
        httpRequestApi.getIndexList().compose(requestBase.<BaseData<IndexData>>applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<IndexData>>(MainIndexActivity.this,false) {
            @Override
            public void onNext(BaseData<IndexData> indexDataBaseData) {
                indexDataBaseData.getData().getList().getApp_ew_home_actor();

            }
        });*/
    }
    @OnClick({ R.id.main_tab_index, R.id.main_tab_news, R.id.main_tab_video,R.id.main_tab_activity,R.id.main_tab_space })
    public void onTabClick(View view){
        switch (view.getId()){
            case R.id.main_tab_index:
                changeTab(TabTag.index);
                break;
            case R.id.main_tab_news:
                changeTab(TabTag.news);
                break;
            case R.id.main_tab_video:
                changeTab(TabTag.video);
                break;
            case R.id.main_tab_activity:
                changeTab(TabTag.activity);
                break;
            case R.id.main_tab_space:
                changeTab(TabTag.space);
                break;
        }
    }

    /**
     * 切换tab
     * @param tabTag 标签
     */
    private void changeTab(TabTag tabTag){
        mTabhost.setCurrentTabByTag(tabTag.toString());
        switch (tabTag){
            case index:
                main_tab_index.setSelected(true);
                main_tab_news.setSelected(false);
                main_tab_video.setSelected(false);
                main_tab_activity.setSelected(false);
                main_tab_space.setSelected(false);
                break;
            case news:
                main_tab_index.setSelected(false);
                main_tab_news.setSelected(true);
                main_tab_video.setSelected(false);
                main_tab_activity.setSelected(false);
                main_tab_space.setSelected(false);
                break;
            case video:
                main_tab_index.setSelected(false);
                main_tab_news.setSelected(false);
                main_tab_video.setSelected(true);
                main_tab_activity.setSelected(false);
                main_tab_space.setSelected(false);
                break;
            case activity:
                main_tab_index.setSelected(false);
                main_tab_news.setSelected(false);
                main_tab_video.setSelected(false);
                main_tab_activity.setSelected(true);
                main_tab_space.setSelected(false);
                break;
            case  space:
                main_tab_index.setSelected(false);
                main_tab_news.setSelected(false);
                main_tab_video.setSelected(false);
                main_tab_activity.setSelected(false);
                main_tab_space.setSelected(true);
                break;
        }

    }

}
