package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserGuideActivity extends BaseActivity {

    @BindView(R.id.ll_guide_account)
    LinearLayout mAccount;
    @BindView(R.id.ll_guide_about)
    LinearLayout mAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getResources().getString(R.string.my_guide)).setFinish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.ll_guide_account, R.id.ll_guide_about, R.id.ll_guide_complain, R.id.ll_guide_shuoming})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_guide_account://注册与账户
                startActivity(new Intent(this, GuideAccountActivity.class));
                break;
            case R.id.ll_guide_about://关于播放器
                startActivity(new Intent(this, AboutTvActivity.class));
                break;
            case R.id.ll_guide_complain://投诉和建议
                startActivity(new Intent(this, ComplainActivity.class));
                break;
            case R.id.ll_guide_shuoming:
                Intent intent = new Intent(this, SolutionActivity.class);
                intent.putExtra("title_bar", "优惠说明");
                intent.putExtra("title", "优惠说明");
                intent.putExtra("id", "12");
                startActivity(intent);
                break;
        }
    }
}
