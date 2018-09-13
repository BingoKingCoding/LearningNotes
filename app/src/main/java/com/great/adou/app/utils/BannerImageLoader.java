package com.great.adou.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wwb on 2017/12/4 11:56.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        GlideUtils.getInstance().loadImage(1,url,imageView);
    }
}
