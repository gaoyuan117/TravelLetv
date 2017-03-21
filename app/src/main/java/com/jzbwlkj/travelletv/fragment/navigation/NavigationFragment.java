package com.jzbwlkj.travelletv.fragment.navigation;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.navigation.ArticleListActivity;
import com.jzbwlkj.travelletv.activity.navigation.VideoActivity;
import com.jzbwlkj.travelletv.adapter.navigation.NavigationGvAdapter;
import com.jzbwlkj.travelletv.base.BaseFragment;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.bean.navigation.NavigationBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * 作者：admin on 2017/2/10 15:48
 */

public class

NavigationFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.gv_fragment_ng)
    GridView mGridView;

    private List<NavigationBean.DataBean> mList;
    private NavigationGvAdapter mAdapter;

    @Override
    protected View loadLayout(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_navigation, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init(View view) {
        mList = new ArrayList<>();
        mAdapter = new NavigationGvAdapter(getActivity(), mList);
    }

    @Override
    protected void initData() {
        getNavigation();
    }

    @Override
    protected void set() {
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 获取分类列表
     */
    private void getNavigation() {
        RetrofitClient.getInstance(getActivity()).createBaseApi().postData(URL.NAVIGATION, maps, new BaseSubscriber<ResponseBody>(getActivity()) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    NavigationBean bean = new Gson().fromJson(responseBody.string(), NavigationBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        mList.addAll(bean.getData());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //如果是文章就跳转到文章列表，否则跳转到视频分类
        if (mList.get(i).getName().equals(getString(R.string.article))) {
            startActivity(new Intent(getActivity(), ArticleListActivity.class));
        } else {
            Intent intent = new Intent(getActivity(), VideoActivity.class);
            intent.putExtra("id", mList.get(i).getId());
            intent.putExtra("type", mList.get(i).getName());
            startActivity(intent);
        }
    }
}
