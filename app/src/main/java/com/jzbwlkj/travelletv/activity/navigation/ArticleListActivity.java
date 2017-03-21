package com.jzbwlkj.travelletv.activity.navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.PlayActivity;
import com.jzbwlkj.travelletv.adapter.navigation.ArticleActivity;
import com.jzbwlkj.travelletv.adapter.navigation.ArticleListAdapter;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.my.InfoBean;
import com.jzbwlkj.travelletv.bean.navigation.ArticleListBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.Dialog;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ArticleListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_article_list)
    ListView mListView;

    private List<ArticleListBean.DataBean> mList;
    private ArticleListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.article));
        mList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(this, mList);
    }

    @Override
    protected void initData() {
        loadArticleList();
    }

    @Override
    protected void setData() {
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 加载文章列表
     */
    private void loadArticleList() {
        RetrofitClient.getInstance(this).postData(URL.ARTICLELIST, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    ArticleListBean bean = new Gson().fromJson(responseBody.string(), ArticleListBean.class);
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

    /**
     * 购买文章
     */
    private void buyArticle(final int i){
        maps.put("art_id",mList.get(i).getId());
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.BUY_ARTICLE, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(),CommonBean.class);
                    if(bean.getCode()==ParamKey.SUCCESS){//购买成功跳转到文章页面
                        Util.toast(bean.getMessage());
                        loadArticleList();//刷新一下
                        toArticleContent(i);
                    }else {//购买失败
                        if(bean.getCode()==-3){//余额不足
                            Dialog.rechargeDialog(ArticleListActivity.this);
                        }else {//其他
                            Util.toast(bean.getMessage());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 跳转到文章详情页
     */
    private void toArticleContent(int i){
        Intent intent = new Intent(ArticleListActivity.this, ArticleActivity.class);
        intent.putExtra("title",mList.get(i).getTitle());
        intent.putExtra("id",mList.get(i).getId());
        toIntentActivity(intent);
    }

    /**
     * 显示购买对话框
     */
    private void showDialogBuy(final int i){
        new Dialog(this).setMessage("阅读该文章需要支付"+mList.get(i).getPrice()+"元钱，是否购买？如果您是会员或者充值金额达到一定数目，会有打折优惠，具体优惠政策请到用户指南中查看。")
                .setPositiveClick("立即购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyArticle(i);
                    }
                }).showDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(mList.get(i).getIs_pur().equals("0")){//未购买
            showDialogBuy(i);
        }else {//以购买
            toArticleContent(i);
        }
    }
}
