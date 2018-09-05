package com.great.adou.app.utils;

import com.blankj.utilcode.util.SPUtils;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/05  17:45.
 */
public class SPHelper {
    private final static String spName = "adou";

    public static SPUtils getSPUtils() {
        return SPUtils.getInstance(spName);
    }


}
