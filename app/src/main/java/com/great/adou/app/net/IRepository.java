package com.great.adou.app.net;

import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionChildListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeChildListBean;
import com.great.adou.app.net.bean.ThemeListBean;

import io.reactivex.Observable;

/**
 * <获取数据的接口>
 * Created by WangWB on 2018/09/06  14:37.
 */
public interface IRepository {

    /**
     * 最新日报
     */
    Observable<DailyListBean> requestDailyList();
    /**
     * 主题日报
     */
    Observable<ThemeListBean> requestThemeList();
    /**
     * 主题日报详情
     */
    Observable<ThemeChildListBean> requestThemeChildList(int id);

    /**
     * 专栏日报
     */
    Observable<SectionListBean> requestSectionList();

    /**
     * 热门日报
     */
    Observable<HotListBean> requestHotList();

    /**
     * 专栏日报详情
     */
    Observable<SectionChildListBean> requestSectionChildList(int id);

}
