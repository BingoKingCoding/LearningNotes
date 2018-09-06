package com.great.adou.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * <懒加载>
 * Created by adou on 2017/11/2:20:22.
 * <p>
 * Email:634051075@qq.com
 */

public abstract class BaseLazyFragment<P extends IPresenter> extends BaseFragment<P> {

    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareRequestData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareRequestData();
        }
    }

    public void prepareRequestData() {
        prepareRequestData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareRequestData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            requestData();
            isDataInitiated = true;
        }
    }

    /**
     * 懒加载
     */
    public abstract void requestData();

}
