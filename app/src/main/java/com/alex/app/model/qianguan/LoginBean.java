package com.alex.app.model.qianguan;

import com.google.gson.Gson;

/**
 * Created by Alex on 2016/6/22.
 */
public class LoginBean {


    /**
     * pwd : 123123
     * name : Alex
     */

    public String pwd;
    public String name;

    public static LoginBean objectFromData(String str) {

        return new Gson().fromJson(str, LoginBean.class);
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
