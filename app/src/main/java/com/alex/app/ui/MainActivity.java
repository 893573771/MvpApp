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

import github.alex.mvp.CancelablePresenter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class MainActivity extends BaseActivity<CancelablePresenter> {

    TextView tvContent;
    private List<UserBean> userBeanList;

    @Override
    protected CancelablePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        findView(R.id.tv_login).setOnClickListener(this);
        findView(R.id.tv_add_img).setOnClickListener(this);

        Subscription subscription = Observable.just(1, 2, 3, 4, 5)
                .skip(2)
                .subscribe(new Subscriber<Integer>() {
            @Override
            public void onNext(Integer item) {
                KLog.e("Next: " + item);
            }
            @Override
            public void onError(Throwable error) {
                KLog.e("Error: " + error.getMessage());
            }
            @Override
            public void onCompleted() {
                KLog.e("Sequence complete.");
            }
        });
        addSubscription(subscription);
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
