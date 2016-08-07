package com.alex.app.ui;

import android.view.View;
import android.widget.TextView;

import com.alex.app.R;
import com.alex.app.model.UserBean;
import com.alex.app.ui.base.BaseActivity;
import com.alex.app.ui.index.IndexActivity;
import com.alex.app.ui.userman.LoginActivity;
import com.alex.app.ui.userman.UserDetailActivity;
import com.alex.app.ui.zhihu.ZhiHuActivity;

import github.alex.model.ParcelableMap;
import github.alex.mvp.CancelablePresenter;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
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
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        tvContent = findView(R.id.tv_content);
        setOnClickListener(R.id.tv_login, R.id.tv_add_img, R.id.tv_doubai, R.id.tv_zhihu);
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        UserBean userBean = new UserBean();
        userBean.pwd = "123456";
        userBean.phone = "13146008029";
        ParcelableMap parcelableMap = new ParcelableMap().put("name", "张文亮").put("age", 24).put("isStu", true).put("userBean", userBean).put("userBean2", userBean);
        if (R.id.tv_login == v.getId()) {
            startActivity(LoginActivity.class);
        } else if (R.id.tv_add_img == v.getId()) {
            startActivity(UserDetailActivity.class, parcelableMap);
        } else if (R.id.tv_doubai == v.getId()) {
            startActivity(IndexActivity.class, parcelableMap);
        } else if (R.id.tv_zhihu == v.getId()) {
            //startTimer();
            startActivity(ZhiHuActivity.class, parcelableMap);
        }
    }

    @Override
    public void onBackPressed() {
        /*按返回键返回桌面，不结束Activity*/
        moveTaskToBack(true);
    }
}
