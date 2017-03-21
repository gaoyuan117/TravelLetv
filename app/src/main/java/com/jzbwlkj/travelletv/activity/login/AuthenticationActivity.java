package com.jzbwlkj.travelletv.activity.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.activity.MainActivity;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.bean.login.RegisterBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Util;

import java.io.IOException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class AuthenticationActivity extends BaseActivity {

    @BindView(R.id.et_authentication_name)
    EditText mName;
    @BindView(R.id.et_authentication_birthday)
    EditText mBirthday;
    @BindView(R.id.et_authentication_phone)
    EditText mPhone;
    @BindView(R.id.et_authentication_id)
    EditText mId;
    @BindView(R.id.et_authentication_school)
    EditText mSchool;
    @BindView(R.id.et_authentication_student_id)
    EditText mStudentId;
    @BindView(R.id.bt_authentication_next)
    Button mNext;
    @BindView(R.id.activity_authentication)
    LinearLayout activityAuthentication;
    @BindView(R.id.rb_authentication_man)
    RadioButton mMan;
    @BindView(R.id.rb_authentication_woman)
    RadioButton mWoman;

    private String name, sex = ParamKey.ONE, date, phone, id, school, student_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    /**
     * 选择出生日期
     */
    private void chooseBirthday() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                mBirthday.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.show();
    }

    /**
     * 实名认证
     */
    private void authentication() {
        maps.put("realname", name);
        maps.put("sex", sex);
        maps.put("idcard", id);
        maps.put("mobile", phone);
        maps.put("birthday", date);

        if (!TextUtils.isEmpty(school)) {
            maps.put("school", school);
        }
        if (!TextUtils.isEmpty(student_id)) {
            maps.put("stu_id_card", student_id);
        }
        RetrofitClient.getInstance(this).createBaseApi().postData(URL.AUTHENTICATION, maps, new BaseSubscriber<ResponseBody>(this) {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    RegisterBean bean = new Gson().fromJson(responseBody.string(), RegisterBean.class);
                    if (bean.getCode() == ParamKey.SUCCESS) {
                        Util.toast(getString(R.string.commit));
                        //TODO 跳转到保证金页面
                        startActivity(new Intent(AuthenticationActivity.this, MainActivity.class));
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
     * 检查输入
     */
    private void checkInput() {
        if (TextUtils.isEmpty(name)) {
            Util.toast(getString(R.string.input_name));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Util.toast(getString(R.string.input_phone));
            return;
        }
        if ((TextUtils.isEmpty(id))) {
            Util.toast(getString(R.string.input_id));
            return;
        }
        if (TextUtils.isEmpty(date)) {
            Util.toast(getString(R.string.input_birthday));
        }
    }

    @OnClick({R.id.rb_authentication_man, R.id.rb_authentication_woman, R.id.bt_authentication_next, R.id.et_authentication_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_authentication_man://男
                sex = ParamKey.ONE;
                break;
            case R.id.rb_authentication_woman://女
                sex = ParamKey.ZERO;
                break;
            case R.id.bt_authentication_next://提交
                name = mName.getText().toString().trim();
                phone = mPhone.getText().toString().trim();
                id = mId.getText().toString().trim();
                school = mSchool.getText().toString().trim();
                student_id = mStudentId.getText().toString().trim();
                checkInput();
                authentication();
                break;
            case R.id.et_authentication_birthday:
                chooseBirthday();
                break;
        }
    }
}
