package com.jzbwlkj.travelletv.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.RechargeActivity;

/**
 * Created by dn on 2017/2/17.
 */

public class Dialog {
    private Context context;
    private AlertDialog dialog;
    private final AlertDialog.Builder builder;

    public Dialog(Activity context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);
        setNegativeClick();
    }

    /**
     * 显示
     */
    public void showDialog() {
        dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 设置显示内容
     *
     * @param message
     * @return
     */
    public Dialog setMessage(String message) {
        builder.setMessage(message);
        return this;
    }

    /**
     * 隐藏
     */
    public Dialog hideDialog() {
        dialog.dismiss();
        return this;
    }

    /**
     * 设置确认监听事件
     *
     * @param listener
     */
    public Dialog setPositiveClick(String text,AlertDialog.OnClickListener listener) {
        builder.setPositiveButton(text, listener);
        return this;
    }

    /**
     * 设置取消监听事件
     */
    public Dialog setNegativeClick() {
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        return this;
    }
    /**
     * 设置取消监听事件2
     */
    public Dialog setNegativeClick2(AlertDialog.OnClickListener listener) {
        builder.setNegativeButton("取消",listener);
        return this;
    }

    /**
     * 设置标题
     */
    public Dialog setTitle(String title) {
        builder.setTitle(title);
        return this;
    }

    /**
     * 捕捉返回键
     */
    public Dialog onKeyDown(DialogInterface.OnKeyListener listener){
        builder.setOnKeyListener(listener);
        return this;
    }

    /**
     * 充值对话框
     */
    public static void rechargeDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您的余额不足，是否前往充值？")
                .setPositiveButton("前往充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, RechargeActivity.class));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
