package com.alex.app.httpman;


import com.alex.app.model.GithubBean;
import com.alex.app.model.qianguan.LoginBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Alex on 2016/6/19.
 */
public interface HttpMan {

    //获取github前30作者
    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<GithubBean>> rxGithubBeanList(@Path("owner") String owner, @Path("repo") String repo);

    //钱罐儿 登录
    @GET("homePhone/loginPhone/{phone}-{pwd}")
    Observable<LoginBean> loginQg(@Path("phone") String phone, @Path("pwd") String pwd);

    // //http://172.27.23.2:8080/test04/servlet?phone=13146008029&pwd=123456
    @GET("andApi")
    Observable<LoginBean> login(@QueryMap Map<String, String> params);

}
