package com.alex.mvpapp.ui.userman;

import com.alex.mvpapp.R;
import com.alex.mvpapp.baseui.BaseActivity;

/**
 * Created by alex on 2016/6/23.
 */
public class LoginActivity extends BaseActivity {

    @Override
    public int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        setText(R.id.tv_title, "登录");
    }

}
