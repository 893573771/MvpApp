package com.alex.app.config;

import com.alex.app.model.UserBean;

import java.util.Iterator;
import java.util.Map;

import github.alex.util.LogUtil;

/**
 * 作者：alex
 * 时间：2016/7/27 18:08
 * 博客地址：http://www.jianshu.com/users/c3c4ea133871/subscriptions
 */
public class Util {
    public static void printMap(Map map) {
        if ((map == null) || (map.size() <= 0)) {
            return;
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair != null) {
                if (pair.getValue() instanceof UserBean) {
                    UserBean userBean = (UserBean) pair.getValue();
                    LogUtil.e("key = " + pair.getKey() + " : " + pair.getValue().getClass().getSimpleName() + " = " + userBean.phone + " " + userBean.pwd);
                } else {
                    LogUtil.e("key = " + pair.getKey() + " : " + pair.getValue().getClass().getSimpleName() + " = " + pair.getValue());
                }
            }
            it.remove();
        }
    }
}
