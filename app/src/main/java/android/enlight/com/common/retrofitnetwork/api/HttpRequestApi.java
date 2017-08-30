package android.enlight.com.common.retrofitnetwork.api;

import android.enlight.com.common.data.WebUrl;
import android.enlight.com.common.retrofitnetwork.modle.BaseData;
import android.enlight.com.common.retrofitnetwork.modle.IndexData;
import android.enlight.com.common.retrofitnetwork.modle.MovieData;

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
