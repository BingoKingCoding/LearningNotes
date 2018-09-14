package com.great.adou.module.main.home;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.great.adou.app.Constants;
import com.great.adou.app.base.BasePresenter;
import com.great.adou.app.net.HttpCallback;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.net.bean.HotListBean;
import com.great.adou.app.net.bean.SectionListBean;
import com.great.adou.app.net.bean.ThemeListBean;
import com.great.adou.app.utils.RxUtils;
import com.great.adou.app.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.functions.Action;

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


    public List<HomeListBean> getListZhiHu()
    {
        List<HomeListBean> newHomeList = new ArrayList<>();
        int daily = 0;
        int hot = 0;
        int theme = 0;
        int section = 0;
        SPUtils spUtils = SPUtils.getInstance();
        String homeListString = spUtils.getString(Constants.SPConstants.HOME_LIST_TITLE);
        String[] split = homeListString.split("&&");
        //for循环确定位置
        for (int i = 0; i < split.length; i++)
        {
            if ("知乎日报".equals(split[i]))
            {
                daily = i + 1;
                continue;
            }
            if ("知乎热门".equals(split[i]))
            {
                hot = i + 1;
                continue;
            }
            if ("知乎主题".equals(split[i]))
            {
                theme = i + 1;
                continue;
            }
            if ("知乎专栏".equals(split[i]))
            {
                section = i + 1;
            }
        }
        //添加标题
        for (int y = 1; y <= 4; y++)
        {
            if (daily == y)
            {
                checkAddToNewHomeList("知乎日报", 1, newHomeList);
                continue;
            }
            if (hot == y)
            {
                checkAddToNewHomeList("知乎热门", 2, newHomeList);
                continue;
            }
            if (theme == y)
            {
                checkAddToNewHomeList("知乎主题", 3, newHomeList);
                continue;
            }
            if (section == y)
            {
                checkAddToNewHomeList("知乎专栏", 4, newHomeList);
            }
        }
        return newHomeList;
    }

    private void checkAddToNewHomeList(String title, int type, List<HomeListBean> newHomeList)
    {
        for (int i = 1; i <= homeList.size(); i++)
        {
            if (!TextUtils.isEmpty(title) && title.equals(homeList.get(i - 1).getTitle()))
            {
                newHomeList.add(homeList.get(i - 1));
            }

            if (homeList.get(i - 1).getType() == type)
            {
                newHomeList.add(homeList.get(i - 1));
            }
        }
    }

    private List<DailyListBean.TopStoriesBean> topStoriesList = new ArrayList<>();

    public List<DailyListBean.TopStoriesBean> getTopStoriesList()
    {
        return topStoriesList;
    }

    public void requestHomeListData() {
        requestDailyData();
    }

    private void requestDailyData() {

        mModel.requestDailyList().compose(RxUtils.io2Main())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .doAfterTerminate(() -> mRootView.setRefreshing(false))
                .subscribe(new HttpCallback<DailyListBean>() {
                    @Override
                    public void onSuccess(DailyListBean data) {
                        topStoriesList = data.getTop_stories();
                        List<DailyListBean.StoriesBean> storiesBeanList = data.getStories();
                        setTitle("知乎日报");
                        for (int i = 0; i < 3; i++)
                        {
                            HomeListBean homeListBean = setType(1);
                            homeListBean.setDailyList(storiesBeanList.get(i));
                            homeList.add(homeListBean);
                        }
                        requestHotList();
                    }

                    @Override
                    public void onFail(String error) {
                        mRootView.showErrorPage(error);
                    }
                });
    }

    private void requestHotList() {
        mModel.requestHotList()
                .compose(RxUtils.io2Main())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new HttpCallback<HotListBean>() {
                    @Override
                    public void onSuccess(HotListBean data) {
                        List<HotListBean.RecentBean> recent = data.getRecent();
                        setTitle("知乎热门");
                        List<HotListBean.RecentBean> hotList = new ArrayList<>();
                        List<HotListBean.RecentBean> hotList2 = new ArrayList<>();
                        for (int i = 0; i < 6; i++)
                        {
                            if (i < 3)
                            {
                                hotList.add(recent.get(i));
                            } else
                            {
                                hotList2.add(recent.get(i));
                            }
                        }
                        HomeListBean homeListBean = setType(2);
                        homeListBean.setHotList(hotList);
                        homeList.add(homeListBean);
                        HomeListBean homeListBean2 = setType(2);
                        homeListBean2.setHotList(hotList2);
                        homeList.add(homeListBean2);
                        requestThemeList();
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
    }

    private void requestThemeList() {
        mModel.requestThemeList()
                .compose(RxUtils.io2Main())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new HttpCallback<ThemeListBean>() {
                    @Override
                    public void onSuccess(ThemeListBean data) {
                        List<ThemeListBean.OthersBean> others = data.getOthers();
                        setTitle("知乎主题");
                        List<ThemeListBean.OthersBean> themeList = new ArrayList<>();
                        List<ThemeListBean.OthersBean> themeList2 = new ArrayList<>();
                        int random = new Random().nextInt(4);
                        for (int i = random; i < random + 4; i++)
                        {
                            if (i < random + 2)
                            {
                                themeList.add(others.get(i));
                            } else
                            {
                                themeList2.add(others.get(i));
                            }
                        }
                        HomeListBean homeListBean = setType(3);
                        homeListBean.setThemeList(themeList);
                        homeList.add(homeListBean);
                        HomeListBean homeListBean2 = setType(3);
                        homeListBean2.setThemeList(themeList2);
                        homeList.add(homeListBean2);
                        fetchSectionList();
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });

    }

    private void fetchSectionList() {
        mModel.requestSectionList()
                .compose(RxUtils.io2Main())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new HttpCallback<SectionListBean>() {
                    @Override
                    public void onSuccess(SectionListBean data) {
                        List<SectionListBean.DataBean> data1 = data.getData();
                        setTitle("知乎专栏");
                        List<SectionListBean.DataBean> sectionList = new ArrayList<>();
                        int random = new Random().nextInt(4);
                        for (int i = random; i < random + 3; i++) {
                            sectionList.add(data1.get(i));
                        }
                        HomeListBean homeListBean = setType(4);
                        homeListBean.setSectionList(sectionList);
                        homeList.add(homeListBean);

                        mRootView.showSuccessPage();
                        mRootView.refreshView(getListHome());
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
    }


    public List<HomeListBean> getListHome() {
        List<HomeListBean> newHomeList = new ArrayList<>();
        int daily = 0;
        int hot = 0;
        int theme = 0;
        int section = 0;
        SPUtils spUtils = SPHelper.getSPUtils();

        String homeListString = spUtils.getString(Constants.SPConstants.HOME_LIST_TITLE);

        String[] split = homeListString.split("&&");
        //for循环确定位置
        for (int i = 0; i < split.length; i++) {
            if ("知乎日报".equals(split[i])) {
                daily = i + 1;
                continue;
            }
            if ("知乎热门".equals(split[i])) {
                hot = i + 1;
                continue;
            }
            if ("知乎主题".equals(split[i])) {
                theme = i + 1;
                continue;
            }
            if ("知乎专栏".equals(split[i])) {
                section = i + 1;
            }
        }
        //添加标题
        for (int y = 1; y <= 4; y++) {
            if (daily == y) {
                checkAddToNewHomeList("知乎日报", 1, newHomeList);
                continue;
            }
            if (hot == y) {
                checkAddToNewHomeList("知乎热门", 2, newHomeList);
                continue;
            }
            if (theme == y) {
                checkAddToNewHomeList("知乎主题", 3, newHomeList);
                continue;
            }
            if (section == y) {
                checkAddToNewHomeList("知乎专栏", 4, newHomeList);
            }
        }
        return newHomeList;
    }

    private void setTitle(String title)
    {
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
