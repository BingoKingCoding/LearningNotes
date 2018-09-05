package com.great.adou.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.bingo.lib.dialog.IDialogConfirm;
import com.bingo.lib.dialog.impl.DialogBase;
import com.bingo.lib.dialog.impl.DialogConfirm;
import com.blankj.utilcode.util.ActivityUtils;
import com.great.adou.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * <请求权限>
 * Created by WangWB on 2018/09/05  16:24.
 */
public class PermissionUtil {

    private static final int CODE_PERMISSIONS = 1027;
    private List<String> needPermissions = new ArrayList<>(); // 未获得的权限列表
    private String mNeedPermissionDescription;
    private OnPermissionsCallback mOnPermissionsCallback;
    private boolean isFinishActivity;
    private Activity mActivity;
    private RxPermissions mRxPermissions;
    private DialogConfirm mDialogConfirm;

    public PermissionUtil(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Desc:
     *
     * @param permissions               需要申请的权限
     * @param callback                  申请权限成功回调
     * @param isFinishActivity          跳转到设置页面dialog点击取消时是否结束当前activity
     * @param needPermissionDescription 跳转到设置页面dialog的提示内容
     * @author wwb
     */
    @SuppressLint("CheckResult")
    public void requestPermissions(String[] permissions, String needPermissionDescription, OnPermissionsCallback callback, boolean isFinishActivity) {

        Collections.addAll(needPermissions, permissions);
        this.mNeedPermissionDescription = needPermissionDescription;
        this.mOnPermissionsCallback = callback;
        this.isFinishActivity = isFinishActivity;

        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(ActivityUtils.getTopActivity());
        }

        mRxPermissions.request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isGrant) throws Exception {
                        if (isGrant) {
                            callback.OnGrant();
                        } else {
                            showPermissionDialog(mActivity, needPermissionDescription, isFinishActivity);
                        }
                    }
                });
    }


    // 显示缺失权限提示
    private void showPermissionDialog(Activity activity, String needPermissionDescription, boolean isFinishActivity) {
        if (mDialogConfirm == null) {
            mDialogConfirm = new DialogConfirm(activity);
        }
        mDialogConfirm.setTextTitle("帮助")
                .setTextConfirm("确定")
                .setTextCancel("取消")
                .setTextContent(needPermissionDescription + "\n是否前往设置？")
                .setCallback(new IDialogConfirm.Callback() {
                    @Override
                    public void onClickCancel(View view, DialogBase dialogBase) {
                        if (isFinishActivity) {
                            activity.finish();
                        }
                    }

                    @Override
                    public void onClickConfirm(View view, DialogBase dialogBase) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivityForResult(intent, CODE_PERMISSIONS);
                    }
                })
                .show();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_PERMISSIONS) {
            requestPermissions(needPermissions.toArray(new String[0]), mNeedPermissionDescription, mOnPermissionsCallback, isFinishActivity);
        }
    }

    public interface OnPermissionsCallback {
        void OnGrant();
    }


    //释放资源
    public void release() {
        if (mActivity != null) {
            mActivity = null;
        }
    }


}
