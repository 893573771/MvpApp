package com.alex.app.ui.userman;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.hanks.checkbox.MaterialCheckBox;
import rx.Observable;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

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
    private Observable<CharSequence> phoneObservable;
    private Observable<CharSequence> pwdObservable;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public int getLeftFinishViewId() {
        return R.id.iv_back;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        setBackgroundColor(Color.parseColor("#FFFFFF"));
        ButterKnife.bind(this);
        setText(R.id.tv_title, "登录");
        materialCheckBox.setBackgroundColor(Color.parseColor("#F5F5F5"));
        materialCheckBox.setDoneShapeColor(Color.parseColor("#FF5722"));
        materialCheckBox.setPaintCenterColor(Color.parseColor("#FFFFFF"));
        materialCheckBox.setBorderColor(Color.parseColor("#E8E8E8"));
        setSelection(etPhone);
        setSelection(etPwd);
        findView(R.id.tv_login).setOnClickListener(this);
        phoneObservable = RxTextView.textChanges(etPhone);
        pwdObservable = RxTextView.textChanges(etPwd);

    }



    @Override
    public void onClick(View v) {
        if (R.id.tv_login == v.getId()) {
            presenter.localValidateLoginInfo(etPhone.getText().toString(), etPwd.getText().toString());
        }
    }

    @Override
    public void onLocalValidateError(String message) {
        toast(message);
    }

}
