package com.great.adou.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.great.adou.app.App;
import com.great.adou.app.di.AppComponent;
import com.great.adou.app.event.Event;
import com.great.adou.app.event.EventBusUtil;
import com.great.adou.app.widget.LoadingPage;
import com.great.adou.app.widget.ProgressDialog;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import javax.inject.Inject;

import static android.view.View.NO_ID;

/**
 * <Fragment基类>
 * Created by WangWB on 2018/09/06  13:57.
 */
public abstract class BaseFragment<P extends IPresenter> extends RxFragment {

    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;
    protected View contentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = getLayoutInflater().inflate(getContentLayoutId(), Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), false);

        onInitContentView(contentView);
        return addLoadingPageIfNeed(contentView);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    protected void initData() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    private View addLoadingPageIfNeed(View contentView) {
        if (openLoadingPage()) {
            mLoadingPage = new LoadingPage(Objects.requireNonNull(getActivity())) {
                @Override
                public View createSuccessView() {
                    return contentView;
                }

                @Override
                protected void retryRequestData() {
                    BaseFragment.this.retryRequestData();
                }
            };
            return mLoadingPage;
        }
        return contentView;
    }

    /**
     * Desc:加载布局开关
     *
     * @author wwb
     */
    protected boolean openLoadingPage() {
        return true;
    }

    /**
     * Desc:初始化控件
     *
     * @author wwb
     */
    protected void onInitContentView(View contentView) {

    }


    //----------------------进度框  by WangWB -----------------------------
    protected static final String MESSAGE_LOADING = "请稍候..";

    private ProgressDialog mLoadingDialog;

    public void showLoadingDialog() {
        showLoadingDialog(getActivity(), MESSAGE_LOADING, true);
    }

    protected void showLoadingDialog(boolean cancelable) {
        showLoadingDialog(getActivity(), MESSAGE_LOADING, cancelable);
    }

    public void showLoadingDialog(String msg) {
        showLoadingDialog(getActivity(), msg, true);
    }


    protected void showLoadingDialog(String msg, boolean cancelable) {
        showLoadingDialog(getActivity(), msg, cancelable);
    }

    protected void showLoadingDialog(Activity activity, String msg, boolean cancelable) {
        dismissLoadingDialog();
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(activity);
        }
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setTextContent(TextUtils.isEmpty(msg) ? MESSAGE_LOADING : msg);
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    //----------------------进度框  by WangWB -----------------------------


    public void showSuccessPage() {
        showPage(LoadingPage.STATE_SUCCESS);
    }

    public void showErrorPage() {
        showPage(LoadingPage.STATE_ERROR);
    }

    public void showErrorPage(String errorDesc) {
        if (mLoadingPage != null) {
            mLoadingPage.setTextErrorDesc(errorDesc);
            showErrorPage();
        }
    }

    protected void showPage(int state) {
        if (mLoadingPage != null) {
            mLoadingPage.showPage(state);
        }
    }

//----------------------EventBus  by WangWB -----------------------------

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            onReceiveEvent(event);
        }
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            onReceiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void onReceiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void onReceiveStickyEvent(Event event) {

    }

    //----------------------EventBus  by WangWB -----------------------------


    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected void retryRequestData() {
    }

    /**
     * 2
     * 网络请求成功去加载布局
     */
    protected abstract int getContentLayoutId();


    protected void initComponent() {
    }

    public void finish() {
        Objects.requireNonNull(getActivity()).finish();
    }

    protected AppComponent getAppComponent() {
        return App.getApp().getAppComponent();
    }

    @Nullable
    protected final <V extends View> V findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }
}
