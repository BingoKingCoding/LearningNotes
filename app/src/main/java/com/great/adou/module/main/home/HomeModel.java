package com.great.adou.module.main.home;

import com.great.adou.app.base.BaseModel;
import com.great.adou.app.di.ActivityScope;
import com.great.adou.app.net.IRepository;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeListBean;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/14  11:18.
 */
@ActivityScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepository repository) {
        super(repository);
    }

    @Override
    public Observable<DailyListBean> requestDailyList() {
        return mRepository.requestDailyList();
    }

    @Override
    public Observable<HotListBean> requestHotList() {
        return mRepository.requestHotList();
    }

    @Override
    public Observable<ThemeListBean> requestThemeList() {
        return mRepository.requestThemeList();
    }

    @Override
    public Observable<SectionListBean> requestSectionList() {
        return mRepository.requestSectionList();
    }
}
