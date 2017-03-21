package com.jzbwlkj.travelletv.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jzbwlkj.travelletv.bean.my.MyWalletBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.view.Dialog;
import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by gaoyuan on 2017/3/10.
 */

public abstract class BaseSubscriber2<T> extends Subscriber<HttpResult<T>> {
    private Context mContext;
    private ProgressDialog mDialog;
    private final int SUCCESS_CODE = 200;


    public BaseSubscriber2(Context mContext) {
        this.mContext = mContext;
        mDialog = new ProgressDialog(mContext);
    }

    @Override
    public void onCompleted() {
        Log.d("gesanri", "onComplete");

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable e) {
        Logger.d("error:" + e.toString());

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        Toast.makeText(mContext, "网络异常，请稍后再试", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNext(HttpResult<T> value) {
        if (value.code == ParamKey.SUCCESS) {
            T t = value.data;
            onHandleSuccess(t);
        } else {
            onHandleError(value.code, value.message);
        }
    }

    public  abstract void onHandleSuccess(T t);

    private void onHandleError(int code, String message) {
        if(code==-3){//余额不足
            Dialog.rechargeDialog(mContext);
            return;
        }
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
