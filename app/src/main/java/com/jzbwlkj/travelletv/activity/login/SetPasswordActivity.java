package com.jzbwlkj.travelletv.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.bean.login.RegisterBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;
import com.jzbwlkj.travelletv.view.TitleBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SetPasswordActivity extends BaseActivity {

    @BindView(R.id.et_set_password_pwd)
    EditText mPasswordPwd;
    @BindView(R.id.et_set_password_repwd)
    EditText mRepwd;
    @BindView(R.id.bt_set_password_register)
    Button mRegister;

    private String phone, password, rePassword, code, type, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        new TitleBar(this).setFinish().setTitle(getString(R.string.register));
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
        type = intent.getStringExtra("type");

        if (type.equals("register")) {
            title = getString(R.string.register);
        } else if (type.equals("forgotten")) {
            title = getString(R.string.reset_password);
            mRegister.setText(getString(R.string.reset_password));
        }
        new TitleBar(this).setTitle(title);
    }

    @Override
    protected void setData() {

    }

    @OnClick(R.id.bt_set_password_register)
    public void onClick() {
        password = mPasswordPwd.getText().toString().trim();
        rePassword = mRepwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Util.toast(getString(R.string.input_password));
            return;
        }
        if (!password.equals(rePassword)) {
            Util.toast(getString(R.string.input_two_password));
            return;
        }
        if (type.equals("register")) {
            register();
        } else {
            resetPassword();
        }
    }

    /**
     * 注册
     */
    private void register() {
        maps.put("user", phone);
        maps.put("pwd", password);
        maps.put("repwd", rePassword);
        maps.put("code", code);
        maps.put("driver", "android");

        RetrofitClient.getInstance(this).postData(URL.REGISTER, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    RegisterBean bean = new Gson().fromJson(responseBody.string(), RegisterBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        URL.TOKEN = bean.getData().getToken();
                        Util.toast(bean.getMessage());
                        startActivity(new Intent(App.app, LoginActivity.class));
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        maps.put("user", phone);
        maps.put("pwd", password);
        maps.put("repwd", rePassword);
        maps.put("code", code);
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.RESET_PASSWORD, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    RegisterBean bean = new Gson().fromJson(responseBody.string(), RegisterBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Util.toast(getString(R.string.reset_password_success));
                        startActivity(new Intent(SetPasswordActivity.this, LoginActivity.class));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
