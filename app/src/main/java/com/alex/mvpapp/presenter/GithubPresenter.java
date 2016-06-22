package com.alex.mvpapp.presenter;

import com.alex.mvpapp.BuildConfig;
import com.alex.mvpapp.httpman.HttpMan;
import com.alex.mvpapp.model.GithubBean;
import com.alex.mvpapp.view.IGithubView;


import java.util.List;

import github.alex.rxjava.HttpSubscriber;
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
public class GithubPresenter {
    private IGithubView iGithubView;

    public GithubPresenter(IGithubView iGithubView) {
        this.iGithubView = iGithubView;
    }

    public void getGithubBeanList() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient okHttpClient = builder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").client(okHttpClient).addConverterFactory(GsonConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan gitHub = retrofit.create(HttpMan.class);
        gitHub.rxGithubBeanList("square", "retrofit").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new MyHttpSubscriber());

    }

    private final class MyHttpSubscriber extends HttpSubscriber<List<GithubBean>> {

        @Override
        public void onStart() {
            iGithubView.showLoadingLayout();
        }

        @Override
        public void onFailure(int code, String message) {
             iGithubView.showFailLayout();
            iGithubView.onSetFailMessage(message);
        }

        @Override
        public void onSuccess(List<GithubBean> result) {
            iGithubView.showSuccessLayout();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; (result != null) && (i < result.size()); i++) {
                GithubBean githubBean = result.get(i);
                builder.append(githubBean.login);
            }
            iGithubView.setContextText(builder.toString());

        }

    }

}
