package com.alex.mvpapp;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.alex.mvpapp.baseui.BaseActivity;
import com.alex.mvpapp.ui.userman.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreateData() {
        ButterKnife.bind(this);
        findView(R.id.tv_login).setOnClickListener(this);
        SpannableString spannableString = new SpannableString("你好");
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5F);
        spannableString.setSpan(relativeSizeSpan, 0,1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvContent.append(spannableString);

        SpannableString spannableString2 = new SpannableString("安卓");
        RelativeSizeSpan relativeSizeSpan2 = new RelativeSizeSpan(2.5F);
        spannableString2.setSpan(relativeSizeSpan2, 0,2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tvContent.append(spannableString2);


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_login == v.getId()) {
            startActivity(LoginActivity.class);
        }
    }

}
