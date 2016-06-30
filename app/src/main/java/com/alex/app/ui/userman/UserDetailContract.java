package com.alex.app.ui.userman;

import java.io.File;

import github.alex.mvp.BaseHttpContract;

/**
 * Created by alex on 2016/6/29.
 */
public interface UserDetailContract {

    interface View extends BaseHttpContract.View {

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 上传图片文件
         */
        void upLoadFile(File file);
    }
}
