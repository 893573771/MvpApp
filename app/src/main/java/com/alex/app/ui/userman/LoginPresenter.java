package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.ui.App;
import com.alex.app.config.AppConst;
import com.alex.app.config.Url;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.qianguan.LoginBean;
import com.socks.library.KLog;


import java.util.LinkedHashMap;
import java.util.Map;

import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import github.alex.util.StringUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2016/6/21.
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
    }
    @Override
    public void localValidateLoginInfo(String phone, String pwd) {
        if(!StringUtil.isPhoneNum(phone)){
            view.onLocalValidateError(AppConst.userManPhone);
            return;
        }
        if(!StringUtil.isLengthOK(pwd, AppConst.loginPwdMinLength, AppConst.loginPwdMaxLength)){
            view.onLocalValidateError(AppConst.userLoginPwd);
            return;
        }
        login(phone, pwd);
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        OkHttpClient okHttpClient =  OkHttpUtil.getInstance().getOkHttpClient(new HeadParams().addHeader("phoneNum",phone).addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.doMainApi).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);
        Map<String,String> params = new LinkedHashMap<>();
        params.put("phone",phone);
        params.put("pwd",pwd);
        httpMan.login(params).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }

    @Override
    public void startIndexActivity() {
        view.onStartIndexActivity();
    }


    private final class MyHttpSubscriber extends HttpSubscriber<LoginBean> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }
        @Override
        public void onFailure(int code, String message) {
            KLog.e(code+" "+message);
            view.dismissLoadingDialog();
        }
        @Override
        public void onSuccess(LoginBean result) {
            view.dismissLoadingDialog();
            startIndexActivity();
        }
    }

}
