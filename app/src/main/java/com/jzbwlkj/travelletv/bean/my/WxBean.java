package com.jzbwlkj.travelletv.bean.my;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gaoyuan on 2017/3/11.
 */

public class WxBean {


        /**
         * appid : wx052f71622ceec18d
         * partnerid : 1440957102
         * prepayid : wx20170311122854854bc7ce950854069390
         * package : Sign=WXPay
         * noncestr : 9fuppVOZpoABgQOj2zoibRZ7LnmBxzNy
         * timestamp : 1489206534
         * sign : DE10DF5A64E007AD43DED5AB60031A5B
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

}
