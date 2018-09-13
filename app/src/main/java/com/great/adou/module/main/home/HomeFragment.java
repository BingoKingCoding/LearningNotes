package com.great.adou.module.main.home;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.great.adou.R;
import com.great.adou.app.base.BaseFragment;
import com.great.adou.app.net.bean.DailyListBean;
import com.great.adou.app.net.bean.HomeListBean;
import com.great.adou.app.utils.BannerImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <首页>
 * Created by WangWB on 2018/09/06  15:10.
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    private int mDistanceY;
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

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        ll_search = contentView.findViewById(R.id.ll_search);
        mRecyclerView = contentView.findViewById(R.id.rv);

        initRecyclerView();
        initSearchBar();
    }

    @Override
    public void onResume() {
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
        View headerView = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.item_home_header, (ViewGroup) mRecyclerView.getParent(), false);
        mBanner = headerView.findViewById(R.id.banner);


        mAdapter = new HomeAdapter(null);
        mAdapter.addHeaderView(headerView);


        mRecyclerView.setAdapter(mAdapter);
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
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    ll_search.setAlpha(alpha);
                } else {
                    //上述虽然判断了滑动距离与toolbar高度相等的情况，但是实际测试时发现，标题栏的背景色
                    //很少能达到完全不透明的情况，所以这里又判断了滑动距离大于toolbar高度的情况，
                    //将标题栏的颜色设置为完全不透明状态
                    ll_search.setBackgroundResource(R.color.colorPrimary);
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
