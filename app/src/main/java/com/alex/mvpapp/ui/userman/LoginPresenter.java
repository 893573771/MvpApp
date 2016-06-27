package com.alex.mvpapp.ui.userman;

import android.support.annotation.NonNull;

import com.alex.mvpapp.App;
import com.alex.mvpapp.config.AppConst;
import com.alex.mvpapp.config.Url;
import com.alex.mvpapp.httpman.HttpMan;
import com.alex.mvpapp.model.qianguan.LoginBean;
import com.orhanobut.logger.Logger;

import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import github.alex.util.StringUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
            view.onLocalPhoneError();
            return;
        }
        if(!StringUtil.isLengthOK(pwd, AppConst.loginPwdMinLength, AppConst.loginPwdMaxLength)){
            view.onLocalPwdError();
            return;
        }
        login(phone, pwd);
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        OkHttpClient okHttpClient =  OkHttpUtil.getInstance().getOkHttpClient(new LogInterceptor(), new HeadParams().addHeader("phoneNum",phone).addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.doMainApi).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);
        httpMan.login(phone,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }
    private final class MyHttpSubscriber extends HttpSubscriber<LoginBean> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }
        @Override
        public void onFailure(int code, String message) {
            Logger.e(code+" "+message);
            view.dismissLoadingDialog();
        }
        @Override
        public void onSuccess(LoginBean result) {
            view.dismissLoadingDialog();
            Logger.e("登录成功");
        }
    }

    private final class LogInterceptor implements HttpLoggingInterceptor.Logger{
        @Override
        public void log(String message) {
            Logger.e(message);
        }
    }
}
