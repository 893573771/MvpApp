package github.alex.rxjava;

/**
 * Created by Alex on 2016/6/21.
 */
public abstract class StringSubscriber extends BaseSubscriber<String> {
    public static String TAG = "#StringSubscriber#";
    /**
     * 网络请求成功
     *
     * @param result 网络请求的结果
     */
    @Override
    public abstract void onSuccess(String result);
}
