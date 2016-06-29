package github.alex.mvp;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import github.alex.annotation.LayoutStatus;

/**
 * Created by hasee on 2016/6/28.
 */
public interface BaseContract {
    interface View {
        /**
         * 获取bodyView的 资源id
         */
        @LayoutRes
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
        void showToast(@NonNull String text);

        /**
         * 多状态布局的 点击事件
         */

        void onStatusLayoutClick(@LayoutStatus int status);

        /**
         * 给文本控件设置文本
         */
        void setText(@NonNull android.view.View view, @NonNull String text);

        /**
         * 给文本控件设置文本
         */
        void setText(@IdRes int id, @NonNull String text);

        @IdRes
        int getLeftTitleViewId();

        /**
         * 处理点击标题的左部按钮，大多数情况下为返回 按钮
         */
        void onClickLeftTitleView(@IdRes int id);

        @IdRes
        int getRightTitleViewId();

        /**
         * 处理点击标题的右部按钮，大多数情况下为分享 更多等功能 按钮
         */
        void onClickRightTitleView(@IdRes int id);

        /**
         * 扩展的findViewById
         */
        <T extends android.view.View> T findView(@IdRes int id);
    }

    interface Presenter {

    }

}
