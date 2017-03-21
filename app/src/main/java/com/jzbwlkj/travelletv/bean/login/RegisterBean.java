package com.jzbwlkj.travelletv.bean.login;

/**
 * Created by dn on 2017/2/20.
 */

public class RegisterBean {

    /**
     * code : 200
     * message : 注册成功
     * data : {"token":"0026bdd1626d45edbeae161afccd0186"}
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
         * token : 0026bdd1626d45edbeae161afccd0186
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
