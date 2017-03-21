package com.jzbwlkj.travelletv.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jzbwlkj.travelletv.R;
import com.jzbwlkj.travelletv.util.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
  private static final String TAG = "WXPayEntryActivity";

  private IWXAPI api;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pay_result);

    api = WXAPIFactory.createWXAPI(this, Constants.WxPay.APP_ID);
    api.handleIntent(getIntent(), this);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    api.handleIntent(intent, this);
  }

  @Override
  public void onReq(BaseReq req) {
  }

  @Override
  public void onResp(BaseResp resp) {
    Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
    Toast.makeText(this, "code:"+resp.errCode, Toast.LENGTH_SHORT).show();
    finish();
    if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//      WxPay.getWxPay().onRes(resp.errCode);
    }
  }
}