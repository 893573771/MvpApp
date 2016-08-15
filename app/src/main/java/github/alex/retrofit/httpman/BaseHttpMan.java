package github.alex.retrofit.httpman;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作者：Alex
 * 时间：2016年08月14日    10:13
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public interface BaseHttpMan {

    @GET
    Observable<String> get(@Url String url);

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, String> paramsMap);

    @POST
    Observable<String> post(@Url String url, @QueryMap Map<String, String> paramsMap);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, String> paramsMap);

    @PUT
    Observable<String> put(@Url String url, @QueryMap Map<String, String> paramsMap);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadFile(@Url String url, @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadFiles(@Url String url, @Part("filename") String fileName, @PartMap Map<String, RequestBody> paramsMap);

    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@Url String url);

}
