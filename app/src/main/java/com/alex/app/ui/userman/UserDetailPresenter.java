package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.httpman.HttpMan;
import com.alex.app.model.qianguan.LoginBean;
import com.alex.app.ui.App;

import java.io.File;

import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alex on 2016/6/21.
 */
public class UserDetailPresenter implements UserDetailContract.Presenter {
    private UserDetailContract.View view;

    public UserDetailPresenter(@NonNull UserDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void upLoadFile(File file, String userName) {

        OkHttpClient okHttpClient =  OkHttpUtil.getInstance()
                .setHeadParams(new HeadParams().addHeader("phoneNum","13146008029").addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())))
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpMan.doMainApi).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);
        RequestBody userNameBody = RequestBody.create(null, userName);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", file.getName(), photoRequestBody);

        httpMan.upLoad(photo, userNameBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }
    private final class MyHttpSubscriber extends HttpSubscriber<LoginBean> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }
        @Override
        public void onFailure(int code, String message) {
            view.setFailMessage(message);
            view.dismissLoadingDialog();
        }
        @Override
        public void onSuccess(LoginBean result) {
            view.dismissLoadingDialog();

        }
    }

}
