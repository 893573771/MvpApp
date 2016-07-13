package com.alex.app.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by alex on 2016/6/29.
 */
public class MovieListBean {

    /**
     * count : 2
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":939930,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"},{"rating":{"max":10,"average":9.4,"stars":"50","min":0},"genres":["剧情","动作","犯罪"],"title":"这个杀手不太冷","casts":[{"alt":"https://movie.douban.com/celebrity/1025182/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/8833.jpg","large":"https://img3.doubanio.com/img/celebrity/large/8833.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/8833.jpg"},"name":"让·雷诺","id":"1025182"},{"alt":"https://movie.douban.com/celebrity/1054454/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/2274.jpg","large":"https://img3.doubanio.com/img/celebrity/large/2274.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/2274.jpg"},"name":"娜塔莉·波特曼","id":"1054454"},{"alt":"https://movie.douban.com/celebrity/1010507/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/104.jpg","large":"https://img3.doubanio.com/img/celebrity/large/104.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/104.jpg"},"name":"加里·奥德曼","id":"1010507"}],"collect_count":907568,"original_title":"Léon","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1031876/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/33301.jpg","large":"https://img3.doubanio.com/img/celebrity/large/33301.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/33301.jpg"},"name":"吕克·贝松","id":"1031876"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p511118051.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p511118051.jpg"},"alt":"https://movie.douban.com/subject/1295644/","id":"1295644"}]
     * title : 豆瓣电影Top250
     */

    public String count;
    public String start;
    public String total;
    public String title;
    /**
     * rating : {"max":10,"average":9.6,"stars":"50","min":0}
     * genres : ["犯罪","剧情"]
     * title : 肖申克的救赎
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
     * collect_count : 939930
     * original_title : The Shawshank Redemption
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
     * year : 1994
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
     * alt : https://movie.douban.com/subject/1292052/
     * id : 1292052
     */

    public List<SubjectsBean> subjects;

    public static MovieListBean objectFromData(String str) {

        return new Gson().fromJson(str, MovieListBean.class);
    }

    public static class SubjectsBean {
        /**
         * max : 10
         * average : 9.6
         * stars : 50
         * min : 0
         */

        public RatingBean rating;
        public String title;
        public String collect_count;
        public String original_title;
        public String subtype;
        public String year;
        /**
         * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
         * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
         * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
         */

        public ImagesBean images;
        public String alt;
        public String id;
        public List<String> genres;
        /**
         * alt : https://movie.douban.com/celebrity/1054521/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
         * name : 蒂姆·罗宾斯
         * id : 1054521
         */

        public List<CastsBean> casts;
        /**
         * alt : https://movie.douban.com/celebrity/1047973/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
         * name : 弗兰克·德拉邦特
         * id : 1047973
         */

        public List<DirectorsBean> directors;

        public static SubjectsBean objectFromData(String str) {

            return new Gson().fromJson(str, SubjectsBean.class);
        }

        public static class RatingBean {
            public String max;
            public double average;
            public String stars;
            public String min;

            public static RatingBean objectFromData(String str) {

                return new Gson().fromJson(str, RatingBean.class);
            }
        }

        public static class ImagesBean {
            public String small;
            public String large;
            public String medium;

            public static ImagesBean objectFromData(String str) {

                return new Gson().fromJson(str, ImagesBean.class);
            }
        }

        public static class CastsBean {
            public String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/17525.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/17525.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/17525.jpg
             */

            public AvatarsBean avatars;
            public String name;
            public String id;

            public static CastsBean objectFromData(String str) {

                return new Gson().fromJson(str, CastsBean.class);
            }

            public static class AvatarsBean {
                public String small;
                public String large;
                public String medium;

                public static AvatarsBean objectFromData(String str) {

                    return new Gson().fromJson(str, AvatarsBean.class);
                }
            }
        }

        public static class DirectorsBean {
            public String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/230.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/230.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/230.jpg
             */

            public AvatarsBean avatars;
            public String name;
            public String id;

            public static DirectorsBean objectFromData(String str) {

                return new Gson().fromJson(str, DirectorsBean.class);
            }

            public static class AvatarsBean {
                public String small;
                public String large;
                public String medium;

                public static AvatarsBean objectFromData(String str) {

                    return new Gson().fromJson(str, AvatarsBean.class);
                }
            }
        }
    }
}
