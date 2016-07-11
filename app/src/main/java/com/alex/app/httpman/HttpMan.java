package com.alex.app.httpman;


import com.alex.app.model.UserBean;

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
 * Created by Alex on 2016/6/19.
 */
public interface HttpMan {
    public static final String doMainApi = "http://172.27.23.3:8080/AlexApp/";
    //public static final String doMainApi = "http://192.168.4.39:8080/AlexApp/";
    @GET("login/{phone}-{pwd}")
    Observable<String> loginQg(@Path("phone") String phone, @Path("pwd") String pwd);

    @GET("login")
    Observable<String> loginGet1(@Query("phone") String phone, @Query("pwd") String pwd);

    @GET("login")
    Observable<String> loginGet2(@QueryMap Map<String, String> params);

    @POST("login")
    Observable<String> loginPost1(@Body Map<String, String> params);

    @POST("login")
    Observable<String> loginPost2(@Body UserBean bean);

    @FormUrlEncoded
    @POST("login")
    Observable<String> loginPost3(@Field("phone") String phone, @Field("pwd") String pwd);

    @Multipart
    @POST("upload")
    Observable<String> upLoad(@Part MultipartBody.Part userLogo, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);

    @Multipart
    @POST("upload")
    Observable<String> upLoad2(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("login")
    Observable<JSONObject> postJson(@Field("userInfo") String content);


    @Multipart
    @POST("upload")
    Observable<String> upLoad3(@Part List<MultipartBody.Part> userLogoList, @Part("phone") RequestBody phoneBody, @Part("pwd") RequestBody pwdBody);



}
