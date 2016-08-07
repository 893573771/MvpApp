package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import github.alex.mvp.BaseHttpContract;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface LoginContract {

    interface View extends BaseHttpContract.View {
        /**
         * 登录时，本地验证手机号码不对
         */
        void onLocalValidateError(String message);

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 本地验证登录信息
         */
        void localValidateLoginInfo(@NonNull String phone, @NonNull String pwd);

        /**
         * 请求登录接口
         */
        void login(@NonNull String phone, @NonNull String pwd);

    }
}
