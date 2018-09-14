package com.great.adou.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.great.adou.R;
import com.great.adou.app.App;
import com.great.adou.app.di.AppComponent;
import com.great.adou.app.event.Event;
import com.great.adou.app.event.EventBusUtil;
import com.great.adou.app.utils.CollectionUtil;
import com.great.adou.app.utils.PermissionUtil;
import com.great.adou.app.utils.StatusBarUtil;
import com.great.adou.app.widget.LoadingPage;
import com.great.adou.app.widget.ProgressDialog;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


/**
 * <所有activity的基类>
 * Created by WangWB on 2018/9/3:22:08.
 * Email:634051075@qq.com
 */
public class BaseActivity<P extends IPresenter> extends RxFragmentActivity {

    @Inject
    protected P mPresenter;

    /**
     * 触摸返回键是否退出App
     */
    protected boolean mIsExitApp = false;
    protected long mExitTime = 0;

    //请求权限
    private PermissionUtil mPermissionUtil;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        initComponent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    public void setContentView(int layoutResID) {
        View contentView = this.getLayoutInflater().inflate(layoutResID, this.findViewById(android.R.id.content), false);
        this.setContentView(contentView);
    }

    public void setContentView(View view) {
        View loadingContentView = addLoadingPageIfNeed(view);

        View contentView = addTitleViewIfNeed(loadingContentView);

        super.setContentView(contentView);
        setStatusBar();

        onInitContentView(contentView);
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

    public void showLoadingDialog() {
        showLoadingDialog(this, MESSAGE_LOADING, true);
    }

    protected void showLoadingDialog(boolean cancelable) {
        showLoadingDialog(this, MESSAGE_LOADING, cancelable);
    }

    public void showLoadingDialog(String msg) {
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


    protected void initComponent() {
    }


    //----------------------权限请求  by WangWB -----------------------------

    public PermissionUtil getPermissionUtil() {
        if (mPermissionUtil == null) {
            mPermissionUtil = new PermissionUtil(this);
        }
        return mPermissionUtil;
    }

    public void requestPermissions(String permissions, String needPermissionDescription, PermissionUtil.OnPermissionsCallback onPermissionsCallback) {
        requestPermissions(permissions, needPermissionDescription, onPermissionsCallback, false);
    }

    public void requestPermissions(String permissions, String needPermissionDescription, PermissionUtil.OnPermissionsCallback onPermissionsCallback, boolean isFinishActivity) {
        List<String> needPermissions = new ArrayList<>();
        Collections.addAll(needPermissions, permissions);
        requestPermissions(needPermissions.toArray(new String[0]), needPermissionDescription, onPermissionsCallback, isFinishActivity);
    }

    public void requestPermissions(String[] permissions, String needPermissionDescription, PermissionUtil.OnPermissionsCallback onPermissionsCallback, boolean isFinishActivity) {
        getPermissionUtil().requestPermissions(permissions, needPermissionDescription, onPermissionsCallback, isFinishActivity);
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


    //----------------------权限请求  by WangWB -----------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPermissionUtil != null) {
            mPermissionUtil.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected AppComponent getAppComponent() {
        return App.getApp().getAppComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            this.mPresenter = null;
        }
        if (mPermissionUtil != null) {
            mPermissionUtil.release();
            mPermissionUtil = null;
        }
    }
}
