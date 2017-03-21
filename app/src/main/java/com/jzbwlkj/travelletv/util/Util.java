package com.jzbwlkj.travelletv.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.bean.PlayBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.internal.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by dn on 2017/2/12.
 */

public class Util {

    /**
     * Glide加载图片
     */
    public static void glide(String url, ImageView img) {
        Glide.with(App.app).load(URL.IMG + url).error(R.mipmap.default_image).into(img);
    }

    /**
     * Toast
     *
     * @param message
     */
    public static void toast(String message) {
        Toast.makeText(App.app, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 选择图片
     *
     * @param activity 当前Activity
     * @param count    选择图片的数量
     */
    public static void selectImg(Activity activity, int count) {
        MultiImageSelector.create()
                .showCamera(false)
                .count(count)
                .multi()
                .origin(new ArrayList<String>())
                .start(activity, ParamKey.REQUEST_IMAGE);

    }

    /**
     * 调用系统相机拍一张照片
     *
     * @param activity
     * @param name     图片名字
     * @return 图片地址
     */
    public static String getPhotoByCamera(Activity activity, String name) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name + ".png");
        Uri imageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, ParamKey.REQUEST_CAMERA);
        return file.getAbsolutePath();
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   宽度
     * @param height  高度
     * @return Bitmap
     */
    public static Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分享
     */
    public static void share(Context context,String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("旅行乐视");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("旅行乐视，海量大片等你来看！快来点击下载吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        oks.setImageUrl("http://lxls.jzbwlkj.com/lxlslogo.jpg"); // 必须有图片路径才能在微信分享有标题
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("旅行乐视");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://lxls.jzbwlkj.com/lxlslogo.jpg");
        // 启动分享GUI
        oks.show(context);
    }

    /**
     * 第三方登录
     *
     * @param name     登录平台的名字
     * @param listener 回调接口
     */
    public static void fastLogin(String name, PlatformActionListener listener) {
        Platform wechat = ShareSDK.getPlatform(name);
        if (wechat.isAuthValid()) {
            wechat.removeAccount(true);
        }
        wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
        wechat.setPlatformActionListener(listener);
        //authorize与showUser单独调用一个即可
        //wechat.authorize();//单独授权，OnComplete返回的hashmap是空的
        wechat.showUser(null);//授权并获取用户信息
    }

    /**
     * WebView 中的图片自适应屏幕
     * <p>
     * compile 'org.jsoup:jsoup:1.10.1'
     */
    public static String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }


    /**
     * 切换软键盘的状态
     */
    public static void toggleSoftKeyboardState(Context context) {
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_IMPLICIT,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
