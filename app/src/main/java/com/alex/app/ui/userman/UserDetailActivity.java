package com.alex.app.ui.userman;

import android.view.View;
import android.widget.ImageView;

import com.alex.app.R;
import com.alex.app.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by alex on 2016/6/29.
 */
public class UserDetailActivity extends BaseActivity {

    @BindView(R.id.iv_0)
    ImageView iv0;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void onCreateData() {
        super.onCreateData();
        ButterKnife.bind(this);
        findView(R.id.tv_left).setOnClickListener(this);
        findView(R.id.tv_right).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.tv_left == v.getId()) {
            FunctionConfig config = new FunctionConfig.Builder().setMutiSelectMaxSize(8).build();
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, config, new MyOnHanlderResultCallback());
        } else if (R.id.tv_right == v.getId()) {
            //带配置
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

        }

        /**
         * 处理失败或异常
         *
         * @param requestCode
         * @param errorMsg
         */
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    }
}
