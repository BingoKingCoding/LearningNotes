package com.great.adou.app.di;


import com.great.adou.BuildConfig;
import com.great.adou.app.App;
import com.great.adou.app.Constants;
import com.great.adou.app.net.IRepository;
import com.great.adou.app.net.RepositoryImpl;
import com.great.adou.app.utils.FileUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <dagger2网络初始化>
 * Created by wang on 2017/11/3 10:30.
 */
@Module
public class NetworkModule {
    private App mApp;

    public NetworkModule(App app) {
        this.mApp = app;
    }

    @Provides
    @Singleton
    IRepository provideRepository(@Named("gankIo") Retrofit gankIoRetrofit, @Named("zhiHu") Retrofit zhiHuRetrofit, @Named("douBan") Retrofit doubanRetrofit) {
        return new RepositoryImpl(mApp, gankIoRetrofit, zhiHuRetrofit, doubanRetrofit);
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.cache(new Cache(FileUtil.getHttpCacheDir(), Constants.HTTP_CACHE_SIZE));
        //设置超时
        builder.connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }


    @Provides
    @Named("gankIo")
    @Singleton
    Retrofit provideGankIoRetrofit(OkHttpClient client) {
        String baseUrl = Constants.BASE_API_URL_GANKIO;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named("zhiHu")
    @Singleton
    Retrofit provideZhiHuRetrofit(OkHttpClient client) {
        String baseUrl = Constants.BASE_API_URL_ZHIHU;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named("douBan")
    @Singleton
    Retrofit provideDouBanRetrofit(OkHttpClient client) {
        String baseUrl = Constants.BASE_API_URL_DOUBAN;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }


}
