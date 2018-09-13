package com.great.adou.module.main.home;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.great.adou.app.net.bean.HomeListBean;

import java.util.List;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/13  16:05.
 */
public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeAdapter(List<HomeListBean> data) {
        super(data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeListBean item) {

    }
}
