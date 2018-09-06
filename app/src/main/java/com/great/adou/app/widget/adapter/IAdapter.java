package com.great.adou.app.widget.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <PageAdapter等需要实现>
 * Created by WangWB on 2018/06/05  11:24.
 */

public interface IAdapter<T> {
    Activity getActivity();

    View inflate(int resource, ViewGroup root);

    boolean isPositionLegal(int position);

    T getData(int position);

    int getDataCount();

    int indexOf(T model);

    void updateData(List<T> listModel);

    void setData(List<T> listModel);

    List<T> getData();

    void appendData(T model);

    void appendData(List<T> list);

    void removeData(T model);

    T removeData(int position);

    void insertData(int position, T model);

    void insertData(int position, List<T> list);

    void updateData(int position, T model);

    void updateData(int position);

    void clearData();
}
