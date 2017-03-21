package com.jzbwlkj.travelletv.bean.my;

/**
 * Created by admin on 2017/3/13.
 */

public class BackStateBean {


        /**
         * code : -4
         * message : 申请失败
         * data : {"status":"3","fail_cause":"姓名和账号不匹配"}
         */

        private int code;
        private String message;
        private com.jzbwlkj.travelletv.bean.my.BackStateBean.DataBean data;

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

        public com.jzbwlkj.travelletv.bean.my.BackStateBean.DataBean getData() {
            return data;
        }

        public void setData(com.jzbwlkj.travelletv.bean.my.BackStateBean.DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * status : 3
             * fail_cause : 姓名和账号不匹配
             */

            private String status;
            private String fail_cause;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getFail_cause() {
                return fail_cause;
            }

            public void setFail_cause(String fail_cause) {
                this.fail_cause = fail_cause;
            }
        }
}
