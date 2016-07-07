package github.alex.okhttp;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by alex on 2016/6/22.
 * OkHttpUtil for Retrofit
 */
public class OkHttpUtil {
    private static OkHttpUtil instance;
    private static final String TAG = "#日志拦截器#";
    private boolean debug = true;
    private HttpLoggingInterceptor.Logger logInterceptor;
    private HeadParams headParams;
    private long connectTimeout;
    private long readTimeout;
    private long writeTimeout;
    private boolean retryOnConnectionFailure;
    private File cacheDir;
    private long cacheMaxSize;
    private HttpLoggingInterceptor.Level level;

    private OkHttpUtil() {
        cacheMaxSize = 1024 * 1024 * 100;
        level = HttpLoggingInterceptor.Level.BASIC;
        debug = true;
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

    /**
     * 设置 日志 拦截器
     */
    public OkHttpUtil httpLoggingInterceptor(HttpLoggingInterceptor.Logger logInterceptor) {
        this.logInterceptor = logInterceptor;
        return this;
    }

    /**
     * 设置请求头
     */
    public OkHttpUtil headParams(HeadParams headParams) {
        this.headParams = headParams;
        return this;
    }

    /**
     * 设置连接超时时间
     */
    public OkHttpUtil connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 设置读取超时时间
     */
    public OkHttpUtil readTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 设置写入超时时间
     */
    public OkHttpUtil writeTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    /**
     * 连接失败自动重试
     */
    public OkHttpUtil retryOnConnectionFailure(boolean retryOnConnectionFailure) {
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        return this;
    }

    /**
     * 设置缓存路径
     */
    public OkHttpUtil cacheDir(File cacheDir) {
        this.cacheDir = cacheDir;
        return this;
    }

    /**
     * 设置缓存大小
     */
    public OkHttpUtil cacheMaxSize(long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
        return this;
    }

    /**设置log的等级*/
    public OkHttpUtil level(HttpLoggingInterceptor.Level level){
        this.level = level;
        return this;
    }
    /**设置处于 debug */
    public OkHttpUtil debug(boolean debug){
        this.debug = debug;
        return this;
    }

    /**
     * 得到 OkHttpClient 对象
     */
    public OkHttpClient build() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (debug) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e(TAG, message);
                    if (logInterceptor != null) {
                        logInterceptor.log(message);
                    }
                }
            });
            loggingInterceptor.setLevel(level);
            /*要在 OkHttpClient.Builder().build(); 之前，否则日志出不来*/
            builder.addInterceptor(loggingInterceptor);
        }
        if (headParams != null) {
            builder.addInterceptor(new HeadInterceptor(headParams));
        }
        if (cacheDir != null) {
            if (cacheMaxSize < 0) {
                cacheMaxSize = 1024 * 1024 * 128;
            }
            Cache cache = new Cache(cacheDir, cacheMaxSize);
            builder.cache(cache);
        }
        OkHttpClient okHttpClient = builder.build();
        OkHttpClient.Builder newBuilder = okHttpClient.newBuilder();
        if (connectTimeout > 0) {
            newBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        }
        if (readTimeout > 0) {
            newBuilder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        }
        if (writeTimeout > 0) {
            newBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        }
        newBuilder.retryOnConnectionFailure(retryOnConnectionFailure);
        return okHttpClient;
    }


    final class HeadInterceptor implements Interceptor {
        private HeadParams headParams;

        public HeadInterceptor(HeadParams headParams) {
            this.headParams = headParams;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder requestBuilder = chain.request().newBuilder().addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8").addHeader("Accept-Encoding", "gzip, deflate").addHeader("Connection", "keep-alive").addHeader("Accept", "*/*");
            Map<String, String> stringHeadMap = headParams.getHeadMap();
             /*解析文本请求头*/
            if ((stringHeadMap != null) && (stringHeadMap.size() > 0)) {
                Iterator<?> iterator = stringHeadMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    @SuppressWarnings("rawtypes") Map.Entry entry = (Map.Entry) iterator.next();
                    requestBuilder.addHeader(entry.getKey() + "", entry.getValue() + "");
                }
            }
            return chain.proceed(requestBuilder.build());
        }
    }

}
