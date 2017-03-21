package com.jzbwlkj.travelletv.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {
    public static Activity recharge;

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.m_more)
    ImageView mMore;
    @BindView(R.id.et_recharge_money)
    EditText mEditTextMoney;
    @BindView(R.id.bt_recharge_sure)
    Button mButtonSure;
    @BindView(R.id.activity_recharge)
    LinearLayout activityRecharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        recharge = this;
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getString(R.string.recharge));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.m_finish, R.id.bt_recharge_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_recharge_sure:
                String money = mEditTextMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra("money", money);
                startActivity(intent);
                break;
            case R.id.m_finish:
                this.finish();
                break;
        }
    }


}
