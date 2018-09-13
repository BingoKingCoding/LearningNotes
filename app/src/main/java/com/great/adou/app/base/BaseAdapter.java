package com.great.adou.app.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * <adapter基类>
 * Created by WangWB on 2018/09/13  16:05.
 */
public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {


    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }
}
