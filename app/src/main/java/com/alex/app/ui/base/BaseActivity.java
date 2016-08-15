package com.alex.app.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.alex.annotation.Status;
import github.alex.helper.ViewHelper;
import github.alex.model.ParcelableMap;
import github.alex.model.StatusLayoutModel;
import github.alex.mvp.BaseHttpContract;
import github.alex.mvp.CancelablePresenter;
import github.alex.util.KeyPadUtil;
import github.alex.util.LogUtil;
import github.alex.util.font.FontUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static github.alex.model.ParcelableMap.bundleKey;
import static github.alex.model.ParcelableMap.extraBundle;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public abstract class BaseActivity<P extends CancelablePresenter> extends AppCompatActivity implements BaseHttpContract.View, View.OnClickListener {

    protected Context context;
    /**
     * Pre 的基类
     */
    protected P presenter;
    private ViewHelper viewHelper;
    private SystemBarTintManager tintManager;
    private Subscription subscription;
    private CompositeSubscription compositeSubscription;
    /**
     * 上次点击的时间
     */
    private static long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        presenter = createPresenter();
        viewHelper = new ViewHelper(this);
        if ((Status.NO_RES_ID != getLayoutResId()) && (0 != getLayoutResId())) {
            setContentView(getLayoutResId());
            setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
        if (findViewById(getLeftFinishViewId()) != null) {
            findViewById(getLeftFinishViewId()).setOnClickListener(this);
        }
        onGetStatusLayoutModel();
        getBodyViewId();
        viewHelper.initMultiModeBodyLayout(this, getBodyViewId());
        initStatusBar();
        /*获取上个页面 传递的数据*/
        Map<String, Object> intentMap = getStringObjectMap();
        onGetIntentData(intentMap);

        onCreateData();
        FontUtil.setFontTypeface(findViewById(android.R.id.content));
    }

    /**
     * 创建 Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取上个页面 传递的数据
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Map<String, Object> intentMap = getStringObjectMap();
        onGetIntentData(intentMap);
    }

    @NonNull
    private Map<String, Object> getStringObjectMap() {
        Bundle intentBundle = getIntent().getParcelableExtra(extraBundle);
        List<String> bundleKeyList = getIntent().getStringArrayListExtra(bundleKey);
        Map<String, Object> intentMap = new HashMap<>();
        for (int i = 0; (bundleKeyList != null) && (i < bundleKeyList.size()); i++) {
            String key = bundleKeyList.get(i);
            Object value = intentBundle.get(key);
            intentMap.put(key, value);
        }
        return intentMap;
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
        return Status.NO_RES_ID;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     *
     * @param map
     */
    @Override
    public void onGetIntentData(Map<String, Object> map) {

    }

    /**
     * 给控件添加点击事件
     *
     * @param ids
     */
    @Override
    public void setOnClickListener(@IdRes int... ids) {
        for (int i = 0; (ids != null) && (i < ids.length); i++) {
            View view = findViewById(ids[i]);
            if (view != null) {
                view.setOnClickListener(this);
            }
        }
    }

    /**
     * 展示吐司
     */
    @Override
    public void toast(@NonNull String text) {
        if (viewHelper != null){
            viewHelper.toast(context, text);
        }
    }

    @Override
    public void onInitLoadingDialog() {
        if (viewHelper != null){
            viewHelper.initLoadingDialog();
        }
    }

    /**
     * 展示延时对话框
     */
    @Override
    public void showLoadingDialog() {
        if (viewHelper != null){
            viewHelper.showLoadingDialog();
        }
    }

    /**
     * 隐藏延时对话框
     */
    @Override
    public void dismissLoadingDialog() {
        if (viewHelper != null){
            viewHelper.dismissLoadingDialog();
        }
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
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
        if (getLeftFinishViewId() == v.getId()) {
            finish();
        }
    }

    /**
     * 判断点击过快 时间间隔 为 300毫秒
     */
    private boolean isClickFrequently() {
        long currClickTime = System.currentTimeMillis();
        if ((currClickTime - lastClickTime) < 300) {
            return true;
        }
        lastClickTime = currClickTime;
        return false;
    }

    /**
     * 页面跳转，无传值
     */
    public void startActivity(@NonNull Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 页面跳转，有传值
     */
    public <M extends ParcelableMap> void startActivity(@NonNull Class clazz, @NonNull M parcelableMap) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(extraBundle, parcelableMap.getBundle());
        intent.putStringArrayListExtra(bundleKey, parcelableMap.getKeyList());
        startActivity(intent);
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
        setText(findView(id), text);
    }

    /**
     * 将EditText的光标移至最后
     *
     * @param view
     */
    @Override
    public void setSelection(@NonNull View view) {
        if (viewHelper == null) {
            LogUtil.e("viewHelper 为空");
            return;
        }
        viewHelper.setSelection(view);
    }

    @Override
    public void showFailLayout() {
        if (viewHelper == null) {
            LogUtil.e("viewHelper 为空");
            return;
        }
        viewHelper.showFailLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (viewHelper != null) {
            viewHelper.cancelToast();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        KeyPadUtil.instance().closeKeyPad(this);
    }

    /**
     * 扩展的 findView
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

    /**
     * 下拉刷新 或 加载 完成
     */
    @Override
    public void onRefreshFinish() {

    }

    /**
     * 获取标题的左部按钮，大多数情况下为 返回 按钮
     */
    @Override
    public int getLeftFinishViewId() {
        return Status.NO_RES_ID;
    }

    /**
     * 得到上下文对象
     */
    @NonNull
    @Override
    public Context getViewContext() {
        return this;
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

    /**
     * 给整体 Activity 设置背景色
     */
    public void setBackgroundColor(int color) {
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        contentFrameLayout.setBackgroundColor(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return KeyPadUtil.instance().canHiddenKeyPad(BaseActivity.this);
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public final class ScrollOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return KeyPadUtil.instance().canHiddenKeyPad(BaseActivity.this);
            }
            return false;
        }

    }

    /**
     * 添加 Subscription
     */
    @Override
    public void addSubscription(Subscription subscription) {
        compositeSubscription = (compositeSubscription == null) ? new CompositeSubscription() : compositeSubscription;
        compositeSubscription.add(subscription);
    }

    /**
     * 销毁一切资源，防止内存泄漏问题
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewHelper != null) {
            viewHelper.detachFromView();
            viewHelper = null;
        }
        tintManager = null;
        KeyPadUtil.instance().closeKeyPad(this);
        KeyPadUtil.detachView();
        onDetachFromView();
        if (presenter != null) {
            presenter.detachFromView();
        }
        presenter = null;
    }

    /**
     * 解除订阅，防止内存泄漏
     */
    @Override
    public void onDetachFromView() {
        if (compositeSubscription != null) {
            /*防止 RxJava 出现内存泄漏问题*/
            compositeSubscription.clear();
            compositeSubscription = null;
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

    /*操作符 - 转换类**/
    public final class AddSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            addSubscription(subscriber);
            return subscriber;
        }
    }

    public final class ReplaceSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            subscription = subscriber;
            return subscriber;
        }
    }
}
