package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.my.CollectAdapter;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.CollectBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class MyCollectActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.m_more)
    ImageView mMore;
    @BindView(R.id.gv_my_collect)
    GridView mGridView;
    @BindView(R.id.activity_my_collect)
    LinearLayout activityMyCollect;

    private List<CollectBean.DataBean> mList;
    private CollectAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getString(R.string.my_collect));
        mList = new ArrayList<>();
        mAdapter = new CollectAdapter(this, mList);
    }

    @Override
    protected void initData() {
        getCollect();
    }

    @Override
    protected void setData() {
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @OnClick(R.id.m_finish)
    public void onClick() {
        this.finish();
    }

    /**
     * 获取收藏列表
     */
    private void getCollect() {
        RetrofitClient.getInstance(this).postData(URL.GET_COLLECT, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CollectBean bean = new Gson().fromJson(responseBody.string(), CollectBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        mList.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("id", mList.get(position).getVideo_id());
        startActivity(intent);
    }
}
