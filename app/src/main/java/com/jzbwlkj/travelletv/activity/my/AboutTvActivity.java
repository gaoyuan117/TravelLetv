package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutTvActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tv);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getResources().getString(R.string.about)).setFinish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.ll_guide_about_first, R.id.ll_guide_about_two})
    public void onClick(View view) {
        Intent intent = new Intent(this,SolutionActivity.class);
        intent.putExtra("title_bar",getString(R.string.about));
        switch (view.getId()) {
            case R.id.ll_guide_about_first:
                intent.putExtra("title",getString(R.string.who_tv));
                intent.putExtra("id","6");
                break;
            case R.id.ll_guide_about_two:
                intent.putExtra("title",getString(R.string.user_protocol));
                intent.putExtra("id","3");
                break;
        }
        startActivity(intent);
    }
}
