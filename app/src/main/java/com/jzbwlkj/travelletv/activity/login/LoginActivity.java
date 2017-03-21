package com.jzbwlkj.travelletv.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.application.App;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.login.LoginBean;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.bean.login.RegisterBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.DynamicBaseUrlInterceptor;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.SharedPreference;
import com.jzbwlkj.travelletv.util.Util;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.ResponseBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_phone)
    EditText mLoginPhone;
    @BindView(R.id.et_login_pwd)
    EditText mLoginPwd;
    @BindView(R.id.tv_login_forget)
    TextView mLoginForget;
    @BindView(R.id.tv_login_register)
    TextView mLoginRegister;
    @BindView(R.id.rb_login_native)
    RadioButton mRbNative;
    @BindView(R.id.rb_login_net)
    RadioButton mRbNet;
    @BindView(R.id.rg_login_choose)
    RadioGroup mRgChoose;
    @BindView(R.id.bt_login_login)
    Button mLogin;
    @BindView(R.id.img_login_webo)
    ImageView mWebo;
    @BindView(R.id.img_login_weixin)
    ImageView mWeixin;
    @BindView(R.id.img_login_qq)
    ImageView mQq;

    public static Activity context = null;
    private String phone, password, openId;
    private SharedPreference sharedPreference;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Util.toast("openId :" + openId);
            postFastLogin();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected void init() {
        sharedPreference = new SharedPreference("user");
    }

    @Override
    protected void initData() {
        String phone = sharedPreference.get("phone");
        String password = sharedPreference.get("pwd");
        if (TextUtils.isEmpty(phone) && TextUtils.isEmpty(password)) {
            return;
        }
        mLoginPwd.setText(password);
        mLoginPhone.setText(phone);
    }

    @Override
    protected void setData() {

    }

    @OnClick({R.id.rb_login_native, R.id.rb_login_net, R.id.tv_login_forget, R.id.tv_login_register, R.id.bt_login_login, R.id.img_login_webo, R.id.img_login_weixin, R.id.img_login_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_login_native://本地服务器
                URL.BASE_URL = URL.NATIVE_BASE_URL;
                URL.IMG = URL.NATIVE_BASE_URL;
                RetrofitClient.changeApiBaseUrl(URL.NATIVE_BASE_URL);
                break;
            case R.id.rb_login_net://互联网
                URL.BASE_URL = URL.NET_BASE_URL;
                URL.IMG = URL.NET_BASE_URL;
                RetrofitClient.changeApiBaseUrl(URL.NET_BASE_URL);
                break;
            case R.id.tv_login_forget://忘记密码
                Intent pwdIntent = new Intent(this, RegisterActivity.class);
                pwdIntent.putExtra("type", "forgotten");
                startActivity(pwdIntent);
                break;
            case R.id.tv_login_register://注册
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", "register");
                startActivity(intent);
                break;
            case R.id.bt_login_login://登录
                phone = mLoginPhone.getText().toString().trim();
                password = mLoginPwd.getText().toString().trim();
                checkInput();
                login();
                break;
            case R.id.img_login_webo://微博
                fastLogin(SinaWeibo.NAME);
                break;
            case R.id.img_login_weixin://微信
                fastLogin(Wechat.NAME);
                break;
            case R.id.img_login_qq://qq
                fastLogin(QQ.NAME);
                break;
        }
        Logger.d(URL.BASE_URL);
    }

    /**
     * 登录
     */
    private void login() {
        Map<String, String> maps = new HashMap<>();
        maps.put("user", phone);
        maps.put("pwd", password);
        maps.put("driver", "android");
        RetrofitClient.getInstance(this, URL.BASE_URL).createBaseApi().postData(URL.LOGIN, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    LoginBean bean = new Gson().fromJson(responseBody.string(), LoginBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        URL.TOKEN = bean.getData().getToken();
                        Logger.d("token:" + URL.TOKEN);
                        saveUserInfo();//登陆信息保存到本地
                        App.user = phone;
                        judgeState(bean.getData().getIs_auth(), bean.getData().getIs_bond());//判断当前的登陆状态
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
     * 判断当前实名认证 保证金状态
     *
     * @param isAuth
     * @param isBond
     */
    private void judgeState(String isAuth, String isBond) {
        if (isAuth.equals("1")) {//以认证
            if (isBond.equals("1")) {//已交保证金
                startActivity(new Intent(this, MainActivity.class));
            } else {//以实名未交保证金
                //TODO 跳转到保证金页面
                startActivity(new Intent(this, AssureMoneyActivity.class));
            }
        } else {
            startActivity(new Intent(App.app, AuthenticationActivity.class));

        }
    }

    /**
     * 检验输入
     */
    private void checkInput() {
        if (TextUtils.isEmpty(phone)) {
            Util.toast(getString(R.string.input_phone));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Util.toast(getString(R.string.input_password));
            return;
        }

        if (phone.length() < 11) {
            Util.toast(getString(R.string.input_sure_phone));
            return;
        }

        if (password.length() < 6) {
            Util.toast(getString(R.string.password_length));
            return;
        }
    }

    /**
     * 保存个人信息
     */
    private void saveUserInfo() {
        ParamKey.PHONE = phone;
        sharedPreference
                .edit()
                .putString("phone", phone)
                .putString("pwd", password)
                .commit();
    }

    /**
     * 第三方登录，获取信息
     */
    private void fastLogin(String name) {
        Util.fastLogin(name, new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                openId = platform.getDb().getUserId();
                handler.sendEmptyMessage(22);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Util.toast(getString(R.string.login_fail));
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Util.toast(getString(R.string.login_fail));
            }
        });
    }

    /**
     * 第三方登录信息，上传到后台就行注册
     */
    private void postFastLogin() {
        maps.put("user", openId);
        maps.put("driver", "android");

        RetrofitClient.getInstance(this).createBaseApi().postData(URL.FAST_LOGIN, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    LoginBean bean = new Gson().fromJson(responseBody.string(), LoginBean.class);
                    Logger.d("openid:" + openId);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Util.toast(bean.getMessage());
                        URL.TOKEN = bean.getData().getToken();
                        judgeState(bean.getData().getIs_auth(), bean.getData().getIs_bond());
                    } else {
                        Util.toast(bean.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
