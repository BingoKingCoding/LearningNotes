package com.great.adou.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.trello.rxlifecycle2.components.RxActivity;

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
        View contentView = this.getLayoutInflater().inflate(layoutResID, (ViewGroup) this.findViewById(android.R.id.content),false);
        this.setContentView(contentView);
    }


    public void setContentView(View view) {

    }


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
            ToastUtils.showShort("再按一次返回桌面!");
        } else {
            //发送事件,参考下面的方法onExitAppReceive
            //            EventBus.getDefault().post(new Message(), EventBusTags.EXITAPP_MESSAGE);
            //            AppUtils.exitApp();

            //使App不退出，而是进入后台运行
            moveTaskToBack(false);
        }
        mExitTime = System.currentTimeMillis();
    }


    protected void setupActivityComponent() {
    }


}
