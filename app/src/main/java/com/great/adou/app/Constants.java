package com.great.adou.app;

/**
 * <常量>
 * Created by WangWB on 2018/09/05  17:42.
 */
public class Constants {
    public class HttpConstants {
        //干货
        public static final String BASE_API_URL_GANKIO = "http://gank.io/";
        //知乎
        public static final String BASE_API_URL_ZHIHU = "http://news-at.zhihu.com/api/4/";
        //豆瓣电影
        public static final String BASE_API_URL_DOUBAN = "https://api.douban.com/";
        //缓存
        public static final int HTTP_CACHE_SIZE = 20 * 1024 * 1024;
        //okHttp设置
        public static final int HTTP_CONNECT_TIMEOUT = 10;
        public static final int HTTP_READ_TIMEOUT = 20;
    }

    public class SPConstants {
        //第一次打开app的标识
        public static final String SP_IS_FIRST = "is_first";
        //
        public final static String HOME_LIST_IS_CHANGE = "home_list_is_change";
        //
        public final static String HOME_LIST_TITLE = "home_list_title";
    }


}
