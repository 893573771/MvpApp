package com.alex.app.ui.zhihu;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alex.app.R;
import com.alex.app.config.Util;
import com.alex.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import github.alex.adapter.TitleFragmentPagerAdapter;
import github.alex.mvp.CancelablePresenter;
import github.alex.util.font.FontUtil;
import github.astuetz.PagerSlidingTabStrip;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */

public class ZhiHuActivity extends BaseActivity {
    protected ViewPager viewPager;

    /**
     * 创建 Presenter
     */
    @Override
    protected CancelablePresenter createPresenter() {
        return null;
    }

    /**
     * 获取标题的左部按钮，大多数情况下为 返回 按钮
     */
    @Override
    public int getLeftFinishViewId() {
        return R.id.iv_back;
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_zhihu;
    }

    /**
     * 获取启动者通过Intent传递过来的 数据
     *
     * @param map
     */
    @Override
    public void onGetIntentData(Map map) {
        super.onGetIntentData(map);
        Util.printMap(map);
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        setText(R.id.tv_title, "知乎日报");
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.psts);
        pagerSlidingTabStrip.setTypeface(FontUtil.typeface, Typeface.BOLD);
        viewPager = (ViewPager) findViewById(R.id.vp);
        List<Fragment> list = new ArrayList<Fragment>();
        List<String> listTitle = new ArrayList<String>();
        String strAry[] = new String[]{"今日头条", "互联网安全", "体育日报", "不准无聊"};
        for (int i = 0; i < strAry.length; i++) {
            list.add(ZhiHuFragment.getInstance(i));
            listTitle.add(strAry[i]);
        }
        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(), list, listTitle);
        viewPager.setAdapter(adapter);
        pagerSlidingTabStrip.setViewPager(viewPager);

    }
}
