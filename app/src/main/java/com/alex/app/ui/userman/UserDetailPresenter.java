package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.httpman.HttpMan;
import com.alex.app.ui.App;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.alex.mvp.CancelablePresenter;
import github.alex.okhttp.OkHttpUtil;
import github.alex.okhttp.RequestParams;
import github.alex.retrofit.StringConverterFactory;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import github.alex.util.GsonUtil;
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
public class UserDetailPresenter extends CancelablePresenter implements UserDetailContract.Presenter {
    private UserDetailContract.View view;

    public UserDetailPresenter(@NonNull UserDetailContract.View view) {
        this.view = view;
    }


    @Override
    public void upLoadFile(List<File> fileList, String phone, String pwd) {

        if ((fileList == null) || (fileList.size() <= 0)) {
            KLog.e("文件为空");
            return;
        }
        OkHttpClient okHttpClient = OkHttpUtil.getInstance()
                .requestParams(new RequestParams()
                        .addHeader("phoneNum", "13146008029")
                        .addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpMan.doMainApi)
                .client(okHttpClient).addConverterFactory(StringConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);

        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            paramsMap.put("userLogo\"; filename=\"" + file.getName() + ".png", fileBody);
        }
        RequestBody phoneBody = RequestBody.create(null, phone);
        RequestBody pwdBody = RequestBody.create(null, pwd);

        paramsMap.put("phone", phoneBody);
        paramsMap.put("pwd", pwdBody);
        subscription = httpMan.upLoad2(paramsMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyHttpSubscriber());

    }


    private final class MyHttpSubscriber extends HttpSubscriber<String> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        @Override
        public void onError(String message) {
            KLog.e(message);
            view.setFailMessage(message);
            view.dismissLoadingDialog();
        }

        @Override
        public void onSuccess(String result) {
            Gson gson = GsonUtil.gson();
            view.dismissLoadingDialog();
            view.toast("上传成功");
        }
    }

    @Override
    public void cancel() {

    }
}
