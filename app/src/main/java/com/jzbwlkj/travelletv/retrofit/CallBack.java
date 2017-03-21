package com.jzbwlkj.travelletv.retrofit;

/**
 * Created by dn on 2017/3/1.
 */

public abstract class CallBack {
    public void onStart(){}

    public void onCompleted(){}

    abstract public void onError(Throwable e);

    public void onProgress(double fileSizeDownloaded){}

    abstract public void onSucess(String path, String name, double fileSize);
}
