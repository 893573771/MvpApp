package github.alex.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static github.alex.annotation.LayoutStatus.layoutStatusDefault;
import static github.alex.annotation.LayoutStatus.layoutStatusEmpty;
import static github.alex.annotation.LayoutStatus.layoutStatusFail;
import static github.alex.annotation.LayoutStatus.layoutStatusLoading;
import static github.alex.annotation.LayoutStatus.layoutStatusSuccess;

/**
 * Created by hasee on 2016/6/21.
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@IntDef(value={layoutStatusDefault,layoutStatusLoading,layoutStatusSuccess, layoutStatusFail, layoutStatusEmpty})
public @interface LayoutStatus {
    /**
     * 默认
     */
    public static final int layoutStatusDefault = 0;
    /**
     * 主布局
     */
    public static final int layoutStatusLoading = 1;
    /**
     * 主布局
     */
    public static final int layoutStatusSuccess = 2;
    /**
     * 失败布局
     */
    public static final int layoutStatusFail = 3;
    /**
     * 空数据布局
     */
    public static final int layoutStatusEmpty = 4;
    /**
     * 布局文件不存在
     */
    public static final int layoutResIdNo = -1;
    /**
     * 布局文件不存在
     */
    public static final int resIdNo = -1;
}
