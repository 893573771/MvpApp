package com.alex.mvpapp.model;

/**
 * Created by Alex on 2016/6/21.
 */
public class WeatherBean {
    /**
     * city : 北京
     * cityid : 101010100
     * temp : 10
     * WD : 东南风
     * WS : 2级
     * SD : 26%
     * WSE : 2
     * time : 10:25
     * isRadar : 1
     * Radar : JC_RADAR_AZ9010_JB
     * njd : 暂无实况
     * qy : 1012
     */

    public WeatherinfoBean weatherinfo;
 

    public static class WeatherinfoBean {
        public String city;
        public String cityid;
        public String temp;
        public String WD;
        public String WS;
        public String SD;
        public String WSE;
        public String time;
        public String isRadar;
        public String Radar;
        public String njd;
        public String qy;
        
    }
}
