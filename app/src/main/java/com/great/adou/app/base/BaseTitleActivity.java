package com.great.adou.app.base;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.great.adou.R;

/**
 * <带有标题栏>
 * Created by WangWB on 2018/09/04  17:48.
 */
public class BaseTitleActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView tv_title;
    protected TextView toolbar_right_action;

    @Override
    protected int onCreateTitleViewResId() {
        return R.layout.layout_toolbar;
    }

    @Override
    protected void onInitTitleView(View titleView) {
        super.onInitTitleView(titleView);
        toolbar = titleView.findViewById(R.id.toolbar);
        tv_title = titleView.findViewById(R.id.toolbar_title);
        toolbar_right_action = titleView.findViewById(R.id.toolbar_right_action);

        //设置相关默认操作
        setTitleNavigationIcon(R.drawable.ic_arrow_back_24dp);
        setTitleBgColor(R.color.base_title_color);

        //左边Navigation Button监听回调
        toolbar.setNavigationOnClickListener(this::onClickNavigationAction);
        toolbar_right_action.setOnClickListener(this::OnClickRightAction);
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
    }


    /**
     * 设置中间标题
     */
    public void setTitleText(String title) {
        if (tv_title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 设置右边单个按钮
     */
    public void setTitleRightActionText(String text) {
        if (toolbar_right_action != null) {
            toolbar_right_action.setText(text);
        }
    }

    /**
     * 设置标题栏背景颜色
     */
    protected void setTitleBgColor(@ColorRes int color) {
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(color));
        }
    }


    /**
     * 设置左边标题图标
     */
    public void setTitleNavigationIcon(@DrawableRes int iconRes) {
        if (toolbar != null) {
            toolbar.setNavigationIcon(iconRes);
        }
    }


    /**
     * 隐藏标题栏
     */
    protected void hideToolbar() {
        if (toolbar.getVisibility() == View.VISIBLE)
            toolbar.setVisibility(View.GONE);
    }

    /**
     * 不显示 NavigationButton
     */
    public void hideTitleNavigationButton() {
        toolbar.setNavigationIcon(null);
    }


    /**
     * Navigation Button点击回调，默认回退销毁页面，其他操作子类可重写
     */
    protected void onClickNavigationAction(View view) {
        super.onBackPressed();
    }

    /**
     * 右边操作点击回调，其他操作子类可重写
     */
    protected void OnClickRightAction(View view) {

    }

}
