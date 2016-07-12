package com.alex.app.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alex.app.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import github.alex.annotation.Status;
import github.alex.dialog.LoadingDialog;
import github.alex.dialog.basedialog.BaseDialog;
import github.alex.dialog.callback.DialogOnKeyListener;
import github.alex.helper.ToastHelper;
import github.alex.helper.ViewHelper;
import github.alex.model.StatusLayoutModel;
import github.alex.mvp.BaseHttpContract;
import github.alex.mvp.CancelablePresenter;
import github.alex.util.ClassUtil;
import github.alex.util.KeyPadUtil;
import github.alex.util.NetUtil;
import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * mvx 模式开发的 基类
 *
 * @author AlexCheung
 * @version 1.1
 * @blog http://www.jianshu.com/users/c3c4ea133871/latest_articles
 */
public abstract class BaseActivity<P extends CancelablePresenter> extends AppCompatActivity implements BaseHttpContract.View, View.OnClickListener {
    private static final String TAG = "#BaseActivity#";
    protected Context context;
    /**
     * Pre 的基类
     */
    protected P presenter;
    private LoadingDialog loadingDialog;
    private ToastHelper toastHelper;
    private ViewHelper viewHelper;
    private SystemBarTintManager tintManager;
    private SubscriptionList subscriptionList;
    /**
     * 上次点击的时间
     */
    private static long lastClickTime;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = createPresenter();
        toastHelper = new ToastHelper();
        viewHelper = new ViewHelper(this);
        onGetIntentData();
        if ((Status.RES_ID_NO != getLayoutResId()) && (0 != getLayoutResId())) {
            setContentView(getLayoutResId());
        }
        viewHelper.setOnLeftTitleViewClickListener(getLeftTitleViewId());
        viewHelper.setOnRightTitleViewClickListener(getRightTitleViewId());
        onGetStatusLayoutModel();
        getBodyViewId();
        viewHelper.initMultiModeBodyLayout(this, getBodyViewId());
        initStatusBar();
        onCreateData();
    }

    /**
     * 创建 Presenter
     */
    protected abstract P createPresenter();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        onGetIntentData();
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public abstract int getLayoutResId();

    /**
     * 获取 主布局视图 的 id
     */
    @Override
    public int getBodyViewId() {
        return Status.RES_ID_NO;
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
    public void toast(String text) {
        if ((toastHelper != null) && (context != null)) {
            toastHelper.toast(context, text);
        }
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
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     */
    @Override
    public void onClick(View v) {
        if (isClickFrequently()) {
            return;
        }
    }

    /**
     * 判断点击过快 时间间隔 为 500毫秒
     */
    private boolean isClickFrequently() {
        long currClickTime = System.currentTimeMillis();
        if ((currClickTime - lastClickTime) < 500) {
            return true;
        }
        lastClickTime = currClickTime;
        return false;
    }

    /**
     * 页面跳转
     */
    public void startActivity(@NonNull Class clazz) {
        ClassUtil.startActivity(this, clazz);
    }

    @Override
    public StatusLayoutModel onGetStatusLayoutModel() {
        return StatusLayoutModel.defaultModel();
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
    public void setFailMessage(String message) {
        viewHelper.showFailLayout();
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
    @CallSuper
    protected void onStop() {
        super.onStop();
        if (toastHelper != null) {
            toastHelper.onDetachView();
        }
        KeyPadUtil.closeKeyPad(this);
    }

    /**
     * 扩展的 findViewById
     */
    public <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 数据转换: dp---->px
     */
    public float dp2Px(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    /**
     * 多状态布局的 点击事件
     */
    @Override
    public void onStatusLayoutClick(@Status int status) {

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
        if (parentView != null) {
            parentView.setFitsSystemWindows(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.qg_title_color_status_bar);
        }
    }

    /**
     * 设置状态栏的颜色值
     *
     * @param colorId 颜色id
     */
    public void setStatusBarTintResource(int colorId) {
        if (tintManager != null) {
            tintManager.setStatusBarTintResource(colorId);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return KeyPadUtil.canHiddenKeyPad(BaseActivity.this);
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public final class ScrollOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return KeyPadUtil.canHiddenKeyPad(BaseActivity.this);
            }
            return false;
        }

    }

    /**
     * 添加 Subscription
     */
    public void addSubscription(Subscription subscription) {
        subscriptionList = (subscriptionList == null) ? new SubscriptionList() : subscriptionList;
        subscriptionList.add(subscription);
    }

    /**
     * 获取当前网络是否可用
     */
    @Override
    public boolean isNetworkAvailable() {
        return NetUtil.isAvailable(this);
    }

    /**
     * 销毁一切资源，防止内存泄漏问题
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (viewHelper != null) {
            viewHelper.onDetachView();
            viewHelper = null;
        }
        if (toastHelper != null) {
            toastHelper.onDetachView();
            toastHelper = null;
        }
        tintManager = null;
        KeyPadUtil.closeKeyPad(this);
        if (subscriptionList != null) {
            /*防止 RxJava 出现内存泄漏问题*/
            subscriptionList.unsubscribe();
            subscriptionList = null;
        }
        if (presenter != null) {
            presenter.detachView();
        }
        presenter = null;
    }

}
