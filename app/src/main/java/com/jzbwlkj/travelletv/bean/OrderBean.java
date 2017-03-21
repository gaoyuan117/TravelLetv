package com.jzbwlkj.travelletv.bean;

/**
 * Created by gaoyuan on 2017/3/9.
 */

public class OrderBean {


    /**
     * code : 200
     * message : 操作成功
     * data : {"order_no":"2017030919866","amount":"10"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_no : 2017030919866
         * amount : 10
         */

        private String order_no;
        private String amount;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
