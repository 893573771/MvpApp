package github.alex.rxjava;


import github.alex.util.HttpErrorUtil;
import github.alex.util.LogUtil;
import rx.Subscriber;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public BaseSubscriber() {

    }

    public BaseSubscriber(Object tag) {
        this.tag = tag;
    }

    /**
     * 开始网络请求
     */
    public abstract void onStart();

    @Override
    public void onError(Throwable e) {
        LogUtil.e("有异常：" + e);
        //onError("有异常: "+e);
        onError(HttpErrorUtil.getMessage(e));
    }

    /**
     * 网络请求失败
     *
     * @param message 请求失败的消息
     */
    public abstract void onError(String message);

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);

}
