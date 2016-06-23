package com.alex.mvpapp.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Alex on 2016/6/21.
 */
public class WeatherBean {


    /**
     * code : 1
     * data : {"customerService":[{"kefuId":"qianguan123","kefuName":"qianguan123"},{"kefuId":"qianguan123","kefuName":"qianguan123"}]}
     * message : success
     */

    public String code;
    public DataBean data;
    public String message;

    public static WeatherBean objectFromData(String str) {

        return new Gson().fromJson(str, WeatherBean.class);
    }

    public static class DataBean {
        /**
         * kefuId : qianguan123
         * kefuName : qianguan123
         */

        public List<CustomerServiceBean> customerService;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static class CustomerServiceBean {
            public String kefuId;
            public String kefuName;

            public static CustomerServiceBean objectFromData(String str) {

                return new Gson().fromJson(str, CustomerServiceBean.class);
            }
        }
    }
}
