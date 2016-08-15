package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.config.AppCon;
import com.alex.app.model.qianguan.LoginBean;
import com.alibaba.fastjson.JSON;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.StringSubscriber;
import github.alex.util.LogUtil;
import github.alex.util.StringUtil;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class LoginPresenter extends CancelablePresenter<LoginContract.View> implements LoginContract.Presenter {
    private static final String qgBaseUrl = "http://api.qianguan360.com/service/";

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
                .baseUrl(qgBaseUrl)
                .build()
                .get("appBid/loginPhone/" + phone + "&" + pwd)
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyStringSubscriber());
    }

    private final class MyStringSubscriber extends StringSubscriber {
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

        @Override
        public void onSuccess(String result) {
            LoginBean loginBean = JSON.parseObject(result, LoginBean.class);
            LogUtil.e(loginBean.code + " " + loginBean.message);
            view.toast("登录成功");
            view.dismissLoadingDialog();
        }
    }

}
