package com.jzbwlkj.travelletv.bean.my;

import java.util.List;

/**
 * Created by gaoyuan on 2017/3/8.
 */

public class BuyVideoBean {


    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"1","uid":"3","vid":"1","buy_price":"50.00","ctime":"2017-01-19 16:04","name":"海贼王","imgpath":"/Uploads/Picture/2017-02-22/58ad3d4f4c901.jpg"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * uid : 3
         * vid : 1
         * buy_price : 50.00
         * ctime : 2017-01-19 16:04
         * name : 海贼王
         * imgpath : /Uploads/Picture/2017-02-22/58ad3d4f4c901.jpg
         */

        private String id;
        private String uid;
        private String vid;
        private String buy_price;
        private String ctime;
        private String name;
        private String imgpath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
    }
}
