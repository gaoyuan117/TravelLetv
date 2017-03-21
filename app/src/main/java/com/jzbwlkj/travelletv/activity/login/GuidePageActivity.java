package com.jzbwlkj.travelletv.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.adapter.ViewAdapter;
import com.jzbwlkj.travelletv.config.ParamKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidePageActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vp_guide_guide)
    ViewPager mViewPager;
    @BindView(R.id.activity_guide_page)
    RelativeLayout activityGuidePage;

    private ViewAdapter mAdapter;
    private List<View> mList;
    private ImageView mStart;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
        boolean isFirst = firstLogin();
        if (!isFirst) {//判断是否是第一次登陆
            startActivity(new Intent(this, DodgerActivity.class));
            finish();
            return;
        }

        init();
        initData();
        setData();
    }

    protected void init() {
        mList = new ArrayList<>();
        mAdapter = new ViewAdapter(mList);
    }

    protected void initData() {
        View guide1 = View.inflate(this, R.layout.guide1, null);
        View guide2 = View.inflate(this, R.layout.guide2, null);
        View guide3 = View.inflate(this, R.layout.guide3, null);
        View guide4 = View.inflate(this, R.layout.guide4, null);
        mStart = (ImageView) guide4.findViewById(R.id.img_guide_start);
        mList.add(guide1);
        mList.add(guide2);
        mList.add(guide3);
        mList.add(guide4);
        mAdapter.notifyDataSetChanged();
    }

    protected void setData() {
        mViewPager.setAdapter(mAdapter);
        mStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DodgerActivity.class));
        finish();
    }

    //判断是否是第一次登陆
    public boolean firstLogin() {
        sp = getSharedPreferences("first_login", MODE_PRIVATE);
        if (sp.getString("first", null) != null) {
            return false;
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("first", ParamKey.ONE);
            editor.commit();
            return true;
        }
    }
}
