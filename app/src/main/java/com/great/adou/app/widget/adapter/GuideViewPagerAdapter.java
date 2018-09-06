package com.great.adou.app.widget.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.great.adou.R;

import java.util.List;

/**
 * <GuideActivity页面使用>
 * Created by WangWB on 2018/09/06  11:05.
 */
public class GuideViewPagerAdapter extends DPagerAdapter<Integer> {

    public GuideViewPagerAdapter(Activity activity, List<Integer> listModel) {
        super(activity, listModel);
    }

    @Override
    public View getView(ViewGroup container, int position) {
        int drawable = getData(position);
        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(drawable);
        return imageView;
    }
}
