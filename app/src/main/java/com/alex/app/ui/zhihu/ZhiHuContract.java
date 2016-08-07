package com.alex.app.ui.zhihu;

import com.alex.app.model.zhihu.NewsListBean;

import github.alex.mvp.BaseHttpContract;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface ZhiHuContract {

    interface View extends BaseHttpContract.View {

        /**
         * 展示 movie 列表
         */
        void onShowNewsList(String loadType, NewsListBean bean);

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 本地验证登录信息
         */
        void loadNewsList(String loadType, int index);

    }
}
