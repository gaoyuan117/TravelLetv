package com.jzbwlkj.travelletv.activity.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.navigation.VideoAdapter;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.VideoBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.HeaderGridView;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class VideoActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.gv_video_navigation)
    HeaderGridView mGridView;

    private List<VideoBean.DataBean.ListBean> mList;
    private VideoAdapter mAdapter;
    private ImageView headImg;
    private VideoBean.DataBean.TjlistBean tjlistBean;//推荐类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.head_video, null);
        headImg = (ImageView) view.findViewById(R.id.img_head_video);
        mGridView.addHeaderView(view);
        mList = new ArrayList<>();
        mAdapter = new VideoAdapter(this, mList);
    }

    @Override
    protected void initData() {
        String type = getIntent().getStringExtra("type");
        String id = getIntent().getStringExtra("id");
        maps.put("id", id);
        new TitleBar(this).setTitle(type);
        loadVideoList();
    }

    @Override
    protected void setData() {
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        headImg.setOnClickListener(this);
    }

    /**
     * 加载视频列表
     */
    private void loadVideoList() {
        RetrofitClient.getInstance(this).postData(URL.VIDEOLIST, maps, new BaseSubscriber<ResponseBody>(this) {

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    VideoBean bean = new Gson().fromJson(responseBody.string(), VideoBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        mList.addAll(bean.getData().getList());
                        mAdapter.notifyDataSetChanged();
                        if (bean.getData().getTjlist() != null && bean.getData().getTjlist().size() > 0) {
                            tjlistBean = bean.getData().getTjlist().get(0);
                            if (tjlistBean != null) {
                                headImg.setVisibility(View.VISIBLE);
                                Util.glide(tjlistBean.getImgpath(), headImg);
                            }
                        }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("id", mList.get(position - 3).getId());//添加头布局后，position会改变，减掉GridView的列数
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("id", tjlistBean.getId());//添加头布局后，position会改变，减掉GridView的列数
        startActivity(intent);
    }
}
