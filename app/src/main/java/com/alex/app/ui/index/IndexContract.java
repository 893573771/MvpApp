package com.alex.app.ui.index;

import com.alex.app.model.MovieListBean;

import github.alex.mvp.BaseHttpContract;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface IndexContract {

    interface View extends BaseHttpContract.View {
        /**
         * 展示 movie 列表
         */
        void onShowMovieList(String loadType, MovieListBean bean);
    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 加载 movie 列表
         */
        void loadMovieList(String loadType);

    }
}
