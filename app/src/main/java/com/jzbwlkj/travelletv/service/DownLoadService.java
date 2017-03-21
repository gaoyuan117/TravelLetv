package com.jzbwlkj.travelletv.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.DownLoadPlayActivity;
import com.jzbwlkj.travelletv.bean.PlayBean;
import com.jzbwlkj.travelletv.util.Util;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DownLoadService extends Service {

    private NotificationManager manager;
    private List<Future<File>> mList;
    private List<File> mFiles;

    public DownLoadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mList = new ArrayList<>();
        mFiles = new ArrayList<>();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        String type = intent.getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("exit")) {
            if (mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (!mList.get(i).isCancelled()) {
                        try {
                            mList.get(i).get().delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            manager.cancelAll();
        } else if (intent != null) {
            PlayBean.DataBean dataBean = (PlayBean.DataBean) intent.getSerializableExtra("data");
            if (dataBean != null) {
                Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                downLoad(this, dataBean);
            }
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
        manager.cancelAll();
        stopService(new Intent(this, DownLoadService.class));
    }

    public void downLoad(final Context mContext, final PlayBean.DataBean dataBean) {
        final int notificationId = Integer.parseInt(dataBean.getId());
        final Notification.Builder builder = new Notification.Builder(mContext);
        builder.setWhen(System.currentTimeMillis()).setContentTitle(dataBean.getTitle() + "下载中").setContentText(dataBean.getTitle());
        builder.setSmallIcon(R.mipmap.xiazai);
        String path = mContext.getExternalFilesDir(null) + File.separator + dataBean.getId() + "-" + dataBean.getTitle() + ".mp4";
        File file = new File(path);
        mFiles.add(file);
        Future<File> future = Ion.with(mContext).load(dataBean.getMoviepath())
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        builder.setProgress((int) (total / (1024 * 1024)), (int) downloaded / (1024 * 1024), false);
                        builder.setContentText(downloaded / (1024 * 1024) + "M" + " / " + (total / (1024 * 1024)) + "M");
                        manager.notify(notificationId, builder.build());
                    }
                }).write(file).setCallback(new FutureCallback<File>() {

                    @Override
                    public void onCompleted(Exception e, File file) {
                        if (e == null) {
                            Intent intent = new Intent(mContext, DownLoadPlayActivity.class);
                            PendingIntent pendintent = PendingIntent.getActivity(mContext, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentText("下载完成");
                            builder.setContentIntent(pendintent);
                            builder.setAutoCancel(true);
                            manager.notify(notificationId, builder.build());
                        } else {
                            Util.toast("下载失败，请稍后重试");
                            manager.cancel(notificationId);
                            mContext.stopService(new Intent(mContext, DownLoadService.class));
                        }
                    }
                });
        mList.add(future);
    }
}
