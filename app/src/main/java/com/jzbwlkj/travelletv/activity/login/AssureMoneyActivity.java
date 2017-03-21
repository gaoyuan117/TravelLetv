package com.jzbwlkj.travelletv.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.login.CashBondBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.Dialog;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssureMoneyActivity extends BaseActivity {

    @BindView(R.id.tv_assure_money)
    TextView mMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assure_money);
        ButterKnife.bind(this);
        new TitleBar(this).setTitle("交纳保证金");
    }

    public void onClick(View view) {
        cashBond();
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        getBond();
    }

    @Override
    protected void setData() {

    }

    /**
     * 获取需要交纳的保证金
     */
    private void getBond() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.getBond(maps)
                .compose(RxUtils.<CashBondBean>io_main())
                .subscribe(new BaseSubscriber<CashBondBean>(this) {
                    @Override
                    public void onNext(CashBondBean commonBean) {
                        if(commonBean.getCode()== ParamKey.SUCCESS){
                            mMoney.setText("¥ "+commonBean.getData().getMoney());
                        }
                    }
                });
    }

    /**
     * 交纳保证金
     */
    private void cashBond(){
        RetrofitClient.getInstance(this).createBaseApi().apiService.cashBond(maps)
                .compose(RxUtils.<CommonBean>io_main())
                .subscribe(new BaseSubscriber<CommonBean>(this) {
                    @Override
                    public void onNext(CommonBean commonBean) {
                        if(commonBean.getCode()==ParamKey.SUCCESS){
                            Util.toast("交纳保证金成功");
                            startActivity(new Intent(AssureMoneyActivity.this,MainActivity.class));
                            finish();
                        }else if(commonBean.getCode()==-3){
                            Dialog.rechargeDialog(AssureMoneyActivity.this);
                        }
                    }
                });

    }
}
