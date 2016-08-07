package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.config.AppCon;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.qianguan.LoginBean;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.BaseSubscriber;
import github.alex.rxjava.RxUtil;
import github.alex.util.LogUtil;
import github.alex.util.StringUtil;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class LoginPresenter extends CancelablePresenter<LoginContract.View> implements LoginContract.Presenter {
    private static final String baseUrl = "http://api.qianguan360.com/service/";

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
        login(phone, pwd);
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        new RetrofitClient.Builder()
                .baseUrl(baseUrl)
                .build()
                .create(HttpMan.class)
                .loginQg(phone, pwd)
                .compose(RxUtil.<LoginBean>rxHttp())
                .lift(new AddSubscriberOperator())
                .subscribe(new MyBaseSubscriber());
    }

    public final class MyBaseSubscriber extends BaseSubscriber<LoginBean> {
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
        public void onSuccess(LoginBean result) {
            LogUtil.e(result.message);
            view.toast("登录成功");
            view.dismissLoadingDialog();
        }

    }
}
