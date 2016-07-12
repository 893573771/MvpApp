package github.alex.rxjava;

import github.alex.util.HttpErrorUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Alex on 2016/6/21.
 */
public abstract class StringSubscriber extends Subscriber<String> {
    public static String TAG = "#StringSubscriber#";
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public StringSubscriber() {

    }

    public StringSubscriber(Object tag) {
        this.tag = tag;
    }

    /**网络请求结束，包含成功 和  异常*/
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        //Log.e(TAG, "有异常：" + e.getMessage());
        if (e instanceof HttpException) {
            onError(HttpErrorUtil.getMessage(((HttpException)e).code()));
        } else {
            onError(HttpErrorUtil.getMessage(e));
        }
    }

    @Override
    public void onNext(String t) {
        onSuccess(t);
    }

    /**
     * 开始网络请求
     */
    public abstract void onStart();

    /**
     * 网络请求失败
     *
     * @param message 请求失败的消息
     */
    public abstract void onError(String message);

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(String result);
}
