package com.jzbwlkj.travelletv.application;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jzbwlkj.travelletv.db.DaoMaster;
import com.jzbwlkj.travelletv.db.DaoSession;
import com.orhanobut.logger.Logger;

import cn.sharesdk.framework.ShareSDK;

/**
 * 作者：admin on 2017/2/10 15:52
 */

public class App extends Application {
    public static Context app;
    private static DaoSession daoSession;
    public static String location = "";//定位信息
    public static String address;//位置  省市
    public static String user;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        //初始化Loger
        Logger.init("okhttp")
                .methodCount(1) // 方法栈打印的个数，默认是 2
                .hideThreadInfo(); // // 隐藏线程信息，默认显示
        //初始化分享
        ShareSDK.initSDK(this);

        setupDatabase();//创建数据库
    }

    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "TravelLetv.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
