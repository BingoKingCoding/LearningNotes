package com.great.adou.app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

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


    private void init() {

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
