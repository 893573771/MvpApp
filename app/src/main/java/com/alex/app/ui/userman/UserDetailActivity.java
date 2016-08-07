package com.alex.app.ui.userman;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import github.alex.annotation.Status;
import github.yongchun.imageselector.view.ImageSelectorActivity;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class UserDetailActivity extends BaseActivity<UserDetailPresenter> implements UserDetailContract.View {
    private final int requestCodeGallery = 1001;
    /**
     * 文件 路径
     */
    private ArrayList<String> urlList;
    private List<Integer> ivIdList;
    private List<File> fileList;

    /**
     * 获取 主布局视图 的 id
     */
    @Override
    public int getBodyViewId() {
        return R.id.sv;
    }

    /**
     * 创建 Presenter
     */
    @Override
    protected UserDetailPresenter createPresenter() {
        return new UserDetailPresenter(this);
    }

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_detail;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     */
    @Override
    public void onCreateData() {
        super.onCreateData();
        ivIdList = new ArrayList<>();
        urlList = new ArrayList<>();
        presenter = new UserDetailPresenter(this);
        ivIdList.add(R.id.iv_0);
        ivIdList.add(R.id.iv_1);
        ivIdList.add(R.id.iv_2);
        findView(R.id.tv_left).setOnClickListener(this);
        findView(R.id.tv_right).setOnClickListener(this);
    }

    /**
     * 处理点击事件，过滤掉 500毫秒内连续 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        int maxSelectNum = 3;
        if (R.id.tv_left == v.getId()) {
            ImageSelectorActivity.start(UserDetailActivity.this, maxSelectNum, ImageSelectorActivity.MODE_SINGLE, true, true, true);
        } else if (R.id.tv_right == v.getId()) {
            ImageSelectorActivity.start(UserDetailActivity.this, maxSelectNum, ImageSelectorActivity.MODE_MULTIPLE, true, true, true);
        }
    }

    /**
     * 多状态布局的 点击事件
     *
     * @param status
     */
    @Override
    public void onStatusLayoutClick(int status) {
        super.onStatusLayoutClick(status);
        if (Status.FAIL == status) {
            List<File> fileList = new ArrayList<>();
            for (int i = 0; (i < ivIdList.size()) && (urlList != null) && (i < urlList.size()); i++) {
                ((ImageView) findView(ivIdList.get(i))).setImageURI(Uri.parse(urlList.get(i)));
                fileList.add(new File(urlList.get(i)));
            }
            presenter.upLoadFile(fileList, "13146008029", "123456");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
            urlList = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            List<File> fileList = new ArrayList<>();
            for (int i = 0; (i < ivIdList.size()) && (urlList != null) && (i < urlList.size()); i++) {
                ((ImageView) findView(ivIdList.get(i))).setImageURI(Uri.parse(urlList.get(i)));
                fileList.add(new File(urlList.get(i)));
            }
            presenter.upLoadFile(fileList, "13146008029", "123456");
        }
    }
}
