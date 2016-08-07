package github.alex.retrofit;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import github.alex.retrofit.interceptor.CacheInterceptor;
import github.alex.retrofit.interceptor.GzipRequestInterceptor;
import github.alex.retrofit.interceptor.RequestInterceptor;
import github.alex.util.LogUtil;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
@SuppressWarnings("ALL")
public class RetrofitClient {
    private static RetrofitClient retrofitClient;
    private Builder builder;

    private RetrofitClient() {
        this.builder = (builder == null) ? new Builder() : builder;
    }

    private RetrofitClient(Builder builder) {
        this.builder = builder;
    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            synchronized (RetrofitClient.class) {
                retrofitClient = (retrofitClient == null) ? new RetrofitClient() : retrofitClient;
            }
        }
        return retrofitClient;
    }


    public <T> T create(Class<T> service, String baseUrl, Converter.Factory converterFactory, OkHttpClient okhttpClient) {
        builder.baseUrl = baseUrl;
        builder.converterFactory = converterFactory;
        builder.okHttpClient = okhttpClient;
        return create(service);
    }

    public <T> T create(Class<T> service, String baseUrl) {
        builder.baseUrl = baseUrl;
        return create(service);
    }

    public <T> T create(Class<T> service) {
        Retrofit.Builder builder = new Retrofit.Builder();
        if (!TextUtils.isEmpty(this.builder.baseUrl)) {
            builder.baseUrl(this.builder.baseUrl);
        }

        /*关联 okhttp*/
        if (this.builder.okHttpClient != null) {
            builder.client(this.builder.okHttpClient);
        } else {
            builder.client(new OkHttpClient.Builder().build());
        }

        /**关联 转换器*/
        if (this.builder.converterFactory != null) {
            builder.addConverterFactory(this.builder.converterFactory);
        } else {
            builder.addConverterFactory(FastJsonConverterFactory.create());
        }

        /*关联 rxjava*/
        if (this.builder.callAdapterFactory != null) {
            builder.addCallAdapterFactory(this.builder.callAdapterFactory);
        } else {
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        return builder.build().create(service);
    }

    public static class Builder {
        public Converter.Factory converterFactory;
        public CallAdapter.Factory callAdapterFactory;
        public OkHttpClient okHttpClient;
        public String baseUrl;
        private HttpLoggingInterceptor.Level level;
        /**
         * 默认连接超时15秒
         */
        private long connectTimeout;
        /**
         * 默认读取超时20秒
         */
        private long readTimeout;
        /**
         * 默认写入超时20秒
         */
        private long writeTimeout;
        private boolean retryOnConnectionFailure;
        private String cacheDir;
        private long cacheMaxSize;
        private String separator;
        /**
         * 是否使用磁盘缓存， 默认使用
         */
        private boolean isUseDiskCache;
        private HttpLoggingInterceptor.Logger logInterceptor;
        private RequestParams requestParams;
        private boolean debug;
        /**
         * 有网的时候，请求超时的时间 单位秒，默认60s
         */
        private long hasNetCacheTime;
        /**
         * 没网的时候，请求超时的时间 单位秒，默认1天
         */
        private long noNetCacheTime;

        public Builder() {
            cacheMaxSize = 1024 * 1024 * 100;
            readTimeout = 20 * 1000;
            connectTimeout = 15 * 1000;
            writeTimeout = 20 * 1000;
            hasNetCacheTime = 60;
            noNetCacheTime = 60 * 60 * 24;
            level = HttpLoggingInterceptor.Level.BASIC;
            debug = true;
            isUseDiskCache = true;
            separator = File.separator;
            cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + separator + "OkHttpClient";
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder converterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        public Builder callAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = factory;
            return this;
        }

        /**
         * 设置 日志 拦截器
         */
        public Builder httpLoggingInterceptor(HttpLoggingInterceptor.Logger logInterceptor) {
            this.logInterceptor = logInterceptor;
            return this;
        }

        /**
         * 设置请求头
         */
        public Builder requestParams(RequestParams requestParams) {
            this.requestParams = requestParams;
            return this;
        }

        /**
         * 设置连接超时时间
         */
        public Builder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取超时时间
         */
        public Builder readTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * 设置写入超时时间
         */
        public Builder writeTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        /**
         * 有网的时候，请求超时的时间 单位秒，默认60s
         */
        public Builder hasNetCacheTime(long hasNetCacheTime) {
            this.hasNetCacheTime = hasNetCacheTime;
            return this;
        }

        /**
         * 没网的时候，请求超时的时间 单位秒，默认1天
         */
        public Builder noNetCacheTime(long noNetCacheTime) {
            this.noNetCacheTime = noNetCacheTime;
            return this;
        }

        /**
         * 连接失败自动重试
         */
        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            this.retryOnConnectionFailure = retryOnConnectionFailure;
            return this;
        }

        /**
         * 是否使用磁盘缓存， 默认使用
         */
        public Builder isUseDiskCache(boolean isUseDiskCache) {
            this.isUseDiskCache = isUseDiskCache;
            return this;
        }

        /**
         * 设置缓存路径，必须包含根路径 如 /storage/emulated/0/OkHttpHelper
         */
        public Builder cacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
            return this;
        }

        /**
         * 设置缓存大小
         */
        public Builder cacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }


        /**
         * 设置处于 debug
         */
        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * 设置log的等级
         */
        public Builder level(HttpLoggingInterceptor.Level level) {
            this.level = level;
            return this;
        }

        public Builder initClient() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (debug) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if ((!TextUtils.isEmpty(message)) && (!message.startsWith("<--"))) {
                            //LogUtil.e(message);
                        }
                        LogUtil.e(message);
                        if (logInterceptor != null) {
                            logInterceptor.log(message);
                        }
                    }
                });
                loggingInterceptor.setLevel(level);
                /*要在 OkHttpClient.Builder().build(); 之前，否则日志出不来*/
                builder.addInterceptor(loggingInterceptor);
            }
            //＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊＊
            if (requestParams != null) {
                builder.addInterceptor(new RequestInterceptor(requestParams));
            }
            builder.addInterceptor(new GzipRequestInterceptor());
            if (isUseDiskCache) {
                // builder.addInterceptor(new OkHttpHelper.CacheInterceptor());
                builder.addNetworkInterceptor(new CacheInterceptor(hasNetCacheTime, noNetCacheTime));
            }
            if (cacheDir != null) {
                if (cacheMaxSize < 0) {
                    cacheMaxSize = 1024 * 1024 * 128;
                }
                File file = new File(cacheDir);
                Cache cache = new Cache(file, cacheMaxSize);
                builder.cache(cache);
            }
            builder.cookieJar(new JavaNetCookiejar());
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
            this.okHttpClient = okHttpClient;
            return this;
        }

        public RetrofitClient build() {
            initClient();
            return new RetrofitClient(this);
        }
    }

}
