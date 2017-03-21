package com.jzbwlkj.travelletv.bean.my;

/**
 * Created by dn on 2017/2/24.
 */

public class InfoBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"realname":"刘继冰","sex":"1","birthday":"1989-09-20","mobile":"13639447983","avatar_path":""}
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
         * realname : 刘继冰
         * sex : 1
         * birthday : 1989-09-20
         * mobile : 13639447983
         * avatar_path :
         */

        private String realname;
        private String sex;
        private String birthday;
        private String mobile;
        private String avatar_path;
        private String is_vip;
        private String vip_level;

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getVip_level() {
            return vip_level;
        }

        public void setVip_level(String vip_level) {
            this.vip_level = vip_level;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar_path() {
            return avatar_path;
        }

        public void setAvatar_path(String avatar_path) {
            this.avatar_path = avatar_path;
        }
    }
}
