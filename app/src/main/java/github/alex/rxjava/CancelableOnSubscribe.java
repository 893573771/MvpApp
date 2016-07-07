package github.alex.rxjava;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alex on 2016/7/7.
 */

public class CancelableOnSubscribe<T> implements Observable.OnSubscribe<Response<T>>{
    private final Call<T> originalCall;

    public CancelableOnSubscribe(Call<T> originalCall) {
        this.originalCall = originalCall;
    }

    @Override
    public void call(Subscriber<? super Response<T>> subscriber) {
        // Since Call is a one-shot type, clone it for each new subscriber.
        final Call<T> call = originalCall.clone();
        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                call.cancel();
            }
        }));
        /////
        try {
            Response<T> response = call.execute();
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(response);
            }
        } catch (Throwable t) {
            Exceptions.throwIfFatal(t);
            if (!subscriber.isUnsubscribed()) {
                subscriber.onError(t);
            }
            return;
        }
        /////
        if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
        }

    }
}
