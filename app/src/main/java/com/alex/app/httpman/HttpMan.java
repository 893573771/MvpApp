package com.alex.app.httpman;


import com.alex.app.model.UserBean;
import com.alex.app.model.qianguan.LoginBean;

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
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface HttpMan {

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
}
