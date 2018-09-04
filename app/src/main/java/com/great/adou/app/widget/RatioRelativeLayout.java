package com.great.adou.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.great.adou.R;

/**
 * <可以设置比例的视图>
 * Created by WangWB on 2018/09/04  16:51.
 */
public class RatioRelativeLayout extends RelativeLayout {
    private float mRatioWidth;
    private float mRatioHeight;

    private int type;

    public RatioRelativeLayout(Context context) {
        this(context, null);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mRatioWidth = t.getInt(R.styleable.RatioLayout_ratioWidthLayout, 1);
        mRatioHeight = t.getInt(R.styleable.RatioLayout_ratioHeightLayout, 1);
        type = t.getInt(R.styleable.RatioLayout_ratioType, 0);
        t.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (type == 0) {
            float mRatio = mRatioHeight / mRatioWidth;
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSize = (int) Math.ceil(widthSize * mRatio);
            int newHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, newHeightSpec);
        } else {
            float mRatio = mRatioWidth / mRatioHeight;
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int widthSize = (int) Math.ceil(heightSize * mRatio);
            int newWidthSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
            super.onMeasure(newWidthSpec, heightMeasureSpec);
        }
    }

}
