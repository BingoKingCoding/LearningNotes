package com.great.adou.module.main.home;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.great.adou.R;
import com.great.adou.app.Constants;
import com.great.adou.app.base.BaseFragment;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.utils.BannerImageLoader;
import com.great.adou.app.utils.SPHelper;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <首页>
 * Created by WangWB on 2018/09/06  15:10.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private Double mDistanceY = 0.0;
    private HomeAdapter mAdapter;
    private Banner mBanner;

    private List<HomeListBean> mHomeList;

    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        fragment.setArguments(bundle);
        return fragment;
    }

    private LinearLayout ll_search;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view_fake_status_bar;
    private View view_bg_search;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        ll_search = findViewById(R.id.ll_search);
        mRecyclerView = contentView.findViewById(R.id.rv);
        mSwipeRefreshLayout = contentView.findViewById(R.id.srl_refresh);
        view_fake_status_bar = findViewById(R.id.fake_status_bar);
        view_bg_search = findViewById(R.id.view_bg_search);

        Logger.i("view_alpha : " + view_bg_search.getAlpha());


        initSwipeRefreshLayout();
        initRecyclerView();
        initSearchBar();
    }

    private void initSwipeRefreshLayout() {

        Objects.requireNonNull(mSwipeRefreshLayout).setColorSchemeResources(android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
    }

    @Override
    protected void initComponent() {
        super.initComponent();
        DaggerHomeComponent.builder()
                .appComponent(getAppComponent())
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.requestHomeListData();
    }

    @Override
    public void onResume() {
        SPUtils spUtils = SPHelper.getSPUtils();
        if (spUtils.getBoolean(Constants.SPConstants.HOME_LIST_IS_CHANGE)) {
            mHomeList = mPresenter.getListHome();
            if (mAdapter != null) {
                mAdapter.setNewData(mHomeList);
                spUtils.put(Constants.SPConstants.HOME_LIST_IS_CHANGE, false);
            }
        }

        if (mBanner != null) {
            mBanner.start();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        View headerView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.item_home_header, (ViewGroup) mRecyclerView.getParent(), false);
        mBanner = headerView.findViewById(R.id.banner);

        mAdapter = new HomeAdapter(null);
        mAdapter.addHeaderView(headerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void refresh() {
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }

        mPresenter.requestHomeListData();
    }

    /**
     * Desc:设置搜索框透明度变化
     *
     * @author wwb
     */
    private void initSearchBar() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = ll_search.getBottom();
                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (toolbarHeight != 0 && mDistanceY <= toolbarHeight) {
                    double d = mDistanceY / toolbarHeight;
                    int i = Double.valueOf(d * 255).intValue();    //i 有可能小于0
//                    view_bg_search.setAlpha(Math.max(i, 0));   // 0~255 透明度
                    view_fake_status_bar.setAlpha((float) Math.max(d, 0));
                    view_bg_search.setAlpha((float) Math.max(d, 0));

                    Logger.i("alpha :" + (float) Math.max(d, 0));
                }
            }
        });
    }


    @Override
    protected void retryRequestData() {
        mPresenter.requestHomeListData();
    }


    @Override
    public void refreshView(List<HomeListBean> homeList) {
        mHomeList = homeList;
        List<DailyListBean.TopStoriesBean> topStoriesList = mPresenter.getTopStoriesList();
        if (mHomeList.size() == 12) {
            initBanner(topStoriesList);
            mAdapter.setNewData(mHomeList);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    private void initBanner(final List<DailyListBean.TopStoriesBean> topStoriesList) {
        mBanner.startAutoPlay();
        mBanner.setDelayTime(3000);
        List<String> imageList = new ArrayList<>();
        for (DailyListBean.TopStoriesBean topStoriesBean : topStoriesList) {
            imageList.add(topStoriesBean.getImage());
        }
        mBanner.setImages(imageList).setImageLoader(new BannerImageLoader()).start();
        mBanner.setOnBannerListener(position ->
        {
            int id = topStoriesList.get(position).getId();
//            startZhiHuDetailActivity(id, mBanner);
        });
    }


}
