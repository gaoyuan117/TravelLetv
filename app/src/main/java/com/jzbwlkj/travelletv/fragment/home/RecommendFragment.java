package com.jzbwlkj.travelletv.fragment.home;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.RecommendAdapter;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseFragment;
import com.jzbwlkj.travelletv.bean.home.BannerBean;
import com.jzbwlkj.travelletv.bean.home.RecommendBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.HeaderGridView;
import com.jzbwlkj.travelletv.view.MyImageLoader;
import com.jzbwlkj.travelletv.view.MySwipeRefreshLayout;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dn on 2017/2/10.
 */

public class  RecommendFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, OnBannerClickListener {
    @BindView(R.id.lv_fg_recommend)
    HeaderGridView mGridView;
    @BindView(R.id.sr_fg_recommend)
    MySwipeRefreshLayout mRefeshView;

    private List<RecommendBean.DataBean> mList;
    private RecommendAdapter mAdapter;
    private Banner mBanner;
    private List<BannerBean.DataBean> mBannerPaths;

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init(View view) {
        mBannerPaths = new ArrayList<>();
        mList = new ArrayList<>();
        mAdapter = new RecommendAdapter(getActivity(), mList);
    }

    @Override
    protected void initData() {
        loadBanner();//加载Banner
        loadVideo();//加载视频
    }

    @Override
    protected void set() {
        addHead();
        mGridView.setAdapter(mAdapter);
        //设置刷新时动画的颜色，可以设置4个
        mRefeshView.setColorSchemeResources(android.R.color.holo_red_light);
        mRefeshView.setOnRefreshListener(this);
        mGridView.setOnItemClickListener(this);
        mBanner.setOnBannerClickListener(this);
    }

    /**
     * 刷新监听事件
     */
    @Override
    public void onRefresh() {
        loadVideo();
    }

    /**
     * GridView add HeadLayout
     */
    private void addHead() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_banner, null);
        mBanner = (Banner) view.findViewById(R.id.banner);
        mGridView.addHeaderView(view);
    }

    /**
     * 加载最新视频
     */
    private void loadVideo() {
        RetrofitClient.getInstance(getActivity()).postData(URL.HOME_NEW_VIDEO, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    RecommendBean bean = new Gson().fromJson(responseBody.string(), RecommendBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        mList.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Util.toast(bean.getMessage());
                    }
                    mRefeshView.setRefreshing(false);
                } catch (IOException e) {
                    e.printStackTrace();
                    mRefeshView.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 加载轮播图图片
     */
    private void loadBanner() {
        RetrofitClient.getInstance(getActivity()).postData(URL.BANNER, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    BannerBean bean = new Gson().fromJson(responseBody.string(), BannerBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mBannerPaths.addAll(bean.getData());
                        List<String> list = new ArrayList<String>();
                        for (int i = 0; i < mBannerPaths.size(); i++) {
                            list.add(URL.IMG + mBannerPaths.get(i).getImgpath());
                        }
                        setBanner(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * set Banner
     */
    private void setBanner(List<String> list) {

        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new MyImageLoader());
        //设置图片集合
        mBanner.setImages(list);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        mBanner.setViewPagerIsScroll(true);
        //设置轮播时间
        mBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();//开始轮播
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();//结束轮播
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        intent.putExtra("id", mList.get(position - 2).getId());
        startActivity(intent);
    }


    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), PlayActivity.class);
        intent.putExtra("id", mBannerPaths.get(position - 1).getVideo_id());
        Logger.d("id",mBannerPaths.get(position-1).getId());
        startActivity(intent);
    }
}
