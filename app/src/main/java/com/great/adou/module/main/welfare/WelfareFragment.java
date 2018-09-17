package com.great.adou.module.main.welfare;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.great.adou.R;
import com.great.adou.app.base.BaseFragment;
import com.great.adou.module.main.home.HomeFragment;

/**
 * <福利>
 * Created by WangWB on 2018/09/17  17:18.
 */
public class WelfareFragment extends BaseFragment {

    public static Fragment newInstance() {
        WelfareFragment fragment = new WelfareFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("type", type);
//        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        TextView toolbar_title = contentView.findViewById(R.id.toolbar_title);
        toolbar_title.setText("福利");
    }

    @Override
    protected boolean openLoadingPage() {
        return false;
    }
}
