package android.enlight.com.common.retrofitnetwork;

import android.enlight.com.common.data.WebUrl;
import android.enlight.com.common.exception.NetworkNotException;
import android.enlight.com.common.utils.Tools;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/8/30.
 * 请求的基础类
 */

public class RequestBase {
    //创建一个单例对象
    private static RequestBase instance=null;
    //retrofit对象
    private Retrofit retrofit;
    //自定义一个OKHttpClient对象处理一些统一的请求
    private OkHttpClient okHttpClient;
    //添加一个请求拦截器做一下统一处理
    private HeardInterceptor heardInterceptor;
    public RequestBase() {
        instance=this;
        //创建请求头拦截
        heardInterceptor=new HeardInterceptor();
        //创建强求类
        okHttpClient=new OkHttpClient().newBuilder()
                .addInterceptor(heardInterceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        /*Request request = chain.request();
                        okhttp3.Response proceed = chain.proceed(request);
                        proceed.code();*/
                        boolean connected = Tools.getNetworkInfo().isConnectToNetwork();
                        if (connected) {
                            okhttp3.Response proceed=chain.proceed(chain.request());
                            int code=proceed.code();
                            if(code==200){
                                return proceed;
                            }
                            return null;
                        } else {
                            throw new NetworkNotException();
                        }
                    }
                })
                .build();
        //创建retrofit对象
        retrofit=new Retrofit.Builder().baseUrl(WebUrl.WEB_ROOT)
                .client(okHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 获取请求类实例
     * @return 当前请求实例
     */
    public static RequestBase getInstance(){
        return (instance==null)?new RequestBase():instance;
    }

    /**
     * 获取请求接口的具体实现
     * @param reqServer 请求接口
     * @param <T>
     * @return 请求接口的具体实现类
     */
    public <T> T createReq(Class<T> reqServer) {
        return retrofit.create(reqServer);
    }

    public <T> Observable.Transformer<T,T> applyAsySchedulers(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new RequestFunc<T>());
            }
        };
    }

    //进行数据的转化或组合
    public class RequestFunc<T> implements Func1<T,T> {
        @Override
        public T call(T t) {
            /*String codeValue=null;
            String msgValue=null;
            try {
                Field code=t.getClass().getDeclaredField("code");
                Field msg=t.getClass().getDeclaredField("msg");
                if(!code.isAccessible()){
                    code.setAccessible(true);
                }
                msg.setAccessible(true);
                codeValue=(String)code.get(t);
                msgValue=(String)msg.get(t);
                if(codeValue.equals("101")){
                    throw new ApiException(codeValue,msgValue);
                }
                Log.i("CodeValue",codeValue);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }*/
            return t;
        }
    }
}
