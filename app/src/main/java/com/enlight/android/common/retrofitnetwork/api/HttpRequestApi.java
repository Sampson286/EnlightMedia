package com.enlight.android.common.retrofitnetwork.api;

import com.enlight.android.common.data.WebUrl;
import com.enlight.android.common.retrofitnetwork.modle.BaseData;
import com.enlight.android.common.retrofitnetwork.modle.IndexData;
import com.enlight.android.common.retrofitnetwork.modle.MovieData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/8/30.
 */

public interface HttpRequestApi {
    @GET(WebUrl.INDEX_LIST)
    Observable<BaseData<IndexData>> getIndexList();
    //创建获取电影集合的请求
    @GET(WebUrl.MOVE_LIST)
    Observable<BaseData<MovieData>> getMovieList(@Query("movietype") String movietype);;
}
