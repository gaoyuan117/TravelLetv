package com.jzbwlkj.travelletv.retrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by dn on 2017/2/19.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;
    private boolean isNeedCahe;
    private ProgressDialog dialog;

    public BaseSubscriber(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setMessage("正在加载中");
    }

    public BaseSubscriber() {

    }

    @Override
    public void onError(Throwable e) {
        if (dialog != null) {
            dialog.dismiss();
        }
        Logger.d(e.getMessage());
        Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        if(dialog!=null){
            dialog.show();
        }
    }

    @Override
    public void onCompleted() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

//    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
