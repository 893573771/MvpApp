package github.alex.rxjava;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public abstract class StringSubscriber extends BaseSubscriber<String> {
    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    @Override
    public abstract void onSuccess(String result);
}
