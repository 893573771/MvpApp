package com.alex.app.ui.douban;

import com.alex.app.config.AppCon;
import com.alex.app.httpman.UrlMan;
import com.alex.app.model.MovieListBean;
import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;
import java.util.Map;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.StringSubscriber;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class DouBanPresenter extends CancelablePresenter<DouBanContract.View> implements DouBanContract.Presenter {
    private String loadType;
    private int start;
    private int count;

    public DouBanPresenter(DouBanContract.View view) {
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
        Map<String, String> paramsMap = new LinkedHashMap<>();
        paramsMap.put("start", start + "");
        paramsMap.put("count", count + "");
        new RetrofitClient.Builder()
                .baseUrl(UrlMan.DouBan.baseUrl)
                .build()
                .get(UrlMan.DouBan.movieList, paramsMap)
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyBaseSubscriber());
    }

    private final class MyBaseSubscriber extends StringSubscriber {
        /**
         * 开始网络请求
         */
        @Override
        public void onStart() {
            view.showLoadingDialog();
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
        public void onSuccess(String result) {
            MovieListBean movieListBean = JSON.parseObject(result, MovieListBean.class);
            view.onShowMovieList(loadType, movieListBean);
            view.dismissLoadingDialog();
        }
    }
}
