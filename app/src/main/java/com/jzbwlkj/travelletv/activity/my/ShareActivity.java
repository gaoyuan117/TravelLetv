package com.jzbwlkj.travelletv.activity.my;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.base.BaseSubscriber2;
import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.my.ApkBean;
import com.jzbwlkj.travelletv.bean.my.UnionBean;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity {

    @BindView(R.id.bt_share)
    Button mShare;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bt_share)
    public void onClick() {
        if(TextUtils.isEmpty(url)){
            Util.toast("网络连接异常");
            return;
        }
        Util.share(this,url);
    }


    private void getApkUrl() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.getApkUrl(maps)
                .compose(RxUtils.<HttpResult<ApkBean>>io_main())
                .subscribe(new BaseSubscriber2<ApkBean>(this) {
                    @Override
                    public void onHandleSuccess(ApkBean apkBean) {
                        url = apkBean.getUrl();
                    }
                });
    }

    @Override
    protected void init() {
        new TitleBar(this).setTitle(getString(R.string.my_share));
    }

    @Override
    protected void initData() {
        getApkUrl();
    }

    @Override
    protected void setData() {

    }
}
