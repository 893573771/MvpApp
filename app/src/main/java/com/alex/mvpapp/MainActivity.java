package com.alex.mvpapp;


import android.view.View;
import android.widget.TextView;

import com.alex.mvpapp.baseui.BaseActivity;
import com.alex.mvpapp.presenter.GithubPresenter;
import com.alex.mvpapp.presenter.QgLoginPresenter;
import com.alex.mvpapp.ui.userman.LoginActivity;
import com.alex.mvpapp.view.IGithubView;

import github.alex.model.StatusLayoutModel;

public class MainActivity extends BaseActivity implements IGithubView {
    private TextView tvContent;
    private GithubPresenter githubPresenter;
    private QgLoginPresenter qgLoginPresenter;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }
    @Override
    public int getBodyViewId() {
        return R.id.tv_content;
    }
    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        findView(R.id.tv_login).setOnClickListener(this);
        githubPresenter = new GithubPresenter(this);
        qgLoginPresenter = new QgLoginPresenter(this);
        //githubPresenter.getGithubBeanList();
        qgLoginPresenter.loginQgV2("13146008029","123456");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(R.id.tv_login == view.getId()){
            startActivity(LoginActivity.class);
        }
    }

    @Override
    public void setContextText(String text) {
        tvContent.setText(text);
    }

    @Override
    public void onStatusLayoutClick(int status) {
        if ((StatusLayoutModel.layoutStatusFail == status) || (StatusLayoutModel.layoutStatusEmpty == status)) {
            qgLoginPresenter.loginQgV2("13146008029","123456");
        }
    }

}
