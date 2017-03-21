package com.jzbwlkj.travelletv.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.ProtocolBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class SolutionActivity extends BaseActivity {

    @BindView(R.id.tv_solution_title)
    TextView mSolutionTitle;
    @BindView(R.id.web_solution_content)
    WebView mSolutionContent;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String titleBar = intent.getStringExtra("title_bar");
        String title = intent.getStringExtra("title");
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(titleBar)) {
            return;
        }
        mSolutionTitle.setText(title);
        new TitleBar(this).setFinish().setTitle(titleBar);

        id = intent.getStringExtra("id");
    }

    @Override
    protected void initData() {
        loadData();
    }

    @Override
    protected void setData() {
    }

    /**
     * 获取信息
     */
    private void loadData() {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        maps.put("id", id);
        RetrofitClient.getInstance(this).postData(URL.PROTOCOL_GUIDE, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    ProtocolBean bean = new Gson().fromJson(responseBody.string(), ProtocolBean.class);
                    if(bean.getCode()== ParamKey.SUCCESS){
                        mSolutionContent.loadDataWithBaseURL(null, Util.getNewContent(bean.getData().getContent()),"text/html","UTF-8",null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
