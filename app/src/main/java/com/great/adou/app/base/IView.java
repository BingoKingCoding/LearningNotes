package com.great.adou.app.base;

/**
 * <MVP的V>
 * Created by WangWB on 2018/09/05  17:38.
 */
public interface IView {

    void showLoadingDialog();

    void showLoadingDialog(String msg);

    void finish();

}
