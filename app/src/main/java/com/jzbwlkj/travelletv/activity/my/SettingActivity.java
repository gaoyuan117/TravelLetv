package com.jzbwlkj.travelletv.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.BackStateBean;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.Dialog;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.ll_set_check)
    LinearLayout mSetCheck;
    @BindView(R.id.ll_set_about)
    LinearLayout llSetAbout;
    @BindView(R.id.ll_set_protocol)
    LinearLayout llSetProtocol;
    @BindView(R.id.ll_set_recharge)
    LinearLayout llSetRecharge;
    @BindView(R.id.ll_set_money)
    LinearLayout llSetMoney;
    @BindView(R.id.ll_set_exit)
    LinearLayout llSetExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getString(R.string.my_set));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }


    @OnClick({R.id.ll_set_check, R.id.ll_set_about, R.id.ll_set_protocol, R.id.ll_set_recharge, R.id.ll_set_money, R.id.ll_set_exit})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_set_check://检测新版本
                Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_set_about://关于我们
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.ll_set_protocol://用户协议
                intent = new Intent(this, SolutionActivity.class);
                intent.putExtra("title_bar", getString(R.string.user_protocol));
                intent.putExtra("title", getString(R.string.user_protocol));
                intent.putExtra("id", "3");
                break;
            case R.id.ll_set_recharge://充值协议
                intent = new Intent(this, SolutionActivity.class);
                intent.putExtra("title_bar", getString(R.string.recharge_protocol));
                intent.putExtra("title", getString(R.string.recharge_protocol));
                intent.putExtra("id", "2");
                break;
            case R.id.ll_set_money://保证金协议
                intent = new Intent(this, SolutionActivity.class);
                intent.putExtra("title_bar", getString(R.string.money_protocol));
                intent.putExtra("title", getString(R.string.money_protocol));
                intent.putExtra("id", "1");
                break;
            case R.id.ll_set_exit://退还保证金
                backState();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 退还保证金的时候显示对话框
     */
    private void showDialog() {
        new Dialog(this)
                .setTitle("退还保证金")
                .setMessage("确定退还保证金？")
                .setPositiveClick("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SettingActivity.this, "退还", Toast.LENGTH_SHORT).show();
                    }
                }).showDialog();
    }

    /**
     * 当前退还保证金的状态
     */
    private void backState() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.backState(maps)
                .compose(RxUtils.<BackStateBean>io_main())
                .subscribe(new BaseSubscriber<BackStateBean>(this) {
                    @Override
                    public void onNext(BackStateBean backStateBean) {
                        if (backStateBean.getCode() == 1) {//未申请过
                            startActivity(new Intent(SettingActivity.this, BackMoneyActivity.class));
                        } else if (backStateBean.getCode() == -2) {//正在处理中
                            Util.toast(backStateBean.getMessage());
                        } else if (backStateBean.getCode() == -4) {//申请失败
                            Util.toast("退还失败："+backStateBean.getData().getFail_cause());
                            startActivity(new Intent(SettingActivity.this, BackMoneyActivity.class));
                        }
                    }
                });
    }
}
