package com.alex.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;
import com.alex.app.ui.userman.LoginActivity;

public class MainActivity extends BaseActivity {

    TextView tvContent;
    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        findView(R.id.tv_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(R.id.tv_login == v.getId()){
            startActivity(LoginActivity.class);
        }
    }

}
