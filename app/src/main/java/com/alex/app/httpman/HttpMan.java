package com.alex.app.httpman;


import com.alex.app.model.UserBean;
import com.alex.app.model.qianguan.LoginBean;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Alex on 2016/6/19.
 */
public interface HttpMan {
    //public static final String doMainApi = "http://172.28.115.6:4477/";
    //public static final String doMainApi = "http://172.28.115.2:4477/";
    public static final String doMainApi = "http://172.27.23.4:4477/";
    //public static final String doMainApi = "http://192.168.5.244:4477/";
    //public static final String doMainApi = "http://192.168.191.3:4477/";

    //钱罐儿 登录
    @GET("homePhone/loginPhone/{phone}-{pwd}")
    Observable<LoginBean> loginQg(@Path("phone") String phone, @Path("pwd") String pwd);

    // //http://172.27.23.2:8080/test04/servlet?phone=13146008029&pwd=123456
    @POST("andApi")
    Observable<LoginBean> login(@Body UserBean bean);

    @FormUrlEncoded
    @POST("andApi")
    Observable<LoginBean> login(@Field("phone") String phone, @Field("pwd") String pwd);

    @Multipart
    @POST("upload")
    Observable<LoginBean> upLoad(@Part MultipartBody.Part photo, @Part("userName") RequestBody userName);

}
