package com.great.adou.module.main;

import android.os.Bundle;

import com.great.adou.R;
import com.great.adou.app.Constants;
import com.great.adou.app.base.BaseTitleActivity;
import com.great.adou.app.utils.SPHelper;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mIsExitApp = true;
        setTitleText("Main");
        hideTitleNavigationButton();



    }


}
