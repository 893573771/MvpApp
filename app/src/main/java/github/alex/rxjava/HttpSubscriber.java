package github.alex.rxjava;


import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Alex on 2016/6/21.
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public HttpSubscriber() {

    }

    public HttpSubscriber(Object tag) {
        this.tag = tag;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        int code = 0;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            code = httpException.code();
        } else {
            code = 404;
        }
        onFailure(code, ErrorUtil.getErrorMessage(code));
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 开始网络请求
     */
    public abstract void onStart();

    /**
     * 网络请求失败
     *
     * @param code    当前的状态码
     * @param message 请求失败的消息
     */
    public abstract void onFailure(int code, String message);

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);
}
