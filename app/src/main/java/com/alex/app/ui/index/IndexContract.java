package com.alex.app.ui.index;

import com.alex.app.model.MovieListBean;

import github.alex.mvp.BaseHttpContract;

/**
 * Created by Alex on 2016/6/23.
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
