package com.jzbwlkj.travelletv.adapter.navigation;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.navigation.ArticleContentBean;
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

public class ArticleActivity extends BaseActivity {

    @BindView(R.id.web_article_content)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        String title = getIntent().getStringExtra("title");
        String id = getIntent().getStringExtra("id");
        maps.put("id", id);
        new TitleBar(this).setTitle(title);
    }

    @Override
    protected void initData() {
        loadArticleContent();
    }

    @Override
    protected void setData() {

    }

    /**
     * 加载文章内容
     */
    private void loadArticleContent() {
        RetrofitClient.getInstance(this).postData(URL.ARTICLECONTENT, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    ArticleContentBean bean = new Gson().fromJson(responseBody.string(), ArticleContentBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mWebView.loadDataWithBaseURL(null, Util.getNewContent(bean.getData().getContent()), "text/html", "UTF-8", null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
