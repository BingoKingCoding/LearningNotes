package com.great.adou.module.main.me;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.great.adou.R;
import com.great.adou.app.base.BaseFragment;
import com.great.adou.app.utils.GlideUtils;
import com.great.adou.app.widget.CircleImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * <个人中心>
 * Created by WangWB on 2018/09/13  14:11.
 */
public class MeFragment extends BaseFragment {

    private String url_header = "http://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537253072533&di=3aec1a943ac349878a275abb0765c638&imgtype=0&src=http%3A%2F%2F07.imgmini.eastday.com%2Fmobile%2F20170810%2F20170810180123_d41d8cd98f00b204e9800998ecf8427e_1.jpeg";

    private CircleImageView iv_head;
    private ImageView iv_bg_header;

    public static Fragment newInstance() {
        MeFragment fragment = new MeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        iv_head = contentView.findViewById(R.id.iv_head);
        iv_bg_header = contentView.findViewById(R.id.iv_bg_header);

    }

    @Override
    protected void initData() {
        super.initData();
        showSuccessPage();
//        GlideUtils.getInstance().loadImage(url_header, iv_head,new RequestOptions().transform(new CircleCrop()));

        GlideUtils.getInstance().loadImage(url_header,iv_head);

        GlideUtils.getInstance().loadImage(url_header, iv_bg_header, new RequestOptions().transform(new BlurTransformation(100)));

    }
}
