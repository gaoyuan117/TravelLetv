package com.jzbwlkj.travelletv.retrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by dn on 2017/3/1.
 */

public class DownLoadManager {

    private String fileName;
    private CallBack callBack;

    private static final String TAG = "DownLoadManager";

    private static String APK_CONTENTTYPE = "application/vnd.android.package-archive";

    private static String PNG_CONTENTTYPE = "image/png";

    private static String JPG_CONTENTTYPE = "image/jpg";

    private static String fileSuffix = "";

    private Handler handler;

    public DownLoadManager(CallBack callBack) {
        this.callBack = callBack;
    }

    private static DownLoadManager sInstance;

    /**
     * DownLoadManager getInstance
     */
    public static synchronized DownLoadManager getInstance(CallBack callBack) {
        if (sInstance == null) {
            sInstance = new DownLoadManager(callBack);
        }
        return sInstance;
    }

    /**
     * 设置文件名字
     */
    public DownLoadManager setFileName(String name) {
        fileName = name;
        return this;
    }


    public boolean writeResponseBodyToDisk(Context context, ResponseBody body) {

        Log.d(TAG, "contentType:>>>>" + body.contentType().toString());

        String type = body.contentType().toString();

        if (type.equals(APK_CONTENTTYPE)) {

            fileSuffix = ".apk";
        } else if (type.equals(PNG_CONTENTTYPE)) {
            fileSuffix = ".png";
        } else if (type.equals(JPG_CONTENTTYPE)) {
            fileSuffix = ".jpg";
        }

        // 其他同上 自己判断加入
        final String path = context.getExternalFilesDir(null) + File.separator + fileName + ".mp4";

        Log.d(TAG, "path:>>>>" + path);

        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete();
            }

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                final double fileSize = body.contentLength();
                double fileSizeDownloaded = 0;
                Log.d(TAG, "file length: " + fileSize);
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    if (callBack != null) {
                        handler = new Handler(Looper.getMainLooper());
                        final double finalFileSizeDownloaded = fileSizeDownloaded / fileSize * 100;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgress(finalFileSizeDownloaded);
                            }
                        });
                    }
                }

                outputStream.flush();
                if (callBack != null) {
                    handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSucess(path, fileName, fileSize);

                        }
                    });
                }

                return true;
            } catch (IOException e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            if (callBack != null) {
                callBack.onError(e);
            }
            return false;
        }
    }
}
