package com.alex.mvpapp.presenter;

import com.alex.mvpapp.App;
import com.alex.mvpapp.config.Url;
import com.alex.mvpapp.httpman.HttpMan;
import com.alex.mvpapp.model.qianguan.LoginBean;
import com.alex.mvpapp.view.IGithubView;
import com.orhanobut.logger.Logger;

import github.alex.mvpview.IBaseView;
import github.alex.okhttp.HeadParams;
import github.alex.okhttp.OkHttpUtil;
import github.alex.presenter.BaseHttpPresenter;
import github.alex.rxjava.HttpSubscriber;
import github.alex.util.DeviceUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hasee on 2016/6/21.
 */
public class QgLoginPresenter extends BaseHttpPresenter<IBaseView> {
    private IGithubView iGithubView;

    public QgLoginPresenter(IGithubView iGithubView) {
        this.iGithubView = iGithubView;
    }

    public void loginQg(String phone, String pwd) {
        OkHttpClient okHttpClient =  OkHttpUtil.getInstance().getOkHttpClient(new LogInterceptor());
        //
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.qianguan360.com/service/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan gitHub = retrofit.create(HttpMan.class);
        gitHub.loginQg(phone,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());

    }

    public void loginQgV2(String phone, String pwd) {
        OkHttpClient okHttpClient =  OkHttpUtil.getInstance().getOkHttpClient(new LogInterceptor(), new HeadParams().addHeader("phoneNum",phone).addHeader("uuid", DeviceUtil.getSafeDeviceSoleId(App.getApp())));
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.doMainApi).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan gitHub = retrofit.create(HttpMan.class);
        gitHub.loginQgV2(phone,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());
    }
    private final class MyHttpSubscriber extends HttpSubscriber<LoginBean> {
        @Override
        public void onStart() {
            iGithubView.showLoadingLayout();
        }
        @Override
        public void onFailure(int code, String message) {
            Logger.e(code+" "+message);
            iGithubView.showFailLayout();
            iGithubView.onSetFailMessage(message);
        }
        @Override
        public void onSuccess(LoginBean result) {
            iGithubView.showSuccessLayout();
            iGithubView.setContextText(result.data.mobileNo);
        }
    }

    private final class LogInterceptor implements HttpLoggingInterceptor.Logger{
        @Override
        public void log(String message) {
            Logger.e(message);
        }
    }
}
