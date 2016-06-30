package github.alex.model;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

/**
 * Created by Alex on 2016/6/21.
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
    /**空数据视图的  xml 资源 id*/
    public int emptyLayoutId;
    @IdRes
    /**空数据视图的  图片控件 资源 id*/
    public int emptyImageViewId;
    @IdRes
    /**空数据视图的  文本控件 资源 id*/
    public int emptyTextViewId;


    public StatusLayoutModel setDefaultLayoutId(int defaultLayoutId){
        this.defaultLayoutId = defaultLayoutId;
        return this;
    }

    public StatusLayoutModel setDefaultImageViewId(int defaultImageViewId){
        this.defaultImageViewId = defaultImageViewId;
        return this;
    }

    public StatusLayoutModel setDefaultTextViewId(int defaultTextViewId){
        this.defaultTextViewId = defaultTextViewId;
        return this;
    }
    public StatusLayoutModel setLoadingLayoutId(int loadingLayoutId){
        this.loadingLayoutId = loadingLayoutId;
        return this;
    }

    public StatusLayoutModel setLoadingViewId(int loadingViewId){
        this.loadingViewId = loadingViewId;
        return this;
    }

    public StatusLayoutModel setLoadingTextViewId(int loadingTextViewId){
        this.loadingTextViewId = loadingTextViewId;
        return this;
    }

    public StatusLayoutModel setFailLayoutId(int failLayoutId){
        this.failLayoutId  =failLayoutId;
        return this;
    }

    public StatusLayoutModel setFailImageViewId(int failImageViewId){
        this.failImageViewId = failImageViewId;
        return this;
    }

    public StatusLayoutModel setFailTextViewId(int failTextViewId){
        this.failTextViewId = failTextViewId;
        return this;
    }

    public StatusLayoutModel setEmptyLayoutId(int emptyLayoutId){
        this.emptyLayoutId = emptyLayoutId;
        return this;
    }

    public StatusLayoutModel setEmptyImageViewId(int emptyImageViewId){
        this.emptyImageViewId = emptyImageViewId;
        return this;
    }

    public StatusLayoutModel setEmptyTextViewId(int emptyTextViewId){
        this.emptyTextViewId = emptyTextViewId;
        return this;
    }

}
