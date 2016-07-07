package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import github.alex.mvp.BaseHttpContract;

/**
 * Created by Alex on 2016/6/23.
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
