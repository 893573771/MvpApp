package github.alex.callback;

/**
 * Created by Alex on 2016/6/13.
 */
public abstract class OnHttpCallback<T>{
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    /**
     * 开始网络请求
     */
    public abstract void onStart();

    /**
     * 网络请求失败
     *
     * @param httpCode 当前的状态码
     * @param message  请求失败的消息
     */
    public abstract void onFailure(int httpCode, String message);
    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);
    public abstract void onCompleted();
}
