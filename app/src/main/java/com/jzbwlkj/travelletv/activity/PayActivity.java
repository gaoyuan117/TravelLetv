package com.jzbwlkj.travelletv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.base.BaseActivity;
import com.jzbwlkj.travelletv.base.BaseSubscriber2;
import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.OrderBean;
import com.jzbwlkj.travelletv.bean.my.UnionBean;
import com.jzbwlkj.travelletv.bean.my.WxBean;
import com.jzbwlkj.travelletv.config.ParamKey;
import com.jzbwlkj.travelletv.config.URL;
import com.jzbwlkj.travelletv.retrofit.BaseSubscriber;
import com.jzbwlkj.travelletv.retrofit.RetrofitClient;
import com.jzbwlkj.travelletv.util.Constants;
import com.jzbwlkj.travelletv.util.RxUtils;
import com.jzbwlkj.travelletv.util.Util;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import java.util.ArrayList;
import java.util.List;

import alipay.AliPay;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {

    @BindView(R.id.m_finish)
    ImageView mFinish;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.rb_pay_wx)
    CheckBox mPayWx;
    @BindView(R.id.rb_pay_ali)
    CheckBox mPayAli;
    @BindView(R.id.rb_pay_yl)
    CheckBox mPayYl;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_pay_wx)
    TextView tvPayWx;
    @BindView(R.id.tv_pay_ali)
    TextView tvPayAli;
    @BindView(R.id.tv_pay_yl)
    TextView tvPayYl;
    @BindView(R.id.ll_pay_wx)
    LinearLayout llPayWx;
    @BindView(R.id.ll_pay_ali)
    LinearLayout llPayAli;
    @BindView(R.id.ll_pay_yl)
    LinearLayout llPayYl;

    private String type;//支付类型
    private List<TextView> mTextViews;
    private List<CheckBox> mRadioButtons;
    private List<String> mTypes;
    private String money, title, des, orderNo;//充值金额
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        mWxApi = WXAPIFactory.createWXAPI(this, Constants.WxPay.APP_ID, true);
        mTextViews = new ArrayList<>();
        mRadioButtons = new ArrayList<>();
        mTypes = new ArrayList<>();
        mTitle.setText(getResources().getString(R.string.recharge));
        //获取要支付的金额
        Intent intent = getIntent();
        if (intent != null) {
            money = intent.getStringExtra("money");
            Logger.d("money:" + money);
        }
    }

    @Override
    protected void initData() {
        mTextViews.add(tvPayWx);
        mTextViews.add(tvPayAli);
        mTextViews.add(tvPayYl);

        mRadioButtons.add(mPayWx);
        mRadioButtons.add(mPayAli);
        mRadioButtons.add(mPayYl);

        mTypes.add("2");//微信
        mTypes.add("1");//支付宝
        mTypes.add("3");//银联

    }

    @Override
    protected void setData() {
        tvPayMoney.setText("￥" + money);
    }

    @OnClick({R.id.m_finish, R.id.bt_pay_recharge, R.id.ll_pay_wx, R.id.ll_pay_ali, R.id.ll_pay_yl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_finish:
                this.finish();
                break;
            case R.id.bt_pay_recharge:
                RechargeActivity.recharge.finish();
                selectPay();
                break;
            case R.id.ll_pay_wx:
                choose(0);
                break;
            case R.id.ll_pay_ali:
                choose(1);
                break;
            case R.id.ll_pay_yl:
                choose(2);
                break;
        }
    }


    private void choose(int i) {
        for (int i1 = 0; i1 < mRadioButtons.size(); i1++) {
            if (i1 == i) {
                mTextViews.get(i1).setTextColor(getResources().getColor(R.color.red));
                type = mTypes.get(i1);
                mRadioButtons.get(i1).setChecked(true);
            } else {
                mTextViews.get(i1).setTextColor(Color.BLACK);
                mRadioButtons.get(i1).setChecked(false);
            }
        }
        Logger.d("支付类型：" + type);
    }


    /**
     * 获取支付宝订单编号
     */
    private void getOrderNo() {
        maps.put("amount", money);
        maps.put("type", type);
        maps.put("token", URL.TOKEN);
        RetrofitClient.getInstance(this).createBaseApi().apiService.getOrder(URL.GET_ORDER, maps)
                .compose(RxUtils.<OrderBean>io_main())
                .subscribe(new BaseSubscriber<OrderBean>() {
                    @Override
                    public void onNext(OrderBean orderBean) {
                        orderNo = orderBean.getData().getOrder_no();
                        alipay();
                    }
                });

    }

    /**
     * 选择支付方式
     */
    private void selectPay() {
        if (type.equals("1")) {
            getOrderNo();
        }
        if (type.equals("2")) {
            wxpay();
        }
        if (type.equals("3")) {
            unionPay();
        }
    }


    /**
     * 银行卡支付
     */
    private void unionPay() {
        //在调用支付控件的代码按以下方式调用支付控件
        //比如onclick或者handler等等...
        /*参数说明：
        activity —— 用于启动支付控件的活动对象
        spId —— 保留使用，这里输入null
        sysProvider —— 保留使用，这里输入null
        orderInfo —— 订单信息为交易流水号，即TN，为商户后台从银联后台获取。
        mode —— 银联后台环境标识，“00”将在银联正式环境发起交易,“01”将在银联测试环境发起交易
        返回值：
        UPPayAssistEx.PLUGIN_VALID —— 该终端已经安装控件，并启动控件
        UPPayAssistEx.PLUGIN_NOT_FOUND — 手机终端尚未安装支付控件，需要先安装支付控件
        */
        maps.put("amount", money);
        RetrofitClient.getInstance(this).createBaseApi().apiService.unionPay(maps)
                .compose(RxUtils.<UnionBean>io_main())
                .subscribe(new BaseSubscriber<UnionBean>() {
                    @Override
                    public void onNext(UnionBean orderBean) {
                        if (orderBean.getCode() == ParamKey.SUCCESS) {
                            String order_no = orderBean.getData().getTN();
                            String serverMode = "01";
                            UPPayAssistEx.startPay(PayActivity.this, null, null, order_no, serverMode);
                        } else {
                            Util.toast(orderBean.getMessage());
                        }
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxpay() {
        maps.put("amount", money);
        //从服务器获取信息，上传到微信
        RetrofitClient.getInstance(this).createBaseApi().apiService.wxpay(maps)
                .compose(RxUtils.<HttpResult<WxBean>>io_main())
                .subscribe(new BaseSubscriber2<WxBean>(this) {
                    @Override
                    public void onHandleSuccess(WxBean wxBean) {
                        PayReq req = new PayReq();
                        req.appId = wxBean.getAppid();
                        req.partnerId = wxBean.getPartnerid();
                        req.packageValue = wxBean.getPackageX();
                        req.sign = wxBean.getSign();
                        req.prepayId = wxBean.getPrepayid();
                        req.nonceStr = wxBean.getNoncestr();
                        req.timeStamp = wxBean.getTimestamp() + "";
                        mWxApi.sendReq(req);
                    }
                });
    }


    /**
     * 支付宝支付
     */
    private void alipay() {
         /* *
         * @param total_amount   订单总价
         * @param subject        标题
         * @param body           描述
         * @param order_trade_no 订单no
          * */

        new AliPay(this).payV2(money, "旅行乐视充值", "旅行乐视充值", orderNo, new AliPay.AlipayCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(PayActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                PayActivity.this.finish();
            }

            @Override
            public void onDeeling() {

            }

            @Override
            public void onCancle() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }


    /**
     * 银联支付回掉
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        Log.v("zftphone", "2 " + data.getExtras().getString("merchantOrderId"));
        if (str.equalsIgnoreCase("success")) {
            msg = "充值成功！";

        } else if (str.equalsIgnoreCase("fail")) {
            msg = "充值失败！";

        } else if (str.equalsIgnoreCase("cancel")) {

            msg = "取消充值";
        }
        //支付完成,处理自己的业务逻辑!
        Util.toast(msg);
    }

}