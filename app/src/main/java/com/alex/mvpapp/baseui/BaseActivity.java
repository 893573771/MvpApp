package com.alex.mvpapp.baseui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alex.mvpapp.R;

import github.alex.callback.OnHttpCallback;
import github.alex.dialog.LoadingDialog;
import github.alex.helper.StatusLayoutHelper;
import github.alex.helper.ToastHelper;
import github.alex.model.StatusLayoutModel;
import github.alex.mvpview.IBaseHttpView;

/**
 * mvx 模式开发的 基类
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseHttpView {
    protected Context context;

    private LoadingDialog loadingDialog;
    private ToastHelper toastHelper;
    private StatusLayoutHelper statusLayoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        toastHelper = new ToastHelper();
        statusLayoutHelper = new StatusLayoutHelper(this);
        onGetIntentData();
        setContentView(getLayoutResID());
        onGetStatusLayoutModel();

        getBodyViewId();
        statusLayoutHelper.initMultiModeBodyLayout(this, getBodyViewId());
        onCreateData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        onGetIntentData();
    }

    /**
     * 配置 布局文件的 资源 id
     */
    public abstract int getLayoutResID();

    /**
     * 获取启动者通过Intent传递过来的 数据
     */
    protected void onGetIntentData() {

    }

    /**
     * 展示吐司
     */
    @Override
    public void onShowToast(String text) {
        if ((toastHelper != null) && (context != null)) {
            toastHelper.showToast(context, text);
        }
    }

    /**
     * 网络请求
     */
    @Override
    public void onHttpRequest(OnHttpCallback onHttpCallback) {

    }

    @Override
    public void onInitLoadingDialog() {

    }

    /**
     * 展示延时对话框
     */
    @Override
    public void showLoadingDialog() {

    }

    /**
     * 隐藏延时对话框
     */
    @Override
    public void dismissLoadingDialog() {

    }

    @Override
    public void onCreateData() {

    }

    @Override
    public int getBodyViewId() {
        return StatusLayoutModel.layoutResIdNo;
    }

    public StatusLayoutModel onGetStatusLayoutModel() {
        StatusLayoutModel statusLayoutModel = new StatusLayoutModel()
        .setDefaultLayoutId(R.layout.alex_layout_default).setDefaultImageViewId(R.id.iv_logo).setDefaultTextViewId(R.id.tv_content)
        .setLoadingLayoutId(R.layout.alex_layout_loading_circle_orange).setLoadingViewId(StatusLayoutModel.resIdNo).setLoadingTextViewId(StatusLayoutModel.resIdNo)
        .setEmptyLayoutId(R.layout.alex_layout_empty).setEmptyImageViewId(R.id.iv_logo).setEmptyTextViewId(R.id.tv_content)
        .setFailLayoutId(R.layout.alex_layout_fail).setFailImageViewId(R.id.iv_logo).setFailTextViewId(R.id.tv_content);
        return statusLayoutModel;
    }

    @Override
    public void showDefaultLayout() {
        statusLayoutHelper.showDefaultLayout();
    }

    @Override
    public void showLoadingLayout() {
        statusLayoutHelper.showLoadingLayout();
    }

    @Override
    public void showSuccessLayout() {
        statusLayoutHelper.showSuccessLayout();
    }

    @Override
    public void showEmptyLayout() {
        statusLayoutHelper.showEmptyLayout();
    }

    @Override
    public void onSetFailMessage(String message) {
        statusLayoutHelper.setFailMessage(message);
    }

    @Override
    public void showFailLayout() {
        statusLayoutHelper.showFailLayout();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (toastHelper != null) {
            toastHelper.dimissToast();
        }
    }

    /**
     * 扩展的findViewById
     */
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2Px(float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * 多状态布局的 点击事件
     */
    @Override
    public void onStatusLayoutClick(int status) {

    }
}
