package com.jzbwlkj.travelletv.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.navigation.ArticleListActivity;
import com.jzbwlkj.travelletv.adapter.SearchAdapter;
import com.jzbwlkj.travelletv.adapter.navigation.ArticleActivity;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.home.SearchBean;
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
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SearchActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    @BindView(R.id.sp_search_type)
    Spinner mSpinner;
    @BindView(R.id.et_search_input)
    EditText mInput;
    @BindView(R.id.tv_search_search)
    TextView mSearch;
    @BindView(R.id.lv_search)
    ListView mListView;

    private List<SearchBean.DataBean.ListBean> mList;
    private SearchAdapter mAdapter;
    private String type, currentType, searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.search));
        mList = new ArrayList<>();
        mAdapter = new SearchAdapter(this, mList);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        mListView.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(this);
        mListView.setOnItemClickListener(this);
    }

    @OnClick(R.id.tv_search_search)
    public void onClick() {
        searchWord = mInput.getText().toString().trim();

        getSearchResult();
    }

    /**
     * 获取搜索结果
     */
    private void getSearchResult() {
        if (TextUtils.isEmpty(searchWord)) {
            Toast.makeText(this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
            return;
        }
        maps.put("sel_name", searchWord);
        maps.put("type", type);
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.SEARCH, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    SearchBean bean = new Gson().fromJson(responseBody.string(), SearchBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        mList.clear();
                        currentType = bean.getData().getType();
                        mList.addAll(bean.getData().getList());
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = (position + 1) + "";//搜索的类型  1：视频  2：文章
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.isEmpty(currentType)) {
            return;
        }
        if (currentType.equals("1")) {//视频
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            startActivity(intent);
        } else if (currentType.equals("2")) {//文章
            if (mList.get(position).getIs_pur() == 0) {//未购买
                showDialogBuy(position);
            } else {//以购买
                toArticleContent(position);
            }
        }
    }


    /**
     * 购买文章
     */
    private void buyArticle(final int i) {
        maps.put("art_id", mList.get(i).getId());
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.BUY_ARTICLE, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(), CommonBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {//购买成功跳转到文章页面
                        Util.toast(bean.getMessage());
                        getSearchResult();
                        toArticleContent(i);
                    } else {//购买失败
                        if (bean.getCode() == -3) {//余额不足
                            Dialog.rechargeDialog(SearchActivity.this);
                        } else {//其他
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
    private void toArticleContent(int i) {
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("title", mList.get(i).getTitle());
        intent.putExtra("id", mList.get(i).getId());
        toIntentActivity(intent);
    }

    /**
     * 显示购买对话框
     */
    private void showDialogBuy(final int i) {
        new Dialog(this).setMessage("阅读该文章需要支付" + mList.get(i).getPrice() + "元钱，是否购买？")
                .setPositiveClick("立即购买", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyArticle(i);
                    }
                }).showDialog();
    }
}
