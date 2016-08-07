package github.alex.retrofit.interceptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import github.alex.retrofit.RequestParams;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：alex
 * 时间：2016/7/27 17:18
 * 博客地址：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

@SuppressWarnings("rawtypes")
public final class RequestInterceptor implements Interceptor {
    private RequestParams requestParams;

    public RequestInterceptor(RequestParams requestParams) {
        this.requestParams = requestParams;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        Map<String, String> stringQueryMap = requestParams.getQueryMap();
        StringBuilder queryStringBuilder = new StringBuilder();

        queryStringBuilder.append(request.url().toString().contains("?") ? "" : "?");

            /*解析文本请求行*/
        if ((stringQueryMap != null) && (stringQueryMap.size() > 0)) {
            Iterator<?> iterator = stringQueryMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                queryStringBuilder.append("&" + entry.getKey() + "=" + entry.getValue() + "");
            }
        }

        Request.Builder requestBuilder = builder.url(request.url().toString() + queryStringBuilder)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*");
        Map<String, String> stringHeadMap = requestParams.getHeadMap();
             /*解析文本请求头*/
        if ((stringHeadMap != null) && (stringHeadMap.size() > 0)) {
            Iterator<?> iterator = stringHeadMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                requestBuilder.addHeader(entry.getKey() + "", entry.getValue() + "");
            }
        }
        RequestBody requestBody = getStringRequestBody(requestParams.getBodyMap());
        if (requestBody != null) {
            requestBuilder.method("POST", requestBody);
        }

        return chain.proceed(requestBuilder.build());
    }

    private RequestBody getStringRequestBody(Map<?, ?> stringBodyMap) {
        FormBody.Builder stringBodyBuilder = new FormBody.Builder();
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
        if ((stringBodyMap == null) || (stringBodyMap.size() <= 0)) {
            return null;
        }
            /*解析文本请求体*/
        if ((stringBodyMap != null) && (stringBodyMap.size() > 0)) {
            Iterator iterator = stringBodyMap.entrySet().iterator();
            while (iterator.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
                stringBodyBuilder.add(entry.getKey() + "", entry.getValue() + "");
            }
        }
        return stringBodyBuilder.build();
    }
}


