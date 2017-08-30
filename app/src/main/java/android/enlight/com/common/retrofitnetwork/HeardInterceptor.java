package android.enlight.com.common.retrofitnetwork;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2017/8/30.
 * 用于拦截添加请求头信息
 */

public class HeardInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request modifyRequest=request.newBuilder()
                    /*.header("sessionid","3c69bd8ec75a47ce9e5411c4b5986acb")
                    .header("X-Client","")*/
                .build();
        return chain.proceed(modifyRequest);
    }
}
