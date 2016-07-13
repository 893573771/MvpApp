package com.alex.app.ui;

import android.view.View;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;
import com.alex.app.ui.index.IndexActivity;
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

    /**
     * 执行在 onCreateView 中
     * 通过 findViewById 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        setOnClickListener(R.id.tv_login, R.id.tv_add_img, R.id.tv_doubai);
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_login == v.getId()) {
            startActivity(LoginActivity.class);
        } else if (R.id.tv_add_img == v.getId()) {
            startActivity(UserDetailActivity.class);
        } else if(R.id.tv_doubai == v.getId()){
            startActivity(IndexActivity.class);
        }
    }


}
