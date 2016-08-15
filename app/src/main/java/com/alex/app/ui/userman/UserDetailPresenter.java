package com.alex.app.ui.userman;

import android.support.annotation.NonNull;

import com.alex.app.httpman.HttpMan;
import com.alex.app.httpman.UrlMan;
import com.alex.app.model.qianguan.LoginBean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import github.alex.mvp.CancelablePresenter;
import github.alex.retrofit.RetrofitClient;
import github.alex.rxjava.BaseSubscriber;
import github.alex.rxjava.RxUtil;
import github.alex.util.LogUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：Alex
 * 时间：2016年08月06日    08:06
 * 博客：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class UserDetailPresenter extends CancelablePresenter<UserDetailContract.View> implements UserDetailContract.Presenter {

    public UserDetailPresenter(@NonNull UserDetailContract.View view) {
        super(view);
    }

    @Override
    public void upLoadFile(List<File> fileList, String phone, String pwd) {

        if ((fileList == null) || (fileList.size() <= 0)) {
            LogUtil.e("文件为空");
            return;
        }



        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), fileList.get(i));
            paramsMap.put("userLogo\"; filename=\"" + file.getName() + ".png", fileBody);
        }
        paramsMap.put("phone", RequestBody.create(null, phone));
        paramsMap.put("pwd", RequestBody.create(null, pwd));

        new RetrofitClient.Builder()
                .baseUrl(UrlMan.ZhiHu.baseUrl)
                .build()
                .create(HttpMan.class).upLoad2(paramsMap)
                .compose(RxUtil.<LoginBean>rxHttp())
                .lift(new ReplaceSubscriberOperator())
                .subscribe(new MyHttpSubscriber());

    }

    private final class MyHttpSubscriber extends BaseSubscriber<LoginBean> {
        @Override
        public void onStart() {
            view.showLoadingDialog();
        }

        @Override
        public void onError(String message) {
            LogUtil.e(message);
            view.setFailMessage(message);
            view.dismissLoadingDialog();
        }

        @Override
        public void onSuccess(LoginBean result) {

            view.dismissLoadingDialog();
            view.toast("上传成功");
        }
    }

}
