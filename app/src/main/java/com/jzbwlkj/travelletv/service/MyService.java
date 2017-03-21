package com.jzbwlkj.travelletv.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.DownLoadActivity;
import com.jzbwlkj.travelletv.activity.DownLoadPlayActivity;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.bean.PlayBean;
import com.jzbwlkj.travelletv.db.DownDao;
import com.jzbwlkj.travelletv.db.DownDaoDao;
import com.jzbwlkj.travelletv.util.Util;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MyService extends Service {

    private NotificationManager manager;
    private MyBroadcastReceiver broadcastReceiver;
    private Map<Integer, Future<File>> maps;
    private DownDaoDao dao;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        maps = new HashMap<>();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        dao = App.getDaoInstant().getDownDaoDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        String type = intent.getStringExtra("type");
        if (!TextUtils.isEmpty(type) && type.equals("exit")) {
            manager.cancelAll();
        } else if (intent != null) {
            PlayBean.DataBean dataBean = (PlayBean.DataBean) intent.getSerializableExtra("data");
            if (dataBean != null) {
                Toast.makeText(this, "开始下载", Toast.LENGTH_SHORT).show();
                downLoad(this, dataBean);
            }
        }
        registerReceiver();

        return START_NOT_STICKY;
    }

    /**
     * 动态注册广播
     */
    private void registerReceiver() {
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("down");
        registerReceiver(broadcastReceiver, filter);
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
        final RemoteViews rv = new RemoteViews(getPackageName(), R.layout.layout_notification);
        builder.setSmallIcon(R.mipmap.xiazai);
        builder.setContent(rv);//设置内容的视图
        Intent intent2 = new Intent("down");
        Bundle bundle = new Bundle();
        bundle.putString("id", notificationId + "");

        intent2.putExtras(bundle);
        PendingIntent intent3 = PendingIntent.getBroadcast(this, notificationId, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.notification_cancel, intent3);
        rv.setTextViewText(R.id.notification_title, dataBean.getTitle());//设置内部TextView的文本内容
        manager.notify(notificationId, builder.build());
        String path = mContext.getExternalFilesDir(null) + File.separator + dataBean.getId() + "-" + dataBean.getTitle() + ".mp4";
        File file = new File(path);
        Future<File> future = Ion.with(mContext).load(dataBean.getMoviepath())
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        rv.setProgressBar(R.id.notification_pg, (int) (total / (1024 * 1024)), (int) downloaded / (1024 * 1024), false);
                        double l = downloaded / (1024 * 1024);//已下载
                        double l1 = total / (1024 * 1024);//总
                        int progress = (int) (l / l1 * 100);
                        rv.setTextViewText(R.id.notification_progress, progress + "%");
                        manager.notify(notificationId, builder.build());
                    }
                }).write(file).setCallback(new FutureCallback<File>() {

                    @Override
                    public void onCompleted(Exception e, File file) {
                        if (e == null) {
                            Intent intent = new Intent(mContext, DownLoadActivity.class);
                            PendingIntent pendintent = PendingIntent.getActivity(mContext, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            rv.setTextViewText(R.id.notification_cancel, "下载完成");
                            rv.setOnClickPendingIntent(R.id.notification_cancel, pendintent);
                            builder.setAutoCancel(true);
                            //保存下载信息到本地
                            DownDao downDao = new DownDao();
                            downDao.setUser(App.user);
                            downDao.setVideoId(dataBean.getId());
                            downDao.setDes(dataBean.getDescribe());
                            downDao.setTitle(dataBean.getTitle());
                            downDao.setUser(App.user);
                            downDao.setFilePath(file.getAbsolutePath());
                            dao.insertOrReplace(downDao);
                            manager.notify(notificationId, builder.build());
                        } else {
                            Util.toast("下载失败");
                            manager.cancel(notificationId);
                            mContext.stopService(new Intent(mContext, DownLoadService.class));
                        }
                    }
                });
        maps.put(notificationId, future);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            String id = extras.getString("id");
            Future<File> fu = maps.get(Integer.parseInt(id));
            fu.cancel(true);
            Logger.d("down " + fu.isDone());
            try {
                fu.get().delete();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            manager.cancel(Integer.parseInt(id));
        }
    }
}
