package github.alex.retrofit;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class RequestParams {
    /**
     * 文本请求头
     */
    private Map<String, String> stringHeadMap;
    /**
     * 文本请求行
     */
    private Map<String, String> stringQueryMap;
    /**
     * /**
     * 文本请求体
     */
    private Map<String, String> stringBodyMap;

    public RequestParams() {
        this.stringBodyMap = new LinkedHashMap<String, String>();
        this.stringQueryMap = new LinkedHashMap<String, String>();
        this.stringHeadMap = new LinkedHashMap<String, String>();
    }

    /**
     * 添加文本请求体
     */
    public RequestParams addHeader(@NonNull String key, @NonNull String value) {
        stringHeadMap.put(key, value);
        return this;
    }

    /**
     * 添加请求行
     */
    public RequestParams addQuery(String key, String value) {
        stringQueryMap.put(key, value);
        return this;
    }

    /**
     * 添加文本请求体
     */
    public RequestParams addBody(@NonNull String key, @NonNull String value) {
        stringBodyMap.put(key, value);
        return this;
    }

    /**
     * 获取请求头参数
     */
    public Map<String, String> getHeadMap() {
        return stringHeadMap;
    }

    /**
     * 获取请求行参数
     */
    public Map<String, String> getQueryMap() {
        return stringQueryMap;
    }

    /**
     * 获取请求头体参数
     */
    public Map<String, String> getBodyMap() {
        return stringBodyMap;
    }
}
