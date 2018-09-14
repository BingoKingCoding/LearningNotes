package com.great.adou.module.main.home;

import com.great.adou.app.di.ActivityScope;
import com.great.adou.app.di.AppComponent;

import dagger.Component;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/14  11:24.
 */
@ActivityScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
