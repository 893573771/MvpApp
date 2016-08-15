package com.alex.app.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import github.alex.annotation.Status;
import github.alex.helper.ViewHelper;
import github.alex.model.StatusLayoutModel;
import github.alex.mvp.BaseHttpContract;
import github.alex.mvp.CancelablePresenter;
import github.alex.util.font.FontUtil;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public abstract class BaseFragment<P extends CancelablePresenter> extends Fragment implements BaseHttpContract.View, View.OnClickListener {
    /**
     * Fragment 的根视图
     */
    protected View rootView;
    protected boolean isVisible;
    protected Activity activity;
    /**
     * 当前 Fragment 在父窗体的 下标
     */
    protected int index;
    /**
     * 可以加载数据
     */
    protected boolean canLoad;
    /**
     * 上次点击的时间
     */
    private static long lastClickTime;
    private ViewHelper viewHelper;
    private Subscription subscription;
    private CompositeSubscription compositeSubscription;
    /**
     * Pre 的基类
     */
    protected P presenter;

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public abstract int getLayoutResId();

    /**
     * 得到上下文对象
     */
    @NonNull
    @Override
    public Context getViewContext() {
        return activity;
    }

    /**
     * 获取bodyView的 资源id
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
    public void onGetIntentData(@NonNull Map<String, Object> map) {

    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public abstract void onCreateData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        canLoad = true;
        activity = getActivity();
        onInitLoadingDialog();
        viewHelper = new ViewHelper(this);
        presenter = createPresenter();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        canLoad = true;
    /*防止View 重造，控件再次初始化*/
        if (rootView == null) {
            if ((Status.NO_RES_ID != getLayoutResId()) && (0 != getLayoutResId())) {
                rootView = inflater.inflate(getLayoutResId(), null);
            }
            onCreateData();
            FontUtil.setFontTypeface(rootView);
        }
        /*过滤Fragment重叠*/
        ViewGroup parent = null;
        if (rootView != null) {
            parent = (ViewGroup) rootView.getParent();
        }
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    /**
     * 创建 Presenter
     */
    protected abstract P createPresenter();

    /**
     * 添加 Subscription
     */
    @Override
    public void addSubscription(@NonNull Subscription subscription) {
        compositeSubscription = (compositeSubscription == null) ? new CompositeSubscription() : compositeSubscription;
        compositeSubscription.add(subscription);
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
     * 初始化延时对话框
     */
    @Override
    public void onInitLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.initLoadingDialog();
        }
    }

    /**
     * 展示延时对话框
     */
    @Override
    public void showLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.showLoadingDialog();
        }
    }

    /**
     * 隐藏延时对话框
     */
    @Override
    public void dismissLoadingDialog() {
        if (viewHelper != null) {
            viewHelper.dismissLoadingDialog();
        }
    }

    /**
     * 得到多状态布局， 数据模型
     */
    @Override
    public StatusLayoutModel onGetStatusLayoutModel() {
        return StatusLayoutModel.defaultModel();
    }

    /**
     * 展示默认布局
     */
    @Override
    public void showDefaultLayout() {
        viewHelper.showDefaultLayout();
    }

    /**
     * 展示loading布局
     */
    @Override
    public void showLoadingLayout() {
        viewHelper.showLoadingLayout();
    }

    /**
     * 展示请求成功后的布局
     */
    @Override
    public void showSuccessLayout() {
        viewHelper.showSuccessLayout();
    }

    /**
     * 展示请求空数据的布局
     */
    @Override
    public void showEmptyLayout() {
        if (viewHelper != null) {
            viewHelper.showEmptyLayout();
        }
    }

    /**
     * 展示出错消息
     */
    @Override
    public void showFailLayout() {
        if (viewHelper != null) {
            viewHelper.showFailLayout();
        }
    }

    /**
     * 展示出错消息
     */
    @Override
    public void setFailMessage(@NonNull String message) {
        if (viewHelper != null) {
            viewHelper.showFailLayout();
            viewHelper.setFailMessage(message);
        }
    }

    /**
     * 展示吐司
     *
     * @param text 吐司内容
     */
    @Override
    public void toast(@NonNull String text) {
        if (viewHelper != null) {
            viewHelper.toast(activity, text);
        }
    }

    /**
     * 多状态布局的 点击事件
     *
     * @param status
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
     * 给文本控件设置文本
     */
    @Override
    public void setText(@NonNull View view, @NonNull String text) {
        if (viewHelper != null) {
            viewHelper.setText(view, text);
        }
    }

    /**
     * 给文本控件设置文本
     */
    @Override
    public void setText(@IdRes int id, @NonNull String text) {
        setText(findView(id), text);
    }

    /**
     * 将EditText的光标移至最后
     *
     * @param view
     */
    @Override
    public void setSelection(@NonNull View view) {
        if (viewHelper != null) {
            viewHelper.setSelection(view);
        }
    }

    /**
     * 获取标题左部返回按钮的id
     */
    @Override
    public int getLeftFinishViewId() {
        return Status.NO_RES_ID;
    }

    /**
     * 扩展的findViewById
     *
     * @param id
     */
    @Override
    public <T extends View> T findView(@IdRes int id) {
        return (T) rootView.findViewById(id);
    }

    /**
     * 给控件添加点击事件
     *
     * @param ids
     */
    @Override
    public void setOnClickListener(@IdRes int... ids) {
        for (int i = 0; (ids != null) && (i < ids.length); i++) {
            View view = findView(ids[i]);
            if (view != null) {
                view.setOnClickListener(this);
            }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null && rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    /**
     * 销毁一切资源，防止内存泄漏问题
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewHelper != null) {
            viewHelper.detachFromView();
            viewHelper = null;
        }
        onDetachFromView();
        if (presenter != null) {
            presenter.detachFromView();
        }
        presenter = null;
        activity = null;
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

    public final class ReplaceSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            subscription = subscriber;
            return subscriber;
        }
    }

    /*操作符 - 转换类**/
    public final class AddSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            addSubscription(subscriber);
            return subscriber;
        }
    }
}
