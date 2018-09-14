package com.great.adou.app.base;

import com.great.adou.app.net.IRepository;

/**
 * <请描述这个类是干什么的>
 * Created by WangWB on 2018/09/14  11:17.
 */
public class BaseModel{
    protected IRepository mRepository;//用于管理网络请求层,以及数据缓存层

    public BaseModel(IRepository repository) {
        this.mRepository = repository;
    }

    public void onDestroy() {
        mRepository = null;
    }
}
