package com.great.adou.app.di;

import android.app.Application;


import com.great.adou.app.net.IRepository;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * <dagger2>
 * Created by wang on 2017/11/3 11:17.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent
{
    Application application();

    OkHttpClient okHttpClient();

    IRepository repository();
}
