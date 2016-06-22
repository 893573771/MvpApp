package com.alex.mvpapp.httpman;


import com.alex.mvpapp.model.GithubBean;
import com.alex.mvpapp.model.qianguan.LoginBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("appBid/loginPhone/{phone}&{pwd}")
    Observable<LoginBean> loginQgV2(@Path("phone") String phone, @Path("pwd") String pwd);

}
