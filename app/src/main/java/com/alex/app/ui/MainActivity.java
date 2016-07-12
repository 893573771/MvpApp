package com.alex.app.ui;

import android.view.View;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;
import com.alex.app.ui.userman.LoginActivity;
import com.alex.app.ui.userman.UserDetailActivity;

import github.alex.mvp.CancelablePresenter;

public class MainActivity extends BaseActivity<CancelablePresenter> {

    TextView tvContent;

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
