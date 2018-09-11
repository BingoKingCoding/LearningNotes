package com.great.adou.module.main.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.great.adou.R;
import com.great.adou.app.base.BaseFragment;

/**
 * <首页>
 * Created by WangWB on 2018/09/06  15:10.
 */
public class HomeFragment extends BaseFragment {


    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean openLoadingPage() {
        return false;
    }
}
