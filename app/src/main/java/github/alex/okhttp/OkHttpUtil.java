package github.alex.okhttp;

import com.alex.mvpapp.BuildConfig;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by hasee on 2016/6/22.
 */
public class OkHttpUtil {
    private static final long connectTimeout = 10 * 1000;
    private static final long readTimeout = 10 * 1000;
    private static final long writeTimeout = 10 * 1000;
    private static OkHttpUtil instance;
    public static OkHttpClient okHttpClient;

    private OkHttpUtil() {
    }

    /**
     * 单例模式  获取 OkHttpUtil
     * <pre>
     *     默认请求超时时间为 10秒
     * </pre>
     */
    public static OkHttpUtil getInstance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                instance = (instance == null) ? new OkHttpUtil() : instance;
            }
        }
        return instance;
    }
    public OkHttpClient getOkHttpClient() {
        return getOkHttpClient(null, null);
    }
    public OkHttpClient getOkHttpClient(final HttpLoggingInterceptor.Logger logInterceptor) {
        return getOkHttpClient(logInterceptor, null);
    }
    public OkHttpClient getOkHttpClient(final HttpLoggingInterceptor.Logger logInterceptor, HeadParams headParams) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //KLog.e(message);
                    if (logInterceptor != null) {
                        logInterceptor.log(message);
                    }
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        Logger.e("headParams = "+(headParams==null));
        if(headParams != null){
            builder.addInterceptor(new HeadInterceptor(headParams));
        }
        OkHttpClient okHttpClient = builder.build();
        OkHttpClient.Builder newBuilder = okHttpClient.newBuilder();
        /**设置请求超时时间*/
        newBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        newBuilder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        newBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        newBuilder.retryOnConnectionFailure(true);
        return okHttpClient;
    }

    private final class HeadInterceptor implements Interceptor {
        private HeadParams headParams;
        public HeadInterceptor(HeadParams headParams) {
            this.headParams = headParams;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cookie", "add cookies here");
            Map<String, String>  stringHeadMap = headParams.getHeadMap();
             /*解析文本请求头*/
            if ((stringHeadMap != null) && (stringHeadMap.size() > 0)) {
                Iterator<?> iterator = stringHeadMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    @SuppressWarnings("rawtypes")
                    java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
                    requestBuilder.addHeader(entry.getKey() + "", entry.getValue() + "");
                    Logger.e(entry.getKey() + "  "+entry.getValue() + "");
                }
            }
            //Request request =
            //.build();
            return chain.proceed(requestBuilder.build());
        }
    }
}
