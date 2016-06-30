package com.alex.app.ui.userman;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;
import com.socks.library.KLog;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class UserDetailActivity extends BaseActivity implements UserDetailContract.View {

    @BindView(R.id.iv_0)
    ImageView iv0;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    private final int requestCodeGallery = 1001;
    private UserDetailPresenter presenter;
    /**文件 路径*/
    private String photoPath;

    @Override
    public int getBodyViewId() {
        return R.id.sv;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ButterKnife.bind(this);
        presenter = new UserDetailPresenter(this);
        findView(R.id.tv_left).setOnClickListener(this);
        findView(R.id.tv_right).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_left == v.getId()) {
            ThemeConfig theme = FinalUtil.getThemeConfig();
            FunctionConfig config = FinalUtil.getFunctionConfig();
            ImageLoader imageloader = new GlideImageLoader();
            CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme).setFunctionConfig(config).build();
            GalleryFinal.init(coreConfig);
            GalleryFinal.openGallerySingle(requestCodeGallery, config, new MyOnHanlderResultCallback());
        } else if (R.id.tv_right == v.getId()) {


        }
    }

    @Override
    public void onStatusLayoutClick(int status) {
        super.onStatusLayoutClick(status);
        if(Status.FAIL == status){
            File file = new File(photoPath);
            presenter.upLoadFile(file);
        }
    }

    private final class MyOnHanlderResultCallback implements GalleryFinal.OnHanlderResultCallback {

        /**
         * 处理成功
         *
         * @param reqeustCode
         * @param resultList
         */
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            KLog.e("" + photoPath);
            photoPath = resultList.get(0).getPhotoPath();
            iv0.setImageURI(Uri.parse(photoPath));
            File file = new File(photoPath);
            presenter.upLoadFile(file);
        }

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            showToast(errorMsg);
        }
    }
}
