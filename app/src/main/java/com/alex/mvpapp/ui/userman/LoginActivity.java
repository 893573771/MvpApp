package com.alex.mvpapp.ui.userman;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.mvpapp.R;
import com.alex.mvpapp.baseui.BaseActivity;
import com.alex.mvpapp.config.AppConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.hanks.checkbox.MaterialCheckBox;

/**
 * Created by alex on 2016/6/23.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.tv_reget_login_pwd)
    TextView tvRegetLoginPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.mcb)
    MaterialCheckBox materialCheckBox;

    private LoginPresenter loginPresenter;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    public int getLeftTitleViewId() {
        return R.id.iv_back;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        setText(R.id.tv_title, "登录");
        materialCheckBox.setBackgroundColor(Color.parseColor("#F5F5F5"));
        materialCheckBox.setDoneShapeColor(Color.parseColor("#FF5722"));
        materialCheckBox.setPaintCenterColor(Color.parseColor("#FFFFFF"));
        materialCheckBox.setBorderColor(Color.parseColor("#E8E8E8"));
        findView(R.id.tv_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (R.id.tv_login == v.getId()) {
            loginPresenter.localValidateLoginInfo(etPhone.getText().toString(), etPwd.getText().toString());
        }else if(R.id.iv_clear == v.getId()){
            etPhone.setText("");
        }
    }

    @Override
    public void onLocalPhoneError() {
        showToast(AppConst.userManPhone);
    }

    @Override
    public void onLocalPwdError() {
        showToast(AppConst.userLoginPwd);
    }

}
