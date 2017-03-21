package com.jzbwlkj.travelletv.retrofit;

import com.jzbwlkj.travelletv.base.HttpResult;
import com.jzbwlkj.travelletv.bean.CommonBean;
import com.jzbwlkj.travelletv.bean.OrderBean;
import com.jzbwlkj.travelletv.bean.login.CashBondBean;
import com.jzbwlkj.travelletv.bean.my.ApkBean;
import com.jzbwlkj.travelletv.bean.my.BackMoneyBean;
import com.jzbwlkj.travelletv.bean.my.BackStateBean;
import com.jzbwlkj.travelletv.bean.my.MyWalletBean;
import com.jzbwlkj.travelletv.bean.my.UnionBean;
import com.jzbwlkj.travelletv.bean.my.WxBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by dn on 2017/2/19.
 */

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps, @Field("token") String token);

    @Multipart
    @POST
    Observable<ResponseBody> postSingleImg(@Url String url, @Part MultipartBody.Part body, @Part("token") RequestBody tokenBody);

    @Multipart
    @POST
    Observable<ResponseBody> postMultiImg(@Url String url, @Part List<MultipartBody.Part> partList);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<OrderBean> getOrder(@Url String url, @FieldMap Map<String, String> maps);

    /**
     * 获取钱包信息
     **/
    @FormUrlEncoded
    @POST("api/User/Mywallet")
    Observable<HttpResult<MyWalletBean>> getWalletInfo(@FieldMap Map<String, String> maps);

    /**
     * 交纳保证金
     **/
    @FormUrlEncoded
    @POST("api/User/cashBond")
    Observable<CommonBean> cashBond(@FieldMap Map<String, String> maps);

    /**
     * 获取保证金金额
     **/
    @FormUrlEncoded
    @POST("api/User/getBond")
    Observable<CashBondBean> getBond(@FieldMap Map<String, String> maps);

    /**
     * 退还保证金
     **/
    @FormUrlEncoded
    @POST("api/User/returnBond")
    Observable<HttpResult<BackMoneyBean>> backMoney(@FieldMap Map<String, String> maps);

    /**
     * 退还保证金的状态
     **/
    @FormUrlEncoded
    @POST("api/User/returnBondstatus")
    Observable<BackStateBean> backState(@FieldMap Map<String, String> maps);

    /**
     * 退还保证金的状态
     **/
    @FormUrlEncoded
    @POST("api/Wechat/wechpay")
    Observable<HttpResult<WxBean>> wxpay(@FieldMap Map<String, String> maps);

    /**
     * 银联支付获取TN
     **/
    @FormUrlEncoded
    @POST("api/Pay/achieveTN")
    Observable<UnionBean> unionPay(@FieldMap Map<String, String> maps);

    /**
     * 银联支付验签
     **/
    @FormUrlEncoded
    @POST("api/Pay/VerifyAppData")
    Observable<UnionBean> verifyUndion(@FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST("api/Public/getUploadsurl")
    Observable<HttpResult<ApkBean>> getApkUrl(@FieldMap Map<String, String> maps);


}
