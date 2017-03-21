package com.jzbwlkj.travelletv.activity.my;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class ComplainActivity extends BaseActivity {

    @BindView(R.id.et_complain_content)
    EditText mContent;
    @BindView(R.id.bt_complain_commit)
    Button mCommit;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.complain));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    /**
     * 提交投诉和建议
     */
    private void commitComplain() {
        maps.put("content", content);
        RetrofitClient.getInstance(this).postData(URL.COMPLAIN, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    CommonBean bean = new Gson().fromJson(responseBody.string(), CommonBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Util.toast(getString(R.string.commit));
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.bt_complain_commit,R.id.ll_complain})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.bt_complain_commit:
               content = mContent.getText().toString().trim();
               if (TextUtils.isEmpty(content)) {
                   Util.toast(getString(R.string.input_complain_content));
                   return;
               }
               if (content.length() < 20) {
                   Util.toast(getString(R.string.world_length));
                   return;
               }
               commitComplain();
               break;
           case R.id.ll_complain:
               Util.toggleSoftKeyboardState(this);
               break;
       }
    }
}
