package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.RechargeActivity;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.base.BaseSubscriber2;
import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.OrderBean;
import com.jzbwlkj.travelletv.bean.login.LoginBean;
import com.jzbwlkj.travelletv.bean.my.MyWalletBean;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity {

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.tv_my_wallet_balance)
    TextView mWalletBalance;
    @BindView(R.id.tv_my_wallet_recharge)
    TextView mWalletRecharge;
    @BindView(R.id.tv_my_wallet_vip)
    TextView mWalletVip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getString(R.string.my_wallet));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.m_finish, R.id.tv_my_wallet_recharge, R.id.tv_my_wallet_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_finish:
                this.finish();
                break;
            case R.id.tv_my_wallet_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.tv_my_wallet_vip:
                startActivity(new Intent(this, VipBuyActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletInfo();
    }

    /**
     * 获取钱包信息
     */
    private void getWalletInfo() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.getWalletInfo(maps)
                .compose(RxUtils.<HttpResult<MyWalletBean>>io_main())
                .subscribe(new BaseSubscriber2<MyWalletBean>(this) {
                    @Override
                    public void onHandleSuccess(MyWalletBean myWalletBean) {
                        mWalletBalance.setText("¥ " + myWalletBean.getAmount());
                    }
                });
    }

}
