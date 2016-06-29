package github.alex.mvp;

import github.alex.callback.OnHttpCallback;
import github.alex.model.StatusLayoutModel;

/**
 * Created by hasee on 2016/6/28.
 */
public interface BaseHttpContract {

    interface View extends BaseContract.View {
        /**
         * 进行网络请求
         */
        void onHttpRequest(OnHttpCallback onHttpCallback);

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
        void onSetFailMessage(String message);

        /**
         * 展示加载失败的布局
         */
        void showFailLayout();

    }
    interface Presenter extends BaseContract.Presenter{

    }
}
