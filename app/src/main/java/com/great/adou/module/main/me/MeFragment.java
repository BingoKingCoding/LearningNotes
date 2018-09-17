package com.great.adou.module.main.me;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.great.adou.R;
import com.great.adou.app.base.BaseFragment;

/**
 * <个人中心>
 * Created by WangWB on 2018/09/13  14:11.
 */
public class MeFragment extends BaseFragment {

    public static Fragment newInstance() {
        MeFragment fragment = new MeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
    }

    @Override
    protected void initData() {
        super.initData();
        showSuccessPage();
    }
}
