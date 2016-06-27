package github.alex.mvpview;


import github.alex.callback.OnHttpCallback;
import github.alex.model.StatusLayoutModel;

/**
 * mvx 模式开发的 接口
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public interface BaseHttpView extends BaseView {
    /**
     * 进行网络请求
     */
    public void onHttpRequest(OnHttpCallback onHttpCallback);

    /**
     * 初始化延时对话框
     */
    public void onInitLoadingDialog();

    /**
     * 展示延时对话框
     */
    public void showLoadingDialog();

    /**
     * 隐藏延时对话框
     */
    public void dismissLoadingDialog();

    /**
     * 得到多状态布局， 数据模型
     */
    public StatusLayoutModel onGetStatusLayoutModel();

    /**
     * 展示默认布局
     */
    public void showDefaultLayout();

    /**
     * 展示loading布局
     */
    public void showLoadingLayout();

    /**
     * 展示请求成功后的布局
     */
    public void showSuccessLayout();

    /**
     * 展示请求空数据的布局
     */
    public void showEmptyLayout();

    /**
     * 展示出错消息
     */
    public void onSetFailMessage(String message);

    /**
     * 展示加载失败的布局
     */
    public void showFailLayout();


}
