package com.great.adou.module.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.great.adou.R;
import com.great.adou.app.AppRuntimeWorker;
import com.great.adou.app.base.BaseActivity;
import com.great.adou.app.utils.StatusBarUtil;
import com.great.adou.app.widget.BottomNavigationView;
import com.great.adou.module.main.home.HomeFragment;
import com.great.adou.module.main.me.MeFragment;
import com.great.adou.module.main.welfare.WelfareFragment;

public class MainActivity extends BaseActivity {

    BottomNavigationView mBottomBar;

    private Fragment[] mFragments = new Fragment[4];
    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex");
        }
        init();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    @Override
    protected boolean openLoadingPage() {
        return false;
    }

    private void init() {
        mIsExitApp = true;

        AppRuntimeWorker.initHomeTitle();

        mBottomBar = findViewById(R.id.bottomBar);
        initTab();
    }

    private void initTab() {

        mFragments[0] = HomeFragment.newInstance();
        mFragments[1] = WelfareFragment.newInstance();
        mFragments[2] = WelfareFragment.newInstance();
        mFragments[3] = MeFragment.newInstance();

        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fragment_container, curIndex);


        mBottomBar.setCallback(new BottomNavigationView.Callback() {
            @Override
            public void onTabSelected(int index) {
                switch (index) {
                    case BottomNavigationView.INDEX_HOME:
                        showCurrentFragment(0);
                        break;
                    case BottomNavigationView.INDEX_DASHBOARD:
                        showCurrentFragment(1);
                        break;
                    case BottomNavigationView.INDEX_FAVOURITE:
                        showCurrentFragment(2);
                        break;
                    case BottomNavigationView.INDEX_ME:
                        showCurrentFragment(3);
                        break;
                }
            }

            @Override
            public void onTabReselected(int index) {

            }
        });
        mBottomBar.selectTab(BottomNavigationView.INDEX_HOME);
    }

    private void showCurrentFragment(int index) {
        FragmentUtils.showHide(curIndex = index, mFragments);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("curIndex", curIndex);
    }

}
