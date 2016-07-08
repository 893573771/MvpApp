package com.alex.app.ui;

import android.view.View;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.model.UserBean;
import com.alex.app.ui.base.BaseActivity;
import com.alex.app.ui.userman.LoginActivity;
import com.alex.app.ui.userman.UserDetailActivity;
import com.socks.library.KLog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {

    TextView tvContent;
    private List<UserBean> userBeanList;
    private Subscription subscription;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        findView(R.id.tv_login).setOnClickListener(this);
        findView(R.id.tv_add_img).setOnClickListener(this);
        /*使用 repeatWhen 延时6s 后，重订阅一次*/
        Observable.range(1, 5)
                .repeatWhen(new MyFunc1())
                .subscribe(new ObjectSubscriber());

    }

    private final class MyFunc1 implements Func1<Observable<? extends Void>, Observable<?>> {
        @Override
        public Observable<?> call(Observable<? extends Void> observable) {
            return Observable.timer(6, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_login == v.getId()) {
            startActivity(LoginActivity.class);
        } else if (R.id.tv_add_img == v.getId()) {
            startActivity(UserDetailActivity.class);
        }
    }

    private final class ObjectSubscriber extends Subscriber<Integer> {
        @Override
        public void onCompleted() {
            KLog.e("执行到... onCompleted");
        }

        @Override
        public void onError(Throwable throwable) {
            KLog.e("执行到... onError");
        }

        @Override
        public void onNext(Integer result) {
            KLog.e("onNext = " + result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }
}
