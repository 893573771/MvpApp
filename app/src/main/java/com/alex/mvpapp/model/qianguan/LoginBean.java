package com.alex.mvpapp.model.qianguan;

import com.google.gson.Gson;

/**
 * Created by Alex on 2016/6/22.
 */
public class LoginBean {

    /**
     * code : 1
     * data : {"userId":"268479","mobileNo":"13146008029"}
     * message : success
     */

    public String code;
    /**
     * userId : 268479
     * mobileNo : 13146008029
     */

    public DataBean data;
    public String message;

    public static LoginBean objectFromData(String str) {

        return new Gson().fromJson(str, LoginBean.class);
    }

    public static class DataBean {
        public String userId;
        public String mobileNo;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }
    }
}
