package com.alex.app.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alex.app.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import github.alex.annotation.LayoutStatus;
import github.alex.callback.OnHttpCallback;
import github.alex.dialog.LoadingDialog;
import github.alex.dialog.basedialog.BaseDialog;
import github.alex.dialog.callback.DialogOnKeyListener;
import github.alex.helper.ToastHelper;
import github.alex.helper.ViewHelper;
import github.alex.model.StatusLayoutModel;
import github.alex.mvp.BaseHttpContract;

/**
 * mvx 模式开发的 基类
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseHttpContract.View, View.OnClickListener {
    protected Context context;
    private LoadingDialog loadingDialog;
    private ToastHelper toastHelper;
    private ViewHelper viewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        toastHelper = new ToastHelper();
        viewHelper = new ViewHelper(this);
        onGetIntentData();
        if ((LayoutStatus.resIdNo != getLayoutResID()) && (0 != getLayoutResID())) {
            setContentView(getLayoutResID());
        }
        viewHelper.setOnLeftTitleViewClickListener(getLeftTitleViewId());
        viewHelper.setOnRightTitleViewClickListener(getRightTitleViewId());
        onGetStatusLayoutModel();
        getBodyViewId();
        viewHelper.initMultiModeBodyLayout(this, getBodyViewId());
        initStatusBar();
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
     * 获取 主布局视图 的 id
     */
    @Override
    public int getBodyViewId() {
        return LayoutStatus.layoutResIdNo;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     */
    protected void onGetIntentData() {

    }

    /**
     * 展示吐司
     */
    @Override
    public void showToast(String text) {
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
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(true);
        loadingDialog.setOnKeyListener(new DialogOnKeyListener(this, DialogOnKeyListener.DialogOnKeyType.dismissKillActivity));
    }

    /**
     * 展示延时对话框
     */
    @Override
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(BaseDialog.AnimType.centerNormal);
    }

    /**
     * 隐藏延时对话框
     */
    @Override
    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
    /**
     * 执行在 onCreateView 中
     * 通过 findViewById 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {

    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 页面跳转
     */
    public void startActivity(@NonNull Class clazz) {
        if (clazz == null) {
            Log.e(BaseActivity.class.getSimpleName(), "当前Class为空");
            return;
        }
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public StatusLayoutModel onGetStatusLayoutModel() {
        StatusLayoutModel statusLayoutModel = new StatusLayoutModel().setDefaultLayoutId(R.layout.alex_layout_default).setDefaultImageViewId(R.id.iv_logo).setDefaultTextViewId(R.id.tv_content).setLoadingLayoutId(R.layout.alex_layout_loading_circle_orange).setLoadingViewId(LayoutStatus.resIdNo).setLoadingTextViewId(LayoutStatus.resIdNo).setEmptyLayoutId(R.layout.alex_layout_empty).setEmptyImageViewId(R.id.iv_logo).setEmptyTextViewId(R.id.tv_content).setFailLayoutId(R.layout.alex_layout_fail).setFailImageViewId(R.id.iv_logo).setFailTextViewId(R.id.tv_content);
        return statusLayoutModel;
    }

    @Override
    public void showDefaultLayout() {
        viewHelper.showDefaultLayout();
    }

    @Override
    public void showLoadingLayout() {
        viewHelper.showLoadingLayout();
    }

    @Override
    public void showSuccessLayout() {
        viewHelper.showSuccessLayout();
    }

    @Override
    public void showEmptyLayout() {
        viewHelper.showEmptyLayout();
    }

    @Override
    public void onSetFailMessage(String message) {
        viewHelper.setFailMessage(message);
    }

    /**
     * 给文本控件设置文本
     */
    @Override
    public void setText(View view, String text) {
        viewHelper.setText(view, text);
    }

    /**
     * 给文本控件设置文本
     */
    @Override
    public void setText(@IdRes int id, String text) {
        viewHelper.setText(findView(id), text);
    }

    @Override
    public void showFailLayout() {
        viewHelper.showFailLayout();
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

    @Override
    public int getLeftTitleViewId() {
        return 0;
    }

    @Override
    public void onClickLeftTitleView(@IdRes int id) {
        finish();
    }

    @Override
    public int getRightTitleViewId() {
        return 0;
    }

    @Override
    public void onClickRightTitleView(@IdRes int id) {

    }

    /**
     * 设置沉浸式状态栏
     */
    protected void initStatusBar() {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (parentView != null) {
                parentView.setFitsSystemWindows(true);
            }
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.qg_title_color_status_bar);
            //tintManager.setNavigationBarTintResource(R.color.qg_title_color_navigation_bar);
        }
    }
}
