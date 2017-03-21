package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.UploadImageBean;
import com.jzbwlkj.travelletv.bean.login.RegisterBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.BitmapUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.ResponseBody;

public class MyAvatarActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.m_more)
    ImageView mMore;
    @BindView(R.id.img_my_avatar_big)
    ImageView mAvatarBig;
    @BindView(R.id.bt_my_avatar_save)
    Button mSave;
    @BindView(R.id.activity_my_avatar)
    RelativeLayout mRelativeLayout;
    private TextView mTake, mSelect, mCancel;//PopWindow

    private String path, path1;//相机回掉的图片路径
    private PopupWindow mWindow;
    private String picId;//上传到服务器的图片id
    private List<String> mListPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_avatar);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getString(R.string.my_avatar));
        mMore.setVisibility(View.VISIBLE);
        mListPath = new ArrayList<>();
    }

    @Override
    protected void initData() {
        View view = View.inflate(this, R.layout.popwindow_my_avatar, null);
        mTake = (TextView) view.findViewById(R.id.tv_pop_my_avatar_take);
        mSelect = (TextView) view.findViewById(R.id.tv_pop_my_avatar_select);
        mCancel = (TextView) view.findViewById(R.id.tv_pop_my_avatar_cancel);
        mWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        String avatar = getIntent().getStringExtra("avatar");
        Util.glide(avatar, mAvatarBig);
    }

    @Override
    protected void setData() {
        mSave.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mTake.setOnClickListener(this);
        mSelect.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mListPath.clear();
        if (requestCode == ParamKey.REQUEST_IMAGE) {//选择的图片地址回掉
            if (resultCode == RESULT_OK) {
                List<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mAvatarBig.setImageBitmap(BitmapFactory.decodeFile(list.get(0)));
                mListPath.add(list.get(0));
            }
        }

        if (requestCode == ParamKey.REQUEST_CAMERA) {//选择的图片地址回掉
            if (resultCode == RESULT_OK) {
                mAvatarBig.setImageBitmap(BitmapFactory.decodeFile(path));
                bitmap();
            }
        }
        mWindow.dismiss();
    }

    /**
     * 上传头像
     */
    private void uploadAvatar() {
        maps.put("avatar", picId);
        RetrofitClient.getInstance(this).postData(URL.UPLOAD_AVATAR, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    RegisterBean bean = new Gson().fromJson(responseBody.string(), RegisterBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Util.toast(bean.getMessage());
                        setResult(ParamKey.MY_REPONSE);
                        finish();
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传图片到服务器
     */
    private void uploadPic(List<String> list) {
        RetrofitClient.getInstance(this).postSingleImg(URL.UPLOAD_IMAGE, list, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    UploadImageBean bean = new Gson().fromJson(responseBody.string(), UploadImageBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        picId = bean.getData().getAvatar().getId() + "";
                        uploadAvatar();
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_finish://返回
                this.finish();
                break;
            case R.id.m_more://显示PopWindow
                mWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.bt_my_avatar_save://保存为头像
                if (mListPath == null || mListPath.size() == 0) {
                    finish();
                    return;
                }
                uploadPic(mListPath);
                break;
            case R.id.tv_pop_my_avatar_cancel://取消，隐藏PopWindow
                mWindow.dismiss();
                break;
            case R.id.tv_pop_my_avatar_take://拍一张
                path = Util.getPhotoByCamera(this, System.currentTimeMillis() + "");
                break;
            case R.id.tv_pop_my_avatar_select://从相册中选一张
                Util.selectImg(this, 1);
                break;
        }
    }

    private void bitmap() {
        String s = BitmapUtils.compressImageUpload(path);
        mListPath.add(s);
    }

}
