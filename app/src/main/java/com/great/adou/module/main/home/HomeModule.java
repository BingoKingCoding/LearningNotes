package com.great.adou.module.main.home;

import com.great.adou.app.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/14  11:25.
 */
@Module
public class HomeModule {
    private HomeContract.View mView;

    public HomeModule(HomeContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @ActivityScope
    HomeContract.View provideView() {
        return this.mView;
    }

    @ActivityScope
    @Provides
    HomeContract.Model provideModel(HomeModel model) {
        return model;
    }
}
