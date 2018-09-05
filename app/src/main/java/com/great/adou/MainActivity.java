package com.great.adou;

import android.os.Bundle;

import com.great.adou.app.base.BaseTitleActivity;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIsExitApp = true;
        setTitleText("Main");
    }
}
