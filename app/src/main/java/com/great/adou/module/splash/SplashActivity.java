package com.great.adou.module.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.great.adou.R;
import com.great.adou.app.Constants;
import com.great.adou.app.base.BaseActivity;
import com.great.adou.app.utils.RxUtils;
import com.great.adou.app.utils.SPHelper;
import com.great.adou.module.main.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class SplashActivity extends BaseActivity {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isFirst = SPHelper.getSPUtils().getBoolean(Constants.SP_IS_FIRST, true);
        if (isFirst) {
            Observable.timer(1, TimeUnit.SECONDS).compose(RxUtils.io2Main()).subscribe(aLong -> {
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            });

        } else {
            requestPermissions();
        }
    }

    @Override
    protected boolean openLoadingPage() {
        return false;
    }

    private void requestPermissions() {
        requestPermissions(PERMISSIONS, getString(R.string.permission_storage_camera_phone), () -> {
            //加入定时器 睡眠 2秒 自动跳转页面
            Observable.timer(2, TimeUnit.SECONDS).compose(RxUtils.io2Main()).subscribe(aLong -> {
                boolean isFirst = SPHelper.getSPUtils().getBoolean(Constants.SP_IS_FIRST, true);
//                if (isFirst) {
//                    Intent intent = new Intent(SplashActivity.this, CitySelectActivity.class);
//                    intent.putExtra(CitySelectActivity.IS_WELCOME, true);
//                    startActivity(intent);
//                    LaunchActivity.this.finish();
//                } else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                SplashActivity.this.finish();
//                }
            });
        }, true);
    }

}
