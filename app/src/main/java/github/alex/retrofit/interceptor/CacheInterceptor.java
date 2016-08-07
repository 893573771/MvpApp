package github.alex.retrofit.interceptor;

import java.io.IOException;

import github.alex.util.NetUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public final class CacheInterceptor implements Interceptor {
    /**
     * 有网的时候，请求超时的时间 单位秒，默认60s
     */
    private long hasNetCacheTime;
    /**
     * 没网的时候，请求超时的时间 单位秒，默认1天
     */
    private long noNetCacheTime;

    public CacheInterceptor(long hasNetCacheTime, long noNetCacheTime) {
        this.hasNetCacheTime = hasNetCacheTime;
        this.noNetCacheTime = noNetCacheTime;
    }

    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String cacheHeaderValue = NetUtil.isNetworkAvailable()
                ? "public, max-age=" + hasNetCacheTime
                : "public, only-if-cached, max-stale=" + noNetCacheTime;

        Request request = originalRequest.newBuilder().build();
        Response response = chain.proceed(request);
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheHeaderValue)
                .build();
    }
}