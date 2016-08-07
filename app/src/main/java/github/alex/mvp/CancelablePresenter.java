package github.alex.mvp;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class CancelablePresenter<V extends BaseContract.View> {
    protected V view;
    private CompositeSubscription compositeSubscription;
    /**加载类型*/
    protected String loadType;
    public CancelablePresenter(@NonNull V view) {
        this.view = view;
        compositeSubscription = new CompositeSubscription();
    }

    /**
     * 添加 Subscription
     */
    public void addSubscription(Subscription subscription) {
        compositeSubscription = (compositeSubscription == null) ? new CompositeSubscription() : compositeSubscription;
        compositeSubscription.add(subscription);
    }

    /**
     * 取消订阅事件， 防止内存泄漏
     */
    public void detachView() {
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
        compositeSubscription = null;
        view = null;
    }

    public final class AddSubscriberOperator<T> implements Observable.Operator<T, T> {
        @Override
        public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
            addSubscription(subscriber);
            return subscriber;
        }
    }
}
