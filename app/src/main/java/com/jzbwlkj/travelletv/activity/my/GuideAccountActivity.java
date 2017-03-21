package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideAccountActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_account);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getResources().getString(R.string.register_account));
        intent = new Intent(this, SolutionActivity.class);
        intent.putExtra("title_bar", getResources().getString(R.string.register_account));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.ll_guide_account_first, R.id.ll_guide_account_two, R.id.ll_guide_account_three, R.id.ll_guide_account_four})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_guide_account_first:
                intent.putExtra("title", getResources().getString(R.string.my_certification));
                intent.putExtra("id","7");
                break;
            case R.id.ll_guide_account_two:
                intent.putExtra("title", getResources().getString(R.string.my_info_exist));
                intent.putExtra("id","8");
                break;
            case R.id.ll_guide_account_three:
                intent.putExtra("title", getResources().getString(R.string.my_invite_code));
                intent.putExtra("id","9");
                break;
            case R.id.ll_guide_account_four:
                intent.putExtra("title", getResources().getString(R.string.change_phone));
                intent.putExtra("id","10");
                break;
        }
        startActivity(intent);
    }
}
