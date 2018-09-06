package com.great.adou.app.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.great.adou.app.base.BaseActivity;


/**
 * <View基类>
 * Created by WangWB on 2018/05/23  11:43.
 */

public class AppView extends FrameLayout implements View.OnClickListener {

    public AppView(@NonNull Context context) {
        super(context);
        baseInit();
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        baseInit();
    }

    public AppView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        baseInit();
    }


    /**
     * 设置是否消费掉触摸事件，true-事件不会透过view继续往下传递
     */
    private boolean mConsumeTouchEvent = false;

    private void baseInit() {
        int layoutId = onCreateContentView();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
    }

    /**
     * 设置布局
     *
     * @param layoutId 布局id
     */
    public void setContentView(int layoutId) {
        removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
    }

    public void setContentView(View contentView) {
        removeAllViews();
        addView(contentView);
    }

    public void setContentView(View contentView, ViewGroup.LayoutParams params) {
        removeAllViews();
        addView(contentView, params);
    }

    /**
     * 可重写此方法返回布局id
     */
    protected int onCreateContentView() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    public Activity getActivity() {
        Context context = getContext();
        if (context instanceof Activity) {
            return (Activity) context;
        } else {
            return null;
        }
    }

    public BaseActivity getBaseActivity() {
        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return (BaseActivity) activity;
        } else {
            return null;
        }
    }

    /**
     * 设置是否消费掉触摸事件
     *
     * @param consumeTouchEvent true-消费掉事件，事件不会透过view继续往下传递
     */
    public void setConsumeTouchEvent(boolean consumeTouchEvent) {
        mConsumeTouchEvent = consumeTouchEvent;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mConsumeTouchEvent) {
            super.onTouchEvent(event);
            return true;
        }
        return super.onTouchEvent(event);
    }
}
