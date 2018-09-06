package com.great.adou.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.great.adou.app.widget.LoadingPage;
import com.great.adou.app.widget.ProgressDialog;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Objects;

import javax.inject.Inject;

/**
 * <Fragment基类>
 * Created by WangWB on 2018/09/06  13:57.
 */
public abstract class BaseFragment<P extends IPresenter> extends RxFragment {

    @Inject
    protected P mPresenter;

    public LoadingPage mLoadingPage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate");
//        if (useEventBus()) {//如果要使用eventBus请将此方法返回true
//            EventBus.getDefault().register(this);
//        }
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = getLayoutInflater().inflate(getContentLayoutId(), Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), false);
        onInitContentView(contentView);
        return addLoadingPageIfNeed(contentView);
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

    protected void dismissLoadingDialog() {
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

    protected void showPage(int state) {
        if (mLoadingPage != null) {
            mLoadingPage.showPage(state);
        }
    }


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

}
