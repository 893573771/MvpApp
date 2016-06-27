package com.alex.mvpapp.ui.userman;

import android.support.annotation.NonNull;

import github.alex.mvpview.BaseHttpView;
import github.alex.presenter.BaseHttpPresenter;

/**
 * Created by Alex on 2016/6/23.
 */
public interface LoginContract {

    interface View extends BaseHttpView {
        /**
         * 登录时，本地验证手机号码不对
         */
        void onLocalPhoneError();

        /**
         * 登录时，本地验证密码不对
         */
        void onLocalPwdError();
    }

    interface Presenter extends BaseHttpPresenter{
        /**本地验证登录信息*/
        void localValidateLoginInfo(@NonNull String phone, @NonNull String pwd);
        /**请求登录接口*/
        void login(@NonNull String phone, @NonNull String pwd);
    }
}
