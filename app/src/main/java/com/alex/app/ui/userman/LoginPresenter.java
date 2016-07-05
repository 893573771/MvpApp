package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.config.AppConst;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.UserBean;
import com.alex.app.ui.App;
import com.socks.library.KLog;

import java.util.HashMap;

import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.retrofit.StringConverterFactory;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import github.alex.util.StringUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alex on 2016/6/21.
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    public LoginPresenter(@NonNull LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void localValidateLoginInfo(String phone, String pwd) {
        if (!StringUtil.isPhoneNum(phone)) {
            view.onLocalValidateError(AppConst.userManPhone);
            return;
        }
        if (!StringUtil.isLengthOK(pwd, AppConst.loginPwdMinLength, AppConst.loginPwdMaxLength)) {
            view.onLocalValidateError(AppConst.userLoginPwd);
            return;
        }
        login(phone, pwd);
    }

    @Override
    public void login(@NonNull String phone, @NonNull String pwd) {
        OkHttpClient okHttpClient = OkHttpUtil.getInstance().headParams(new HeadParams().addHeader("phoneNum", phone).addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp()))).build();
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

        httpMan.loginPost3(phone,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }

    private final class MyHttpSubscriber extends HttpSubscriber<String> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        @Override
        public void onFailure(int code, String message) {
            view.showToast(message);
            view.dismissLoadingDialog();
        }

        @Override
        public void onSuccess(String result) {
            KLog.e(result);
            view.showToast("登录成功");
            view.dismissLoadingDialog();
        }
    }
}
