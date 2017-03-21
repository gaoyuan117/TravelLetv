package com.jzbwlkj.travelletv.bean.login;

/**
 * Created by dn on 2017/2/19.
 */

public class VerifyBean {

    /**
     * code : 1
     * message : 操作成功
     * data : {"verify":"412152"}
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
         * verify : 412152
         */

        private String verify;

        public String getVerify() {
            return verify;
        }

        public void setVerify(String verify) {
            this.verify = verify;
        }
    }
}
