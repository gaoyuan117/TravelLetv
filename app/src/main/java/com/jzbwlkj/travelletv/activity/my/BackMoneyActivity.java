package com.jzbwlkj.travelletv.activity.my;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.base.BaseSubscriber2;
import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.login.CashBondBean;
import com.jzbwlkj.travelletv.bean.my.BackMoneyBean;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackMoneyActivity extends BaseActivity {

    @BindView(R.id.bt_tx_wx)
    RadioButton mWx;
    @BindView(R.id.bt_tx_zfb)
    RadioButton mZfb;
    @BindView(R.id.bt_tx_yhk)
    RadioButton mYhk;
    @BindView(R.id.et_tx_id)
    EditText mNo;
    @BindView(R.id.et_tx_name)
    EditText mName;
    @BindView(R.id.bt_tx_sure)
    Button mSure;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_money);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle("退还保证金");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.bt_tx_wx, R.id.bt_tx_zfb, R.id.bt_tx_yhk, R.id.bt_tx_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_tx_wx:
                type = "2";
                break;
            case R.id.bt_tx_zfb:
                type = "1";
                break;
            case R.id.bt_tx_yhk:
                type = "3";
                break;
            case R.id.bt_tx_sure:
                String no = mNo.getText().toString().trim();
                String name = mName.getText().toString().trim();
                if (TextUtils.isEmpty(type)) {
                    Util.toast("请选择退款方式");
                    return;
                }
                if (TextUtils.isEmpty(no)) {
                    Util.toast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Util.toast("请输入姓名");
                    return;
                }
                maps.put("number", no);
                maps.put("name", name);
                maps.put("type", type);
                backMoney();
                break;
        }
    }

    /**
     * 退还保证金
     */
    private void backMoney() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.backMoney(maps)
                .compose(RxUtils.<HttpResult<BackMoneyBean>>io_main())
                .subscribe(new BaseSubscriber2<BackMoneyBean>(this) {
                    @Override
                    public void onHandleSuccess(BackMoneyBean backMoneyBean) {
                        Util.toast("申请成功");
                        finish();
                    }
                });
    }
}
