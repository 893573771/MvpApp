package com.alex.app.ui.userman;

import java.io.File;
import java.util.List;

import github.alex.mvp.BaseHttpContract;
/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public interface UserDetailContract {

    interface View extends BaseHttpContract.View {

    }

    interface Presenter extends BaseHttpContract.Presenter {
        /**
         * 上传图片文件
         */
        void upLoadFile(List<File> fileList, String phone, String pwd);
    }
}
