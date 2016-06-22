package github.alex.okhttp;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by hasee on 2016/6/22.
 */
public class HeadParams {
    /**
     * 文本请求头
     */
    private Map<String, String> stringHeadMap;

    public HeadParams() {
        this.stringHeadMap = new LinkedHashMap<String, String>();
    }

    /**
     * 添加文本请求体
     */
    public HeadParams addHeader(String key, String value) {
        stringHeadMap.put(key, value);
        return this;
    }
    /**
     * 获取请求头参数
     */
    public Map<String, String> getHeadMap(){
        return stringHeadMap;
    }
}
