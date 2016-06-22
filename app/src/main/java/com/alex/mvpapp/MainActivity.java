package com.alex.mvpapp;


import android.widget.TextView;

import com.alex.mvpapp.baseui.BaseActivity;
import com.alex.mvpapp.presenter.GithubPresenter;
import com.alex.mvpapp.presenter.QgLoginPresenter;
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
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        githubPresenter = new GithubPresenter(this);
        qgLoginPresenter = new QgLoginPresenter(this);
        //githubPresenter.getGithubBeanList();
        qgLoginPresenter.loginQgV2("13146008029","123456");
    }

    @Override
    public void setContextText(String text) {
        tvContent.setText(text);
    }

    @Override
    public int getBodyViewId() {
        return R.id.tv_content;
    }

    @Override
    public void onStatusLayoutClick(int status) {
        if ((StatusLayoutModel.layoutStatusFail == status) || (StatusLayoutModel.layoutStatusEmpty == status)) {
            qgLoginPresenter.loginQgV2("13146008029","123456");
        }
    }

}
