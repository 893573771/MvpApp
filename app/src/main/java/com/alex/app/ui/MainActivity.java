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
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class MainActivity extends BaseActivity {

    TextView tvContent;
    private List<UserBean> userBeanList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        findView(R.id.tv_login).setOnClickListener(this);
        findView(R.id.tv_add_img).setOnClickListener(this);
        subscription = Observable.interval(1, TimeUnit.SECONDS)
                .groupBy(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long value) {
                        return value % 3;
                    }
                })
                .subscribe(new Action1<GroupedObservable<Long, Long>>() {
                    @Override
                    public void call(final GroupedObservable<Long, Long> result) {
                        Subscription subscription = result.subscribe(new Action1<Long>() {
                                                                         @Override
                                                                         public void call(Long value) {
                                                                             KLog.e("key = " + result.getKey() + " value = " + value);
                                                                         }
                                                                     }
                        );
                        addSubscription(subscription);
                    }
                });
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
    
}
