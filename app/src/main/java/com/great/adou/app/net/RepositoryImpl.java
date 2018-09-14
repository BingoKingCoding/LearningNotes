package com.great.adou.app.net;

import android.content.Context;


import com.great.adou.app.net.apiservice.DoubanService;
import com.great.adou.app.net.apiservice.GankIoService;
import com.great.adou.app.net.apiservice.ZhiHuService;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionChildListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeChildListBean;
import com.great.adou.app.net.bean.ThemeListBean;

import io.reactivex.Observable;
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

    @Override
    public Observable<DailyListBean> requestDailyList()
    {
        return mZhiHuService.requestDailyList();
    }

    @Override
    public Observable<ThemeListBean> requestThemeList()
    {
        return mZhiHuService.requestThemeList();
    }

    @Override
    public Observable<ThemeChildListBean> requestThemeChildList(int id)
    {
        return mZhiHuService.requestThemeChildList(id);
    }

    @Override
    public Observable<SectionListBean> requestSectionList()
    {
        return mZhiHuService.requestSectionList();
    }

    @Override
    public Observable<SectionChildListBean> requestSectionChildList(int id)
    {
        return mZhiHuService.requestSectionChildList(id);
    }

    @Override
    public Observable<HotListBean> requestHotList()
    {
        return mZhiHuService.requestHotList();
    }
}
