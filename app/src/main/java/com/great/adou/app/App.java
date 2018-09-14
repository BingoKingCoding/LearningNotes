package com.great.adou.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.great.adou.R;
import com.great.adou.app.di.AppComponent;
import com.great.adou.app.di.AppModule;
import com.great.adou.app.di.DaggerAppComponent;
import com.great.adou.app.di.NetworkModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * <艺辉我爱你>
 * <p>
 * ***   ***
 * **    *    **
 * **     **
 * ***
 * <p>
 * <p>
 * Created by WangWB on 2018/8/30:23:35.
 * Email:634051075@qq.com
 */
public class App extends Application {

    private AppComponent mAppComponent;
    private static App sApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        initUtils();
        initLog();
        initInjector();
    }

    public static App getApp() {
        return sApp;
    }

    private void initUtils() {
        //https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/README-CN.md
        Utils.init(this);
    }

    private void initLog() {
        //https://github.com/orhanobut/logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("wwb")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }


    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
