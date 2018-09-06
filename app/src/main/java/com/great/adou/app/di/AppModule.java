package com.great.adou.app.di;

import android.app.Application;

import com.great.adou.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <dagger2注入>
 * Created by WangWB on 2018/09/06  14:38.
 */
@Module
public class AppModule {

    private App mApp;

    public AppModule(App mApp) {
        this.mApp = mApp;
    }

    @Provides
    @Singleton
    public App provideApp() {
        return mApp;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApp;
    }

}
