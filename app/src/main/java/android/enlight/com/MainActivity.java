package android.enlight.com;

import android.enlight.com.common.retrofitnetwork.ProgressSubscriber;
import android.enlight.com.common.retrofitnetwork.RequestBase;
import android.enlight.com.common.retrofitnetwork.api.HttpRequestApi;
import android.enlight.com.common.retrofitnetwork.modle.BaseData;
import android.enlight.com.common.retrofitnetwork.modle.IndexData;
import android.enlight.com.common.retrofitnetwork.modle.MovieData;
import android.enlight.com.common.view.BaseActivity;
import android.enlight.com.view.customview.EnlightTabHost;
import android.view.View;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.main_tab_video)
    private View main_tab_video;
    @BindView(R.id.main_tab_life)
    private View main_tab_life;
    @BindView(R.id.main_tab_navigation)
    private View main_tab_navigation;
    @BindView(R.id.main_tab_space)
    private View main_tab_space;
    @BindView(android.R.id.tabhost)
    private EnlightTabHost mTabhost;
    private String[] listUrl={"www.baidu.com","www.cbchot.com"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initialized() {
        RequestBase requestBase=RequestBase.getInstance();
        HttpRequestApi httpRequestApi=requestBase.createReq(HttpRequestApi.class);
        httpRequestApi.getMovieList("4").compose(requestBase.<BaseData<MovieData>>applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<MovieData>>(MainActivity.this,false) {
            @Override
            public void onNext(BaseData<MovieData> movielistBaseData) {
                movielistBaseData.getData().getTotal();
            }
        });
        httpRequestApi.getIndexList().compose(requestBase.<BaseData<IndexData>>applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<IndexData>>(MainActivity.this,false) {
            @Override
            public void onNext(BaseData<IndexData> indexDataBaseData) {
                indexDataBaseData.getData().getList().getApp_ew_home_actor();
            }
        });
       /* httpRequestApi.getMovieList("4").compose(requestBase.applyAsySchedulers()).subscribe(new ProgressSubscriber<BaseData<MovieData>>(MainActivity.this,false) {
            @Override
            public void onNext(BaseData<MovieData> movielistBaseData) {
                movielistBaseData.getData().getTotal();
            }
        });*/
        /*NormalRequest normalRequest=new NormalRequest();
        normalRequest.getTest(this);*/
        /*mTabhost.setup(this,getFragmentManager(),R.id.main_tabcontent);
        for (int i = 0; i < listUrl.length; i++) {

        }*/
    }
}
