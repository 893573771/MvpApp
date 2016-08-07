package com.alex.app.httpman;


import com.alex.app.model.MovieListBean;
import com.alex.app.model.UserBean;
import com.alex.app.model.qianguan.LoginBean;
import com.alex.app.model.zhihu.NewsListBean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface HttpMan {
    public static final String doMainApi = "http://172.27.23.3:8080/AlexApp/";
    //public static final String doMainApi = "http://192.168.5.55:8080/AlexApp/";
    public static final String qgbaseUrl = "http://api.qianguan360.com/service/";

    @GET("appBid/loginPhone/{phone}&{pwd}")
    Observable<LoginBean> loginQg(@Path("phone") String phone, @Path("pwd") String pwd);

    @GET("login")
    Observable<LoginBean> loginGet(@Query("phone") String phone, @Query("pwd") String pwd);

    @GET("login")
    Observable<LoginBean> loginGet(@QueryMap Map<String, String> params);

    @POST("login")
    Observable<LoginBean> loginPost(@Body Map<String, String> params);

    @POST("login")
    Observable<LoginBean> loginPost(@Body UserBean bean);

    @FormUrlEncoded
    @POST("login")
    Observable<LoginBean> loginPost3(@Field("phone") String phone, @Field("pwd") String pwd);

    @Multipart
    @POST("upload")
    Observable<LoginBean> upLoad(@Part MultipartBody.Part userLogo, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);

    @Multipart
    @POST("upload")
    Observable<LoginBean> upLoad2(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("login")
    Observable<JSONObject> postJson(@Field("userInfo") String content);


    @Multipart
    @POST("upload")
    Observable<String> upLoad3(@Part List<MultipartBody.Part> userLogoList, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);

    @GET("movie/top250")
    Observable<MovieListBean> loadMovieList(@Query("start") String start, @Query("count") String count);

    /**
     * 今日头条
     */
    @GET("/api/4/theme/9")
    Observable<NewsListBean> loadZhiHuLatestNews();

    /**
     * 互联网安全
     */
    @GET("/api/4/theme/10")
    Observable<NewsListBean> loadZhiHuSafety();

    /**
     * 不准无聊
     */
    @GET("/api/4/theme/11")
    Observable<NewsListBean> loadZhiHuInterest();

    /**
     * 体育日报
     */
    @GET("/api/4/theme/8")
    Observable<NewsListBean> loadZhiHuSport();

    //传入id查看详细信息
    @GET("/api/4/news/{id}")
    Observable<NewsListBean> loadZhiHuNewsDetails(@Path("id") int id);
}
