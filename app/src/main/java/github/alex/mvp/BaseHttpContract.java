package github.alex.mvp;

import android.support.annotation.NonNull;

import github.alex.model.StatusLayoutModel;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface BaseHttpContract {

    interface View extends BaseContract.View {

        /**
         * 初始化延时对话框
         */
        void onInitLoadingDialog();

        /**
         * 展示延时对话框
         */
        void showLoadingDialog();

        /**
         * 隐藏延时对话框
         */
        void dismissLoadingDialog();

        /**
         * 得到多状态布局， 数据模型
         */
        StatusLayoutModel onGetStatusLayoutModel();

        /**
         * 展示默认布局
         */
        void showDefaultLayout();

        /**
         * 展示loading布局
         */
        void showLoadingLayout();

        /**
         * 展示请求成功后的布局
         */
        void showSuccessLayout();

        /**
         * 展示请求空数据的布局
         */
        void showEmptyLayout();

        /**
         * 展示出错消息
         */
        void setFailMessage(@NonNull String message);

        /**
         * 展示加载失败的布局
         */
        void showFailLayout();

        /**
         * 下拉刷新 或 加载 完成
         */
        void onRefreshFinish();
    }

    interface Presenter extends BaseContract.Presenter {

    }
}
