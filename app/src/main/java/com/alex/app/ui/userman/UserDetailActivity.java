package com.alex.app.ui.userman;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import github.alex.annotation.Status;
import github.alex.helper.GlideImageLoader;
import github.alex.util.FinalUtil;

/**
 * Created by alex on 2016/6/29.
 */
public class UserDetailActivity extends BaseActivity<UserDetailPresenter> implements UserDetailContract.View {
    private final int requestCodeGallery = 1001;
    private UserDetailPresenter presenter;
    /**
     * 文件 路径
     */
    private List<PhotoInfo> photoPathList;
    private List<Integer> ivIdList;

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

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ivIdList = new ArrayList<>();
        photoPathList = new ArrayList<>();
        presenter = new UserDetailPresenter(this);
        ivIdList.add(R.id.iv_0);
        ivIdList.add(R.id.iv_1);
        ivIdList.add(R.id.iv_2);
        findView(R.id.tv_left).setOnClickListener(this);
        findView(R.id.tv_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_left == v.getId()) {
            ThemeConfig theme = FinalUtil.getThemeConfig();
            FunctionConfig config = FinalUtil.getFunctionConfig(3);
            ImageLoader imageloader = new GlideImageLoader();
            CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme).setFunctionConfig(config).build();
            GalleryFinal.init(coreConfig);
            GalleryFinal.openGallerySingle(requestCodeGallery, config, new MyOnHandlerResultCallback());
        } else if (R.id.tv_right == v.getId()) {
            ThemeConfig theme = FinalUtil.getThemeConfig();
            FunctionConfig config = FinalUtil.getFunctionConfig(3);
            ImageLoader imageloader = new GlideImageLoader();
            CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme).setFunctionConfig(config).build();
            GalleryFinal.init(coreConfig);
            GalleryFinal.openGalleryMuti(requestCodeGallery, config, new MyOnHandlerResultCallback());
        }
    }

    @Override
    public void onStatusLayoutClick(int status) {
        super.onStatusLayoutClick(status);
        if (Status.FAIL == status) {
            List<File> fileList = new ArrayList<>();
            for (int i = 0; (i < ivIdList.size()) && (photoPathList != null) && (i < photoPathList.size()); i++) {
                fileList.add(new File(photoPathList.get(i).getPhotoPath()));
            }
            presenter.upLoadFile(fileList, "13146008029", "123456");
        }
    }

    private final class MyOnHandlerResultCallback implements GalleryFinal.OnHandlerResultCallback {
        /**
         * 处理成功
         *
         * @param requestCode
         * @param resultList
         */
        @Override
        public void onSuccess(int requestCode, List<PhotoInfo> resultList) {
            photoPathList = resultList;
            List<File> fileList = new ArrayList<>();
            for (int i = 0; (i < ivIdList.size()) && (photoPathList != null) && (i < photoPathList.size()); i++) {
                ((ImageView) findView(ivIdList.get(i))).setImageURI(Uri.parse(photoPathList.get(i).getPhotoPath()));
                fileList.add(new File(photoPathList.get(i).getPhotoPath()));

            }
            presenter.upLoadFile(fileList, "13146008029", "123456");
        }

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param message
         */
        @Override
        public void onFailure(int requestCode, String message) {
            toast(message);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }
}
