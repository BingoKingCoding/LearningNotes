package com.great.adou.app.base;

import com.great.adou.app.event.EventBusUtil;
import com.great.adou.app.net.HttpCallback;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wang on 2017/11/2 17:32.
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mRootView;


    private HttpCallback mHttpCallback;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }


    @Override
    public void onStart() {
        if (useEventBus())//如果要使用eventBus请将此方法返回true
            EventBusUtil.register(this);//注册eventBus
    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用eventBus请将此方法返回true
            EventBusUtil.unregister(this);//解除注册eventBus
        unDispose();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    public boolean useEventBus() {
        return true;
    }


    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有disposable放入,集中处理
    }

    private void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }

}
