package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.InfoBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.CircleImageView;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.img_my_info_avatar)
    CircleImageView mInfoAvatar;
    @BindView(R.id.ll_my_info_name)
    LinearLayout mInfoName;
    @BindView(R.id.ll_my_info_sex)
    LinearLayout mInfoSex;
    @BindView(R.id.ll_my_info_birthday)
    LinearLayout mInfoBirthday;
    @BindView(R.id.ll_my_info_phone)
    LinearLayout mInfoPhone;
    @BindView(R.id.ll_my_info_code)
    LinearLayout mInfoCode;
    @BindView(R.id.tv_my_info_sex)
    TextView mSex;
    @BindView(R.id.tv_my_info_birthday)
    TextView mBirhday;
    @BindView(R.id.tv_my_info_name)
    TextView mName;
    @BindView(R.id.tv_my_info_phone)
    TextView mPhone;

    private String avatar;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.my_info));
    }

    @Override
    protected void initData() {
        String json = getIntent().getStringExtra("json");
    }

    @Override
    protected void setData() {

    }

    /**
     * 设置个人信息
     */
    private void setInfo(String json) {
        InfoBean infoBean = new Gson().fromJson(json, InfoBean.class);
        InfoBean.DataBean bean = infoBean.getData();
        avatar = bean.getAvatar_path();
        Util.glide(avatar, mInfoAvatar);
        mName.setText(bean.getRealname());
        //设置生日
        if (!TextUtils.isEmpty(bean.getBirthday())) {
            mBirhday.setText(bean.getBirthday());
        }
        //设置性别
        if (bean.getSex().equals(ParamKey.ONE)) {
            mSex.setText(getString(R.string.man));
        } else {
            mSex.setText(getString(R.string.woman));
        }
        //设置手机号
        mPhone.setText(bean.getMobile());
    }

    @OnClick({R.id.img_my_info_avatar, R.id.ll_my_info_name, R.id.ll_my_info_sex, R.id.ll_my_info_birthday, R.id.ll_my_info_phone, R.id.ll_my_info_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_my_info_avatar:
                Intent intent = new Intent(this, MyAvatarActivity.class);
                intent.putExtra("avatar", avatar);
                startActivity(intent);
                break;
            case R.id.ll_my_info_name:

                break;
            case R.id.ll_my_info_sex:

                break;
            case R.id.ll_my_info_birthday:

                break;
            case R.id.ll_my_info_phone:

                break;
            case R.id.ll_my_info_code:
                startActivity(new Intent(this, CodeActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    /**
     * 获取个人信息
     */
    private void loadInfo() {
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.MY_BASE_INFO, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    json = responseBody.string();
                    InfoBean bean = new Gson().fromJson(json, InfoBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        setInfo(json);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}