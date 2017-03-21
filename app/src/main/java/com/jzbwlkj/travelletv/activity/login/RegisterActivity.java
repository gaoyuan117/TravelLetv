package com.jzbwlkj.travelletv.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.bean.login.VerifyBean;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.CountDownUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class RegisterActivity extends BaseActivity implements CountDownUtils.CountdownListener {

    @BindView(R.id.et_login_phone)
    EditText mPhone;
    @BindView(R.id.et_login_pwd)
    EditText mPwd;
    @BindView(R.id.get_verification_code)
    TextView mVerificationCode;
    @BindView(R.id.bt_register_register)
    Button mRegister;

    private Map<String, String> mMaps;
    private String phone, verify,verify2,title;//手机号 验证码 标题栏
    private String type;//type：register，forgotten，modifymobile
    private CountDownUtils countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mMaps = new HashMap<>();
        countDown = new CountDownUtils(mVerificationCode,"%s秒后重新获取",60);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("type");

        if(data.equals("register")){
            type = "register";
            title = getString(R.string.register);
        }else if(data.equals("forgotten")){
            type = "forgotten";
            title = getString(R.string.reset_password);
            mRegister.setText(getString(R.string.reset_password));
        }
        new TitleBar(this).setTitle(title);

    }

    @Override
    protected void setData() {
        countDown.setCountdownListener(this);

    }

    @OnClick({R.id.get_verification_code, R.id.bt_register_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_verification_code://获取验证码
                phone = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Util.toast(getString(R.string.input_phone));
                    return;
                }
                mVerificationCode.setEnabled(false);
                countDown.start();
                getVerificationCode();
                break;
            case R.id.bt_register_register://注册
                if (TextUtils.isEmpty(phone)) {
                    Util.toast(getString(R.string.input_phone));
                    return;
                }
                verify2 = mPwd.getText().toString();
                if (TextUtils.isEmpty(verify2)) {
                    Util.toast(getString(R.string.input_verification_code));
                    return;
                }
                if(!verify2.equals(verify)){
                    Util.toast("验证码错误");
                    return;
                }
                Intent intent = new Intent(this, SetPasswordActivity.class);
                intent.putExtra("phone", phone);
                intent.putExtra("code", verify);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerificationCode() {
        mMaps.put("user", phone);
        mMaps.put("type", type);
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.GET_VERIFICATION_CODE, mMaps, new BaseSubscriber<ResponseBody>(this) {

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    VerifyBean verifyBean = new Gson().fromJson(responseBody.string(), VerifyBean.class);
                    if (verifyBean.getCode() == 200) {
                        Logger.d(verifyBean.getData().getVerify());
                        verify = verifyBean.getData().getVerify();
                    } else {
                        Toast.makeText(RegisterActivity.this, verifyBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStartCount() {

    }

    @Override
    public void onFinishCount() {
        mVerificationCode.setEnabled(true);
        mVerificationCode.setText("重新获取验证码");
    }

    @Override
    public void onUpdateCount(int currentRemainingSeconds) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDown.isRunning()){
            countDown.stop();
        }
    }
}
