package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.config.AppCon;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.UserBean;
import com.alex.app.model.qianguan.LoginBean;
import com.alex.app.App;
import com.socks.library.KLog;

import java.util.HashMap;

import github.alex.mvp.CancelablePresenter;
import github.alex.okhttp.OkHttpUtil;
import github.alex.okhttp.RequestParams;
import github.alex.retrofit.StringConverterFactory;
import github.alex.rxjava.StringSubscriber;
import github.alex.util.DeviceUtil;
import github.alex.util.GsonUtil;
import github.alex.util.StringUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alex on 2016/6/21.
 */
public class LoginPresenter extends CancelablePresenter<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void localValidateLoginInfo(String phone, String pwd) {
        if (!StringUtil.isPhoneNum(phone)) {
            view.onLocalValidateError(AppCon.userManPhone);
            return;
        }
        if (!StringUtil.isLengthOK(pwd, AppCon.loginPwdMinLength, AppCon.loginPwdMaxLength)) {
            view.onLocalValidateError(AppCon.userLoginPwd);
            return;
        }
        if(view.isNetworkAvailable()){
            login(phone, pwd);
        }else{
            view.toast(AppCon.netNo);
        }
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        OkHttpClient okHttpClient = OkHttpUtil.getInstance()
                .requestParams(new RequestParams()
                        .addHeader("phoneNum", phone)
                        .addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())))
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpMan.doMainApi).client(okHttpClient)
                .addConverterFactory(StringConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);
        UserBean bean = new UserBean();
        bean.phone = phone;
        bean.pwd = pwd;
        HashMap map = new HashMap();
        map.put("phone", phone);
        map.put("pwd", pwd);

        Subscription subscription = httpMan.loginPost3(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyHttpSubscriber());
        addSubscription(subscription);
    }

    private final class MyHttpSubscriber extends StringSubscriber {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        /**
         * 网络请求失败
         *
         * @param message 请求失败的消息
         */
        @Override
        public void onError(String message) {
            view.toast(message);
            view.dismissLoadingDialog();
        }

        /**
         * 网络请求成功
         *
         * @param result 网络请求的结果
         */
        @Override
        public void onSuccess(String result) {
            LoginBean bean = GsonUtil.gson().fromJson(result, LoginBean.class);
            KLog.e(bean);

            view.toast("登录成功");
            view.dismissLoadingDialog();
        }

    }
}
