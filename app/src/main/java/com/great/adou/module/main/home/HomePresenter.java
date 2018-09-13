package com.great.adou.module.main.home;

import android.support.annotation.NonNull;

import com.great.adou.app.base.BasePresenter;
import com.great.adou.app.net.HttpCallback;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/13  17:20.
 */
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    private List<HomeListBean> homeList = new ArrayList<>();

    private List<DailyListBean.TopStoriesBean> topStoriesList = new ArrayList<>();


    public void requestHomeListData() {
        requestDailyData();
    }

    private void requestDailyData() {

        mModel.requestDailyList().compose(RxUtils.io2Main())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new HttpCallback<DailyListBean>() {
                    @Override
                    public void onSuccess(DailyListBean data) {
                        topStoriesList = data.getTop_stories();
                        List<DailyListBean.StoriesBean> storiesBeanList = data.getStories();
                        setTitle("知乎日报");
                        for (int i = 0; i < 3; i++) {
                            HomeListBean homeListBean = setType(1);
                            homeListBean.setDailyList(storiesBeanList.get(i));
                            homeList.add(homeListBean);
                        }
                        requestHotList();
                    }

                    @Override
                    public void onFail() {
                        mRootView.showErrorPage();
                    }
                });
    }

    private void requestHotList() {

    }

    public List<DailyListBean.TopStoriesBean> getTopStoriesList() {
        return new ArrayList<>();
    }


    private void setTitle(String title) {
        HomeListBean homeListBean = setType(0);
        homeListBean.setTitle(title);
        homeList.add(homeListBean);
    }

    @NonNull
    private HomeListBean setType(int type) {
        HomeListBean homeListBean = new HomeListBean();
        homeListBean.setType(type);
        return homeListBean;
    }


}
