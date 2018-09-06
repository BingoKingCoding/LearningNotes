package com.great.adou.app.widget.listener;

import android.view.View;

/**
 * <ViewPager中点击回调>
 * Created by WangWB on 2018/06/05  11:34.
 */

public interface DItemClickCallback<T> {
    void onItemClick(int position, T item, View view);
}
