package github.alex.retrofit.httpman;

/**
 * 作者：Alex
 * 时间：2016年08月15日    00:10
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public abstract class BaseCallback<T> {
    /**
     * @param tag 网络请求的标志位
     */
    public Object tag;

    public BaseCallback() {

    }

    public BaseCallback(Object tag) {
        this.tag = tag;
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

    public void onCompleted() {
    }

    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    public abstract void onSuccess(T result);
}
