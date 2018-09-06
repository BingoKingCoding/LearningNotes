package com.great.adou.app.net;

import android.content.Context;


import com.great.adou.app.net.apiservice.DoubanService;
import com.great.adou.app.net.apiservice.GankIoService;
import com.great.adou.app.net.apiservice.ZhiHuService;

import retrofit2.Retrofit;

/**
 * <获取数据具体实现>
 * Created by adou on 2017/11/2:22:29.
 */

public class RepositoryImpl implements IRepository
{
    private GankIoService mGankIoService;
    private ZhiHuService mZhiHuService;
    private DoubanService mDoubanService;
    private Context mContext;

    public RepositoryImpl(Context context, Retrofit gankIoRetrofit, Retrofit zhiHuRetrofit, Retrofit doubanRetrofit)
    {
        this.mContext = context;
        this.mGankIoService = gankIoRetrofit.create(GankIoService.class);
        this.mZhiHuService = zhiHuRetrofit.create(ZhiHuService.class);
        this.mDoubanService = doubanRetrofit.create(DoubanService.class);
    }






}
