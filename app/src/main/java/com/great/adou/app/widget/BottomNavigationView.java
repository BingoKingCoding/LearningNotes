package com.great.adou.app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.great.adou.R;
import com.great.adou.app.utils.select.SelectViewManager;

/**
 * <底部导航栏>
 * Created by WangWB on 2018/09/06  15:23.
 */
public class BottomNavigationView extends AppView {
    public BottomNavigationView(@NonNull Context context) {
        super(context);
        init();
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public static final int INDEX_HOME = 0;
    public static final int INDEX_DASHBOARD = 1;
    public static final int INDEX_FAVOURITE = 2;
    public static final int INDEX_ME = 3;


    private TabMainMenuView mTabHome, mTabDashboard, mTabFavourite, mTabMe;

    private SelectViewManager<TabMainMenuView> mSelectionManager = new SelectViewManager<>();


    private void init() {
        setContentView(R.layout.view_main_tab);

        mTabHome = findViewById(R.id.view_tab_home);
        mTabDashboard = findViewById(R.id.view_tab_dashboard);
        mTabFavourite = findViewById(R.id.view_tab_favourite);
        mTabMe = findViewById(R.id.view_tab_me);

        initTabs();
        initSelectManager();

    }


    private void initTabs() {
        changeBottomNavigation(mTabHome, R.drawable.icon_main_find_normal,
                R.drawable.icon_main_find_selected, "首页");
        changeBottomNavigation(mTabDashboard, R.drawable.icon_main_channel_normal,
                R.drawable.icon_main_channel_selected, "福利");
        changeBottomNavigation(mTabFavourite, R.drawable.icon_main_make_normal,
                R.drawable.icon_main_make_selected, "收藏");
        changeBottomNavigation(mTabMe, R.drawable.icon_main_mine_normal,
                R.drawable.icon_main_mine_selected, "我的");
    }

    public void changeBottomNavigation(TabMainMenuView tabMainMenuView, int drawableNormal, int drawableSelect, String textName) {
        tabMainMenuView.configImage()
                .setImageResIdNormal(drawableNormal)
                .setImageResIdSelected(drawableSelect)
                .setSelected(false);
        tabMainMenuView.getTabNameTextView().setText(textName);
        tabMainMenuView.configTextView()
                .setTextColorNormal(getResources().getColor(R.color.text_333333))
                .setTextColorSelected(getResources().getColor(R.color.colorPrimary))
                .setSelected(false);
    }

    private void initSelectManager() {
        mSelectionManager.setReSelectCallback((index, item) -> getCallback().onTabReselected(index));

        mSelectionManager.addSelectCallback(new SelectViewManager.SelectCallback<TabMainMenuView>() {
            @Override
            public void onNormal(int index, TabMainMenuView item) {
            }

            @Override
            public void onSelected(int index, TabMainMenuView item) {
                getCallback().onTabSelected(index);
            }
        });
        mSelectionManager.setItems(mTabHome, mTabDashboard, mTabFavourite, mTabMe);
    }

    public void selectTab(int index) {
        mSelectionManager.performClick(index);
    }


    public interface Callback {
        void onTabSelected(int index);

        void onTabReselected(int index);
    }

    private Callback mCallback;

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    private Callback getCallback() {
        if (mCallback == null) {
            mCallback = new Callback() {
                @Override
                public void onTabSelected(int index) {
                }

                @Override
                public void onTabReselected(int index) {
                }
            };
        }
        return mCallback;
    }

}
