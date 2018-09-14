package com.great.adou.app.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.great.adou.R;


/**
 * <加载页面>
 * Created by WangWB on 2018/08/29  10:28.
 */
public abstract class LoadingPage extends FrameLayout {

    private TextView tv_error_desc;

    public LoadingPage(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_SUCCESS = 3;
    private View loadingView;                 // 加载中的界面
    private View errorView;                   // 错误界面
    public View successView;                 // 加载成功的界面

    private int state = STATE_UNKNOWN;

    private AnimationDrawable mAnimationDrawable;

    private void init() {
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //把loadingView添加到frameLayout上
        if (loadingView == null) {
            loadingView = createLoadingView();
            this.addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        //把errorView添加到frameLayout上
        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        //使用butterKnife需要先初始化
        if (successView == null) {
            successView = createSuccessView();
            this.addView(successView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }

        showPage(state);//根据状态显示界面
    }

    private View createLoadingView() {
        loadingView = LayoutInflater.from(getContext()).inflate(R.layout.layout_loadingpage_state_loading, this, false);
        ImageView img = loadingView.getRootView().findViewById(R.id.img_progress);

        // 加载动画 这边也可以直接用progressbar
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        return loadingView;
    }

    private View createErrorView() {
        errorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_loadingpage_state_error, this, false);
        CardView cv_error_refresh = errorView.findViewById(R.id.cv_error_refresh);
        tv_error_desc = errorView.findViewById(R.id.tv_desc);
        cv_error_refresh.setOnClickListener(view -> {
            showPage(STATE_LOADING);
            retryRequestData();
        });
        return errorView;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void showPage(int state) {
        this.state = state;

        if (loadingView != null) {
            if (state == STATE_UNKNOWN || state == STATE_LOADING) {
                loadingView.setVisibility(View.VISIBLE);
                // 开始动画
                startAnimation();
            } else {
                // 关闭动画
                stopAnimation();
                loadingView.setVisibility(View.GONE);
            }
        }
        if (state == STATE_ERROR || state == STATE_SUCCESS) {
            //关闭动画
            stopAnimation();
        }


        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.GONE);
        }

        if (successView != null) {
            successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE : View.GONE);
        }

    }


    private void startAnimation() {
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
    }

    private void stopAnimation() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }


    public void setTextErrorDesc(String desc) {
        if (!TextUtils.isEmpty(desc) && tv_error_desc != null) {
            tv_error_desc.setText(desc);
        }
    }


    /***
     * 创建成功的界面
     */
    public abstract View createSuccessView();

    /**
     * 请求服务器
     * 必须在请求完成后调用showPage方法
     */
    protected abstract void retryRequestData();

}
