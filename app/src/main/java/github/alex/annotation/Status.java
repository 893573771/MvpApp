package github.alex.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hasee on 2016/6/21.
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@IntDef(value={Status.DEFAULT, Status.LOADING, Status.SUCCESS, Status.FAIL, Status.EMPTY})
public @interface Status {
    /**
     * 默认
     */
    public static final int DEFAULT = 0;
    /**
     * 主布局
     */
    public static final int LOADING = 1;
    /**
     * 主布局
     */
    public static final int SUCCESS = 2;
    /**
     * 失败布局
     */
    public static final int FAIL = 3;
    /**
     * 空数据布局
     */
    public static final int EMPTY = 4;
    /**
     * 布局文件不存在
     */
    public static final int layoutResIdNo = -1;
    /**
     * 布局文件不存在
     */
    public static final int resIdNo = -1;
}
