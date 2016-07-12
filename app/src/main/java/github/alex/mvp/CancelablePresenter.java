package github.alex.mvp;

import android.support.annotation.NonNull;

import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by alex on 2016/7/7.
 */
public class CancelablePresenter<V extends BaseContract.View> {
    protected V view;
    private SubscriptionList subscriptionList;

    public CancelablePresenter(@NonNull V view) {
        this.view = view;
        subscriptionList = new SubscriptionList();
    }

    /**
     * 添加 Subscription
     */
    public void addSubscription(Subscription subscription) {
        subscriptionList = (subscriptionList == null) ? new SubscriptionList() : subscriptionList;
        subscriptionList.add(subscription);
    }

    /**
     * 取消订阅事件， 防止内存泄漏
     */
    public void detachView() {
        if (subscriptionList != null) {
            subscriptionList.unsubscribe();
            subscriptionList = null;
        }
        view = null;
    }
}
