package github.alex.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static github.alex.model.StatusLayoutModel.layoutStatusDefault;
import static github.alex.model.StatusLayoutModel.layoutStatusFail;
import static github.alex.model.StatusLayoutModel.layoutStatusLoading;
import static github.alex.model.StatusLayoutModel.layoutStatusSuccess;
import static github.alex.model.StatusLayoutModel.layoutStatusEmpty;

/**
 * Created by hasee on 2016/6/21.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@IntDef(value={layoutStatusDefault,layoutStatusLoading,layoutStatusSuccess, layoutStatusFail, layoutStatusEmpty})
public @interface LayoutStatus {

}
