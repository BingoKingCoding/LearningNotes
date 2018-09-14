package com.great.adou.app;

import com.blankj.utilcode.util.SPUtils;
import com.great.adou.app.utils.SPHelper;

/**
 * <APP运行时处理一些事务>
 * Created by WangWB on 2018/09/14  10:25.
 */
public class AppRuntimeWorker {

    public static void initHomeTitle() {
        //初始化首页栏目顺序
        SPUtils spUtils = SPHelper.getSPUtils();
        if (!spUtils.getBoolean(Constants.SPConstants.HOME_LIST_IS_CHANGE)) {
            spUtils.put(Constants.SPConstants.HOME_LIST_TITLE, "知乎日报&&知乎热门&&知乎主题&&知乎专栏&&");
            spUtils.put(Constants.SPConstants.HOME_LIST_IS_CHANGE, true);
        }
    }

}
