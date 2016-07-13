package com.alex.app.ui.index;

import com.alex.app.config.AppCon;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.MovieListBean;

import github.alex.mvp.CancelablePresenter;
import github.alex.okhttp.OkHttpUtil;
import github.alex.rxjava.BaseSubscriber;
import github.alex.util.GsonUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by alex on 2016/6/21.
 */
public class IndexPresenter extends CancelablePresenter<IndexContract.View> implements IndexContract.Presenter {
    private String loadType;
    private int start;
    private int count;
    public IndexPresenter(IndexContract.View view) {
        super(view);
        start = 0;
        count = 10;
    }

    /**
     * 加载 movie 列表
     */
    @Override
    public void loadMovieList(String loadType) {
        this.loadType = loadType;
        if((AppCon.loadFirst.equals(loadType) || (AppCon.loadRefresh.equals(loadType)))){
            start = 0;
            count = 10;
        }else{
            start += count;
            count = 10;
        }
        OkHttpClient okHttpClient = OkHttpUtil.getInstance()
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(HttpMan.doMainApi).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.gson()))//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        HttpMan httpMan = retrofit.create(HttpMan.class);

        Subscription subscription = httpMan.loadMovieList(start+"", count+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyHttpSubscriber());
        addSubscription(subscription);
    }

    private final class MyHttpSubscriber extends BaseSubscriber<MovieListBean> {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            if(AppCon.loadFirst.equals(loadType)){
                view.showLoadingDialog();
            }
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

        /**
         * 网络请求成功
         *
         * @param result 网络请求的结果
         */
        @Override
        public void onSuccess(MovieListBean result) {
            view.onShowMovieList(loadType, result);
            view.dismissLoadingDialog();
        }
    }
}
