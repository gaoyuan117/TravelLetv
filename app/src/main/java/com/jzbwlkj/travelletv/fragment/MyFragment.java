package com.jzbwlkj.travelletv.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.login.LoginActivity;
import com.jzbwlkj.travelletv.activity.my.BuyVideoActivity;
import com.jzbwlkj.travelletv.activity.my.CodeActivity;
import com.jzbwlkj.travelletv.activity.my.HistoryActivity;
import com.jzbwlkj.travelletv.activity.my.MyAvatarActivity;
import com.jzbwlkj.travelletv.activity.my.MyCollectActivity;
import com.jzbwlkj.travelletv.activity.my.MyInfoActivity;
import com.jzbwlkj.travelletv.activity.my.MyWalletActivity;
import com.jzbwlkj.travelletv.activity.my.SettingActivity;
import com.jzbwlkj.travelletv.activity.my.ShareActivity;
import com.jzbwlkj.travelletv.activity.my.UserGuideActivity;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseFragment;
import com.jzbwlkj.travelletv.bean.my.InfoBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.CircleImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * 作者：admin on 2017/2/10 15:48
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.tv_my_nickname)
    TextView mNickname;
    @BindView(R.id.tv_my_name)
    TextView mName;
    @BindView(R.id.img_my_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.img_my_rank1)
    ImageView mrank1;
    @BindView(R.id.img_my_rank2)
    ImageView mrank2;
    @BindView(R.id.img_my_rank3)
    ImageView mrank3;
    @BindView(R.id.img_my_rank4)
    ImageView mrank4;
    @BindView(R.id.bt_my_exit)
    Button exit;

    private String json,avatar,rank;

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected void set() {

    }

    /**
     * 获取个人信息
     */
    private void loadInfo() {
        RetrofitClient.getInstance(getActivity()).createBaseApi().postData(URL.MY_BASE_INFO, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    json = responseBody.string();
                    InfoBean bean = new Gson().fromJson(json, InfoBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        setInfo(bean.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置个人信息
     */
    private void setInfo(InfoBean.DataBean bean) {
        mNickname.setText(getString(R.string.nickname) + bean.getRealname());
        mName.setText(getString(R.string.truename)+bean.getMobile());
        avatar = bean.getAvatar_path();
        Util.glide(avatar, mAvatar);
        rank = bean.getVip_level();

        if(!TextUtils.isEmpty(bean.getVip_level())){
            if(bean.getVip_level().equals("1")){
                mrank1.setVisibility(View.VISIBLE);
            }else if(bean.getVip_level().equals("2")){
                mrank1.setVisibility(View.VISIBLE);
                mrank2.setVisibility(View.VISIBLE);
            }else if(bean.getVip_level().equals("3")){
                mrank1.setVisibility(View.VISIBLE);
                mrank2.setVisibility(View.VISIBLE);
                mrank3.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.img_my_avatar, R.id.img_my_rank1, R.id.ll_my_info, R.id.ll_my_wallet,
            R.id.ll_my_collect, R.id.ll_my_history, R.id.ll_my_shop, R.id.ll_my_code,
            R.id.ll_my_share, R.id.ll_my_guide, R.id.ll_my_set,R.id.bt_my_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_my_avatar://头像
                Intent avatarIntent = new Intent(App.app, MyAvatarActivity.class);
                avatarIntent.putExtra("avatar",avatar);
                startActivityForResult(avatarIntent, ParamKey.MY_REQUEST);
                break;

            case R.id.img_my_rank1://会员级别
                Toast.makeText(mActivity, "会员级别"+rank, Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_my_info://个人资料
                Intent infoIntent = new Intent(getActivity(), MyInfoActivity.class);
                infoIntent.putExtra("json", json);
                startActivity(infoIntent);
                break;

            case R.id.ll_my_wallet://钱包
                Intent walletIntent = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(walletIntent);
                break;

            case R.id.ll_my_collect://收藏
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;

            case R.id.ll_my_history://历史
                startActivity(new Intent(getActivity(), HistoryActivity.class));
                break;

            case R.id.ll_my_shop://以购买的视频
                startActivity(new Intent(getActivity(), BuyVideoActivity.class));
                break;

            case R.id.ll_my_code://邀请码
                startActivity(new Intent(getActivity(), CodeActivity.class));
                break;

            case R.id.ll_my_share://分享
                startActivity(new Intent(getActivity(), ShareActivity.class));
                break;

            case R.id.ll_my_guide://用户指南
                startActivity(new Intent(getActivity(), UserGuideActivity.class));
                break;

            case R.id.ll_my_set://设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.bt_my_exit:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadInfo();
    }
}
