package github.alex.okhttp;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.alex.app.BuildConfig;

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
 * Created by hasee on 2016/6/22.
 */
public class OkHttpUtil {
    private static final long connectTimeout = 10 * 1000;
    private static final long readTimeout = 10 * 1000;
    private static final long writeTimeout = 10 * 1000;
    private static OkHttpUtil instance;
    public static OkHttpClient okHttpClient;
    private static final String TAG = "网络请求结果";
    private static String cacheDir;
    private static long maxSize;

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
        cacheDir = "OkHttpUtil";
        maxSize = 1024 * 1024 * 100;
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return getOkHttpClient(null, null, null, cacheDir, maxSize);
    }

    public OkHttpClient getOkHttpClient(final HttpLoggingInterceptor.Logger logInterceptor) {
        return getOkHttpClient(logInterceptor, null, null, cacheDir, maxSize);
    }

    public OkHttpClient getOkHttpClient(HeadParams headParams) {
        return getOkHttpClient(null, headParams, null, cacheDir, maxSize);
    }

    public OkHttpClient getOkHttpClient(final HttpLoggingInterceptor.Logger logInterceptor, HeadParams headParams) {
        return getOkHttpClient(null, headParams, null, cacheDir, maxSize);
    }

    public OkHttpClient getOkHttpClient(final HttpLoggingInterceptor.Logger logInterceptor, HeadParams headParams, Context context, String cacheDir, long cacheMaxSize) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
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
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        if (headParams != null) {
            builder.addInterceptor(new HeadInterceptor(headParams));
        }
        if ((context != null) && (TextUtils.isEmpty(cacheDir)) && (cacheMaxSize > 0)) {
            Cache cache = new Cache(getDiskCacheDir(context, cacheDir), cacheMaxSize);
            builder.cache(cache);
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

    private static File getDiskCacheDir(Context context, String cacheDir) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getAbsolutePath();
            } else {
                cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + cacheDir);
    }
}
