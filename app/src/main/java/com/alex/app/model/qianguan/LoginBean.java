package com.alex.app.model.qianguan;

import com.google.gson.Gson;

/**
 * Created by Alex on 2016/6/22.
 */
public class LoginBean {


    /**
     * phone : 13146008029
     * uid : uid13146008029
     * name : Alex
     */

    public String phone;
    public String uid;
    public String name;

    public static LoginBean objectFromData(String str) {
        return new Gson().fromJson(str, LoginBean.class);
    }
}
