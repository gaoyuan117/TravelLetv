package com.jzbwlkj.travelletv.bean.my;

/**
 * Created by gaoyuan on 2017/3/11.
 */

public class UnionBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"TN":"640671235578535700201"}
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
         * TN : 640671235578535700201
         */

        private String TN;

        public String getTN() {
            return TN;
        }

        public void setTN(String TN) {
            this.TN = TN;
        }
    }
}
