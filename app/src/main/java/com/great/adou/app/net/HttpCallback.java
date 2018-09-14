package com.great.adou.app.net;


import android.net.ParseException;
import android.util.MalformedJsonException;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.great.adou.R;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * <网络请求回调>
 * Created by wang on 2017/11/3 16:45.
 */

public abstract class HttpCallback<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T data) {
        // 2017/3/22 这边网络请求成功返回都不一样所以不能在这里统一写了（如果是自己公司需要规定一套返回方案）
        // 2017/3/22 这里先统一处理为成功   我们要是想检查返回结果的集合是否是空，只能去子类回掉中完成了。
        onSuccess(data);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        String errorDesc = "请求数据失败";
        if (e instanceof UnknownHostException) {
            errorDesc = "网络异常，请检查您的网络状态";
        } else if (e instanceof ConnectException) {
            errorDesc = "网络连接异常，请检查您的网络状态";
        } else if (e instanceof JSONException || e instanceof ParseException || e instanceof MalformedJsonException) {
            errorDesc = "解析错误";
        }
        ToastUtils.showShort(errorDesc);
        onFail(errorDesc);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 如果喜欢统一处理成功回掉也是可以的。
     * 不过获取到的数据都是不规则的，理论上来说需要判断该数据是否为null或者list.size()是否为0
     * 只有不成立的情况下，才能调用成功方法refreshView/()。如果统一处理就放在每个refreshView中处理。
     */
    public abstract void onSuccess(T data);

    public abstract void onFail(String error);

}
