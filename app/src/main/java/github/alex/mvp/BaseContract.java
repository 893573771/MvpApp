package github.alex.mvp;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import github.alex.annotation.Status;

/**
 * Created by alex on 2016/6/28.
 */
public interface BaseContract {
    interface View {
        /**
         * 配置 布局文件的 资源 id
         */
        @LayoutRes
        int getLayoutResId();

        /**
         * 获取bodyView的 资源id
         */
        @IdRes
        int getBodyViewId();

        /**
         * 执行在 onCreateView 中
         * 通过 findViewById 初始主视图化控件
         * 初始化所有基础数据，
         */
        void onCreateData();

        /**
         * 展示吐司
         *
         * @param text 吐司内容
         */
        void toast(@NonNull String text);

        /**
         * 多状态布局的 点击事件
         */
        void onStatusLayoutClick(@Status int status);

        /**
         * 给文本控件设置文本
         */
        void setText(@NonNull android.view.View view, @NonNull String text);

        /**
         * 给文本控件设置文本
         */
        void setText(@IdRes int id, @NonNull String text);

        /**
         * 获取标题左部返回按钮的id
         */
        @IdRes
        int getLeftFinishViewId();

        /**
         * 扩展的findViewById
         */
        <T extends android.view.View> T findView(@IdRes int id);

        /**给控件添加点击事件*/
        void setOnClickListener(int ... ids);
    }

    interface Presenter {


    }

}
