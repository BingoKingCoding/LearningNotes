package com.great.adou.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.great.adou.R;
import com.great.adou.app.Constants;
import com.great.adou.app.base.BaseActivity;
import com.great.adou.app.utils.SPHelper;
import com.great.adou.app.widget.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {

    private static final int[] pics =
            {R.drawable.ic_guide_one, R.drawable.ic_guide_two, R.drawable.ic_guide_three};

    private ViewPager vp_guide;
    private LinearLayout ll_indicator;
    private CardView cv_experience;

    private List<Integer> mListPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected void onInitContentView(View contentView) {
        super.onInitContentView(contentView);
        vp_guide = findViewById(R.id.vp_guide);
        ll_indicator = findViewById(R.id.ll_indicator);
        cv_experience = findViewById(R.id.cv_experience);

        cv_experience.setOnClickListener(v -> {
            Intent intent = new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            SPHelper.getSPUtils().put(Constants.SPConstants.SP_IS_FIRST, false);
            finish();
        });

        initView();

    }

    private void initView() {
        mListPic = new ArrayList<>();
        for (int pic : pics) {
            mListPic.add(pic);
        }

        createIndicator();

        GuideViewPagerAdapter pagerAdapter = new GuideViewPagerAdapter(this, mListPic);
        vp_guide.setAdapter(pagerAdapter);
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateSelected(position);

                if (position == mListPic.size() - 1) {
                    cv_experience.setVisibility(View.VISIBLE);
                } else {
                    cv_experience.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_guide.setPageTransformer(false, (page, position) -> {
            final float normalizedPosition = Math.abs(Math.abs(position) - 1);
            page.setScaleX(normalizedPosition / 2 + 0.5f);
            page.setScaleY(normalizedPosition / 2 + 0.5f);
        });

        updateSelected(0);

    }

    private void createIndicator() {
        View view;

        for (int i = 0; i < mListPic.size(); i++) {
            //创建底部小圆点
            view = new View(this);
            view.setBackgroundResource(R.drawable.selector_indicator_guide);
            view.setSelected(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            //设置间隔
            if (i != mListPic.size() - 1) {
                layoutParams.rightMargin = 20;
            }
            ll_indicator.addView(view, layoutParams);
        }

    }

    /**
     * Desc:
     *
     * @author wwb
     */
    private void updateSelected(int position) {
        for (int i = 0; i < mListPic.size(); i++) {
            if (i != position) {
                ll_indicator.getChildAt(i).setSelected(false);
            }
        }
        ll_indicator.getChildAt(position).setSelected(true);
    }


    @Override
    protected boolean openLoadingPage() {
        return false;
    }
}
