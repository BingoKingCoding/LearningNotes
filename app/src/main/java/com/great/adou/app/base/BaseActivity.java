package com.great.adou.app.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.great.adou.R;
import com.great.adou.app.utils.CollectionUtil;
import com.great.adou.app.utils.StatusBarUtil;
import com.great.adou.app.widget.LoadingPage;
import com.great.adou.app.widget.ProgressDialog;
import com.trello.rxlifecycle2.components.RxActivity;

import java.util.Collection;

/**
 * <所有activity的基类>
 * Created by WangWB on 2018/9/3:22:08.
 * Email:634051075@qq.com
 */
public class BaseActivity extends RxActivity {


    /**
     * 触摸返回键是否退出App
     */
    protected boolean mIsExitApp = false;
    protected long mExitTime = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        setupActivityComponent();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void setContentView(int layoutResID) {
        View contentView = this.getLayoutInflater().inflate(layoutResID, this.findViewById(android.R.id.content), false);
        this.setContentView(contentView);
    }


    public void setContentView(View view) {
        View loadingContentView = addLoadingPageIfNeed(view);

        View contentView = addTitleViewIfNeed(loadingContentView);

        super.setContentView(contentView);
        onInitContentView(contentView);

        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    //----------------------添加标题栏和LoadingPage  by WangWB -----------------------------

    protected LoadingPage mLoadingPage;

    private View addLoadingPageIfNeed(View contentView) {
        if (openLoadingPage()) {
            mLoadingPage = new LoadingPage(this) {
                @Override
                public View createSuccessView() {
                    return contentView;
                }

                @Override
                protected void retryRequestData() {
                    BaseActivity.this.retryRequestData();
                }
            };
            return mLoadingPage;
        }
        return contentView;
    }

    protected boolean openLoadingPage() {
        return true;
    }

    /**
     * Desc:重新获取数据
     *
     * @author wwb
     */
    protected void retryRequestData() {

    }

    protected void onInitContentView(View contentView) {

    }


    /**
     * 为contentView添加titleView
     */
    private View addTitleViewIfNeed(View contentView) {
        View viewFinal = contentView;

        int resId = onCreateTitleViewResId();
        if (resId != 0) {
            View titleView = getLayoutInflater().inflate(resId, findViewById(android.R.id.content), false);

            LinearLayout linAll = new LinearLayout(this);
            linAll.setOrientation(LinearLayout.VERTICAL);
            linAll.addView(titleView);
            linAll.addView(contentView);
            viewFinal = linAll;

            onInitTitleView(titleView);
        }
        return viewFinal;
    }

    /**
     * 返回标题栏布局id，如需更换子类实现
     */
    protected int onCreateTitleViewResId() {
        return 0;
    }

    /**
     * 初始化标题栏view，如需更换子类实现
     */
    protected void onInitTitleView(View titleView) {
    }


    protected void showSuccessPage() {
        showPage(LoadingPage.STATE_SUCCESS);
    }

    protected void showErrorPage() {
        showPage(LoadingPage.STATE_ERROR);
    }

    protected void showPage(int state) {
        if (mLoadingPage != null) {
            mLoadingPage.showPage(state);
        }
    }

    //----------------------添加标题栏和LoadingPage  by WangWB -----------------------------


    //----------------------双击退出应用  by WangWB -----------------------------
    @Override
    public void onBackPressed() {
        if (mIsExitApp) {
            exitApp();
        } else {
            super.onBackPressed();
        }
    }


    public void exitApp() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showShort("再按一次退出" + AppUtils.getAppName());
        } else {
            //发送事件,参考下面的方法onExitAppReceive
            //            EventBus.getDefault().post(new Message(), EventBusTags.EXITAPP_MESSAGE);
            AppUtils.exitApp();

            //使App不退出，而是进入后台运行
//            moveTaskToBack(false);
        }
        mExitTime = System.currentTimeMillis();
    }

    //----------------------双击退出应用  by WangWB -----------------------------


    //----------------------进度框  by WangWB -----------------------------
    protected static final String MESSAGE_LOADING = "请稍候..";

    private ProgressDialog mLoadingDialog;

    protected void showLoadingDialog() {
        showLoadingDialog(this, MESSAGE_LOADING, true);
    }

    protected void showLoadingDialog(boolean cancelable) {
        showLoadingDialog(this, MESSAGE_LOADING, cancelable);
    }

    protected void showLoadingDialog(String msg) {
        showLoadingDialog(this, msg, true);
    }


    protected void showLoadingDialog(String msg, boolean cancelable) {
        showLoadingDialog(this, msg, cancelable);
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


    /**
     * 判断内容是否为空
     *
     * @param content
     * @return
     */
    public boolean isEmpty(CharSequence content) {
        return TextUtils.isEmpty(content);
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public boolean isEmpty(Collection<?> list) {
        return CollectionUtil.isEmpty(list);
    }


    protected void setupActivityComponent() {
    }

}
