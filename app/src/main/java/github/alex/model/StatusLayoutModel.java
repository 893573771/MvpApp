package github.alex.model;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.alex.app.R;

import github.alex.annotation.Status;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class StatusLayoutModel {
    @LayoutRes
    /**默认视图的  xml 资源 id*/
    public int defaultLayoutId;
    @IdRes
    /**默认视图的  图片控件 资源 id*/
    public int defaultImageViewId;
    @IdRes
    /**默认视图的  文本控件 资源 id*/
    public int defaultTextViewId;
    @LayoutRes
    /**加载中视图的  xml 资源 id*/
    public int loadingLayoutId;
    @IdRes
    /**加载中视图的  图片控件 资源 id*/
    public int loadingViewId;
    @IdRes
    /**加载中视图的  文本控件 资源 id*/
    public int loadingTextViewId;
    @LayoutRes
    /**加载失败视图的  xml 资源 id*/
    public int failLayoutId;
    @IdRes
    /**加载失败视图的  图片控件 资源 id*/
    public int failImageViewId;
    @IdRes
    /**加载失败视图的  文本控件 资源 id*/
    public int failTextViewId;
    @LayoutRes
    /**加载失败视图的  xml 资源 id*/
    public int netErrorLayoutId;
    @IdRes
    /**加载失败视图的  图片控件 资源 id*/
    public int netErrorImageViewId;
    @IdRes
    /**加载失败视图的  文本控件 资源 id*/
    public int netErrorTextViewId;
    @LayoutRes
    /**空数据视图的  xml 资源 id*/
    public int emptyLayoutId;
    @IdRes
    /**空数据视图的  图片控件 资源 id*/
    public int emptyImageViewId;
    @IdRes
    /**空数据视图的  文本控件 资源 id*/
    public int emptyTextViewId;

    public static StatusLayoutModel instance;

    public StatusLayoutModel setDefaultLayoutId(int defaultLayoutId) {
        this.defaultLayoutId = defaultLayoutId;
        return this;
    }

    public StatusLayoutModel setDefaultImageViewId(int defaultImageViewId) {
        this.defaultImageViewId = defaultImageViewId;
        return this;
    }

    public StatusLayoutModel setDefaultTextViewId(int defaultTextViewId) {
        this.defaultTextViewId = defaultTextViewId;
        return this;
    }

    public StatusLayoutModel setLoadingLayoutId(int loadingLayoutId) {
        this.loadingLayoutId = loadingLayoutId;
        return this;
    }

    public StatusLayoutModel setLoadingViewId(int loadingViewId) {
        this.loadingViewId = loadingViewId;
        return this;
    }

    public StatusLayoutModel setLoadingTextViewId(int loadingTextViewId) {
        this.loadingTextViewId = loadingTextViewId;
        return this;
    }

    public StatusLayoutModel setFailLayoutId(int failLayoutId) {
        this.failLayoutId = failLayoutId;
        return this;
    }

    public StatusLayoutModel setFailImageViewId(int failImageViewId) {
        this.failImageViewId = failImageViewId;
        return this;
    }

    public StatusLayoutModel setFailTextViewId(int failTextViewId) {
        this.failTextViewId = failTextViewId;
        return this;
    }

    public StatusLayoutModel setNeErrorLayoutId(int netErrorLayoutId) {
        this.netErrorLayoutId = netErrorLayoutId;
        return this;
    }

    public StatusLayoutModel setNeErrorImageViewId(int netErrorImageViewId) {
        this.netErrorImageViewId = netErrorImageViewId;
        return this;
    }

    public StatusLayoutModel setNeErrorTextViewId(int netErrorTextViewId) {
        this.netErrorTextViewId = netErrorTextViewId;
        return this;
    }

    public StatusLayoutModel setEmptyLayoutId(int emptyLayoutId) {
        this.emptyLayoutId = emptyLayoutId;
        return this;
    }

    public StatusLayoutModel setEmptyImageViewId(int emptyImageViewId) {
        this.emptyImageViewId = emptyImageViewId;
        return this;
    }

    public StatusLayoutModel setEmptyTextViewId(int emptyTextViewId) {
        this.emptyTextViewId = emptyTextViewId;
        return this;
    }

    /**
     * 获取一个默认的多状态布局的模型
     */
    public static StatusLayoutModel defaultModel() {

        if (instance == null) {
            synchronized (StatusLayoutModel.class) {
                instance = (instance == null) ? new StatusLayoutModel() : instance;
            }
        }
        instance.setDefaultLayoutId(R.layout.alex_layout_default)
                .setDefaultImageViewId(R.id.iv_logo)
                .setDefaultTextViewId(R.id.tv_content)
                .setLoadingLayoutId(R.layout.alex_layout_loading_circle_orange)
                .setLoadingViewId(Status.NO_RES_ID)
                .setLoadingTextViewId(Status.NO_RES_ID)
                .setEmptyLayoutId(R.layout.alex_layout_empty)
                .setEmptyImageViewId(R.id.iv_logo)
                .setEmptyTextViewId(R.id.tv_content)
                .setNeErrorLayoutId(R.layout.alex_layout_net_error)
                .setNeErrorImageViewId(R.id.iv_logo)
                .setNeErrorTextViewId(R.id.tv_content)
                .setFailLayoutId(R.layout.alex_layout_fail)
                .setFailImageViewId(R.id.iv_logo)
                .setFailTextViewId(R.id.tv_content);
        return instance;
    }

}
