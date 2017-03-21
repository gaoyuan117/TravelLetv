package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PayActivity;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.my.VipBuyBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.Dialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class VipBuyActivity extends BaseActivity {

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.img_vip_buy_one)
    ImageView mVipBuyOne;
    @BindView(R.id.img_vip_buy_six)
    ImageView mVipBuySix;
    @BindView(R.id.img_vip_buy_year)
    ImageView mVipBuyYear;
    @BindView(R.id.tv_vip_buy_cunzhi)
    TextView mCunzhi;
    @BindView(R.id.img_vip_buy_rumen)
    ImageView imgVipBuyRumen;
    @BindView(R.id.tv_vip_buy_one)
    TextView tvVipBuyOne;
    @BindView(R.id.tv_vip_buy_oned)
    TextView tvVipBuyOned;
    @BindView(R.id.tv_vip_buy_money)
    TextView tvVipBuyMoney;
    @BindView(R.id.tv_vip_buy_six)
    TextView tvVipBuySix;
    @BindView(R.id.tv_vip_buy_sixd)
    TextView tvVipBuySixd;
    @BindView(R.id.tv_vip_buy_money_six)
    TextView tvVipBuyMoneySix;
    @BindView(R.id.tv_vip_buy_year)
    TextView tvVipBuyYear;
    @BindView(R.id.tv_vip_buy_yeard)
    TextView tvVipBuyYeard;
    @BindView(R.id.tv_vip_buy_money_year)
    TextView tvVipBuyMoneyYear;
    @BindView(R.id.tv_vip_amount)
    TextView mAmount;

    private Intent intent;
    private VipBuyBean.DataBean vipBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_buy);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getString(R.string.vip_recharge));
        intent = new Intent(this, PayActivity.class);
    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.m_finish, R.id.img_vip_buy_one, R.id.img_vip_buy_six, R.id.img_vip_buy_year})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_finish:
                this.finish();
                break;
            case R.id.img_vip_buy_one:
                maps.put("id",vipBean.getList().get(0).getId());
                buyVip();
                break;
            case R.id.img_vip_buy_six:
                maps.put("id",vipBean.getList().get(0).getId());
                buyVip();
                break;
            case R.id.img_vip_buy_year:
                maps.put("id",vipBean.getList().get(0).getId());
                buyVip();
                break;
        }
    }

    /**
     * 加载Vip购买信息
     */
    private void loadData() {
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.VIP_BUY, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                VipBuyBean bean = null;
                try {
                    bean = new Gson().fromJson(responseBody.string(), VipBuyBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        vipBean = bean.getData();
                        setInfo(bean.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 设置信息
     */
    private void setInfo(VipBuyBean.DataBean bean) {
        mAmount.setText(bean.getUser().getAmount());
        tvVipBuyOne.setText(bean.getList().get(0).getTitle());
        tvVipBuyOned.setText(bean.getList().get(0).getDescribe());
        tvVipBuyMoney.setText("￥" + bean.getList().get(0).getPrice());

        tvVipBuySix.setText(bean.getList().get(1).getTitle());
        tvVipBuySixd.setText(bean.getList().get(1).getDescribe());
        tvVipBuyMoneySix.setText("￥" + bean.getList().get(1).getPrice());

        tvVipBuyYear.setText(bean.getList().get(2).getTitle());
        tvVipBuyYeard.setText(bean.getList().get(2).getDescribe());
        tvVipBuyMoneyYear.setText("￥" + bean.getList().get(2).getPrice());
    }

    /**
     * 购买会员
     */
    private void buyVip() {
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.RECHARGE_VIP, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(), CommonBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        finish();
                    }else if(bean.getCode()==-3){
                        Dialog.rechargeDialog(VipBuyActivity.this);
                    }
                    Util.toast(bean.getMessage());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
