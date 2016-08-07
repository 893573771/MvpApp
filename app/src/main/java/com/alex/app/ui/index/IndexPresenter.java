package com.alex.app.ui.index;

import com.alex.app.config.AppCon;
import com.alex.app.httpman.HttpMan;
import com.alex.app.model.MovieListBean;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.BaseSubscriber;
import github.alex.rxjava.RxUtil;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class IndexPresenter extends CancelablePresenter<IndexContract.View> implements IndexContract.Presenter {
    public static final String baseUrl = "https://api.douban.com/v2/";
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
        if ((AppCon.loadFirst.equals(loadType) || (AppCon.loadRefresh.equals(loadType)))) {
            start = 0;
            count = 10;
        } else {
            start += count;
            count = 10;
        }
        new RetrofitClient.Builder()
                .baseUrl(baseUrl)
                .build()
                .create(HttpMan.class).loadMovieList(start + "", count + "")
                .compose(RxUtil.<MovieListBean>rxHttp())
                .lift(new AddSubscriberOperator())
                .subscribe(new MyBaseSubscriber());

    }

    private final class MyBaseSubscriber extends BaseSubscriber<MovieListBean> {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            if (AppCon.loadFirst.equals(loadType)) {
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
