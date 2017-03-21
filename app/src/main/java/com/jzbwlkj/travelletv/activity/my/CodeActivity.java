package com.jzbwlkj.travelletv.activity.my;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.base.BaseSubscriber2;
import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.my.ApkBean;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CodeActivity extends BaseActivity {

    @BindView(R.id.img_code_url)
    ImageView mCodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getResources().getString(R.string.my_code));
    }

    @Override
    protected void initData() {
        getApkUrl();
    }

    @Override
    protected void setData() {

    }

    private void getApkUrl() {
        RetrofitClient.getInstance(this).createBaseApi().apiService.getApkUrl(this.maps)
                .compose(RxUtils.<HttpResult<ApkBean>>io_main())
                .subscribe(new BaseSubscriber2<ApkBean>(this) {
                    @Override
                    public void onHandleSuccess(ApkBean apkBean) {
                        Bitmap code = Util.generateBitmap(apkBean.getUrl(), 200, 200);
                        mCodeImg.setImageBitmap(code);
                    }
                });
    }


}
