package com.jzbwlkj.travelletv.fragment.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.DownLoadActivity;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.activity.SearchActivity;
import com.jzbwlkj.travelletv.adapter.ViewPagerAdapter;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseFragment;
import com.jzbwlkj.travelletv.bean.LocationBean;
import com.jzbwlkj.travelletv.bean.SwitchBean;
import com.jzbwlkj.travelletv.bean.home.TabLayoutBean;
import com.jzbwlkj.travelletv.bean.navigation.NavigationBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.fragment.navigation.NavigationFragment;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxBus;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 作者：admin on 2017/2/10 15:47
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tab_fragment_home)
    TabLayout mTabLayout;
    @BindView(R.id.img_fragment_home_dh)
    ImageView mImg;
    @BindView(R.id.vp_fragment)
    ViewPager mViewPager;
    @BindView(R.id.tv_main_city)
    TextView mCity;
    @BindView(R.id.et_main_search)
    EditText mSearch;
    @BindView(R.id.img_main_down)
    ImageView mDown;

    private List<String> mTitles;
    private List<Fragment> mList;
    private ViewPagerAdapter mAdapter;
    private Subscription subscribe;


    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init(View view) {
        mTitles = new ArrayList<>();
        mList = new ArrayList<>();
        mAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), mList, mTitles);
    }

    @Override
    protected void initData() {
        loadTab();
        if(!TextUtils.isEmpty(App.address)){
            mCity.setText(App.address);
        }
    }

    @Override
    protected void set() {
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabTextColors(Color.BLACK, Color.RED);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);

        //接收DownLoadService发来的信息
        subscribe = RxBus.getInstance().toObserverable(LocationBean.class)
                .subscribe(new Action1<LocationBean>() {
                    @Override
                    public void call(LocationBean locationBean) {
                        mCity.setText(locationBean.address);
                    }
                });

    }

    /**
     * 加载Tablayout标题
     */
    private void loadTab() {
        RetrofitClient.getInstance(getActivity()).createBaseApi().postData(URL.GET_TABLAYOUT, maps, new BaseSubscriber<ResponseBody>(getActivity()) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    TabLayoutBean bean = new Gson().fromJson(responseBody.string(), TabLayoutBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        for (int i = 0; i < bean.getData().size(); i++) {//添加标题
                            mTitles.add(bean.getData().get(i).getName());
                        }

                        for (int i = 0; i < mTitles.size(); i++) {//添加碎片
                            if (i == 0) {
                                mList.add(new RecommendFragment());
                            } else {
                                HomeOtherFragment fragment = new HomeOtherFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("id", bean.getData().get(i).getId() + "");
                                fragment.setArguments(bundle);
                                mList.add(fragment);
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @OnClick({R.id.et_main_search, R.id.img_main_down, R.id.img_fragment_home_dh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_main_search://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.img_main_down://下载
                Intent intent = new Intent(getActivity(), DownLoadActivity.class);
                startActivity(intent);
                break;
            case R.id.img_fragment_home_dh://分类
                RxBus.getInstance().post(new SwitchBean("to"));//发送到MainActivity
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (!subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        super.onDestroy();
    }
}
