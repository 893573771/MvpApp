package github.alex.mvp;

import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by alex on 2016/7/7.
 */
public abstract class CancelablePresenter {

    public Subscription subscription;
    public SubscriptionList subscriptionList;

    public CancelablePresenter() {
        subscriptionList = new SubscriptionList();
    }

    /**
     * 设置  Subscription
     */
    void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    /**
     * 添加 Subscription
     */
    void addSubscription(Subscription subscription) {
        subscriptionList = (subscriptionList == null) ? new SubscriptionList() : subscriptionList;
        subscriptionList.add(subscription);
    }

    /**
     * 取消订阅事件
     */
    public void cancel(){
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
        if (subscriptionList != null) {
            subscriptionList.unsubscribe();
            subscriptionList = null;
        }
    }

}
