package com.great.adou.module.main.home;


import com.great.adou.app.base.IModel;
import com.great.adou.app.base.IView;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * <契约>
 * Created by wwb on 2017/12/1 17:14.
 */

public interface HomeContract {
    interface View extends IView {
        void refreshView(List<HomeListBean> homeList);
    }

    interface Model extends IModel {
        Observable<DailyListBean> requestDailyList();

        Observable<HotListBean> requestHotList();

        Observable<ThemeListBean> requestThemeList();

        Observable<SectionListBean> requestSectionList();
    }
}
