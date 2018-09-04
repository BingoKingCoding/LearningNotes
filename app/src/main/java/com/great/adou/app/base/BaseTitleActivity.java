package com.great.adou.app.base;

import android.view.View;

import com.great.adou.R;

/**
 * <带有标题栏>
 * Created by WangWB on 2018/09/04  17:48.
 */
public class BaseTitleActivity extends BaseActivity {

    @Override
    protected int onCreateTitleViewResId() {
        return R.layout.layout_toolbar;
    }

    @Override
    protected void onInitTitleView(View titleView) {
        super.onInitTitleView(titleView);
//        StatusBarUtils.setGodModel(this, R.color.white);
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        contentView.setFitsSystemWindows(true);
    }


}
