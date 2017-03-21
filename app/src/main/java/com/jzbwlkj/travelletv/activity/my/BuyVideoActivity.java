package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.my.BuyAdapter;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.BuyVideoBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BuyVideoActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_buy_video)
    ListView mListView;
    @BindView(R.id.activity_buy_video)
    LinearLayout activityBuyVideo;

    private BuyAdapter mAdapter;
    private List<BuyVideoBean.DataBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_video);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getResources().getString(R.string.my_shop)).setFinish();
        mList = new ArrayList<>();
        mAdapter = new BuyAdapter(this, mList);
    }

    @Override
    protected void initData() {
        loadBuyVideo();
    }

    @Override
    protected void setData() {
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 加载已购买视频
     */
    private void loadBuyVideo() {
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.BUY_VIDEO, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    BuyVideoBean bean = new Gson().fromJson(responseBody.string(), BuyVideoBean.class);
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
        intent.putExtra("id", mList.get(position).getVid());
        Logger.d("id",mList.get(position).getVid());
        startActivity(intent);
    }
}
