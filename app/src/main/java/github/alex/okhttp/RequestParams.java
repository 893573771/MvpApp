package github.alex.okhttp;

import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by alex on 2016/6/22.
 */
public class RequestParams {
    /**
     * 文本请求头
     */
    private Map<String, String> stringHeadMap;

    /**
     * 文本请求体
     */
    private Map<String, String> stringBodyMap;

    public RequestParams() {
        this.stringBodyMap = new LinkedHashMap<String, String>();
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
     * 添加文本请求体
     */
    public RequestParams addBody(@NonNull String key, @NonNull String value) {
        stringBodyMap.put(key, value);
        return this;
    }
    /**
     * 获取请求头参数
     */
    public Map<String, String> getHeadMap(){
        return stringHeadMap;
    }
    /**
     * 获取请求头体参数
     */
    public Map<String, String> getBodyMap(){
        return stringBodyMap;
    }
}
