package com.jzbwlkj.travelletv.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.OtherAdapter;
import com.jzbwlkj.travelletv.adapter.RecommendAdapter;
import com.jzbwlkj.travelletv.base.BaseFragment;
import com.jzbwlkj.travelletv.bean.home.RecommendBean;
import com.jzbwlkj.travelletv.bean.my.VideoBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.MySwipeRefreshLayout;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2017/3/3.
 */

public class HomeOtherFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {


    @BindView(R.id.lv_fg_other)
    GridView mGridView;
    @BindView(R.id.sr_fg_recommend)
    MySwipeRefreshLayout mSwipeRefresh;

    private List<VideoBean.DataBean.ListBean> mList;
    private OtherAdapter mAdapter;

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_other, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init(View view) {
        mList = new ArrayList<>();
        mAdapter = new OtherAdapter(getActivity(), mList);
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            String id = arguments.getString("id");
            maps.put("id", id);
        }
        loadVideoList();
    }

    @Override
    protected void set() {
        mGridView.setAdapter(mAdapter);
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(this);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 加载视频列表
     */
    private void loadVideoList() {
        RetrofitClient.getInstance(getActivity()).postData(URL.VIDEOLIST, maps, new BaseSubscriber<ResponseBody>() {

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    VideoBean bean = new Gson().fromJson(responseBody.string(), VideoBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        mList.addAll(bean.getData().getList());
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadVideoList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        intent.putExtra("id", mList.get(position).getId());
        startActivity(intent);
    }
}
