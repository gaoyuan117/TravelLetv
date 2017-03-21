package com.jzbwlkj.travelletv.activity.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.my.AboutUsBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_about_wx)
    TextView tvAboutWx;
    @BindView(R.id.tv_about_phone)
    TextView tvAboutPhone;
    @BindView(R.id.tv_about_email)
    TextView tvAboutEmail;
    @BindView(R.id.tv_about_zf)
    TextView tvAboutZf;
    @BindView(R.id.tv_about_web)
    TextView tvAboutWeb;
    @BindView(R.id.tv_about_version)
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        init();
    }


    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getString(R.string.about_us));
    }

    @Override
    protected void initData() {
        loadInfo();
    }

    @Override
    protected void setData() {

    }

    /**
     * 加载旅行乐视信息
     */
    private void loadInfo() {
        RetrofitClient.getInstance(this).postData(URL.ABOUT_US, maps, new BaseSubscriber<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    AboutUsBean bean = new Gson().fromJson(responseBody.string(), AboutUsBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        setInfo(bean.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setInfo(AboutUsBean.DataBean bean) {
        tvAboutWx.setText(bean.getWeixin());
        tvAboutEmail.setText(bean.getMail());
        tvAboutPhone.setText(bean.getPhone());
        tvAboutWeb.setText(bean.getOfficial_website());
        tvAboutZf.setText(bean.getShiwu());
        tvVersion.setText(bean.getVersion_number());
    }
}
