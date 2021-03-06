package com.great.adou.app.base;

/**
 * Created by wang on 2017/11/2 17:29.
 */

public interface IPresenter {
    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 Activity#onDestroy() 时会默认调用 { IPresenter#onDestroy()}
     */
    void onDestroy();

}
