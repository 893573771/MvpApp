package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.httpman.HttpMan;
import com.alex.app.ui.App;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.retrofit.StringConverterFactory;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
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
    public void upLoadFile(File file, String phone, String pwd) {

        OkHttpClient okHttpClient = OkHttpUtil.getInstance().headParams(new HeadParams().addHeader("phoneNum", "13146008029").addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp()))).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpMan.doMainApi).client(okHttpClient).addConverterFactory(StringConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);

        RequestBody photoBody = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody phoneBody = RequestBody.create(null, phone);
        RequestBody pwdBody = RequestBody.create(null, pwd);
        Map<String, RequestBody> fileBodyMap = new HashMap();
        fileBodyMap.put("photo", photoBody);
        fileBodyMap.put("photos\"; fileName=\"icon.png", photoBody);
        fileBodyMap.put("username",  RequestBody.create(null, "abc"));
        httpMan.upLoad2(fileBodyMap, phoneBody, pwdBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }

    private final class MyHttpSubscriber extends HttpSubscriber<String> {
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
        public void onSuccess(String result) {
            KLog.e(result);

            view.dismissLoadingDialog();
            view.showToast("上传成功");
        }
    }

}
