package com.jzbwlkj.travelletv.bean.my;

/**
 * Created by admin on 2017/2/25.
 */

public class AboutUsBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"id":"1","cover_id":"1208","version_number":"V.0.0.0","weixin":"旅行乐视","phone":"111111111111","mail":"lvxingleshi@mmmm.com","shiwu":"ppppppppp@pp.com","official_website":"lvxingleshi.com","uptime":"1487901234","img_path":"/Uploads/Picture/2017-02-24/58af8b5be57e8.png"}
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
         * id : 1
         * cover_id : 1208
         * version_number : V.0.0.0
         * weixin : 旅行乐视
         * phone : 111111111111
         * mail : lvxingleshi@mmmm.com
         * shiwu : ppppppppp@pp.com
         * official_website : lvxingleshi.com
         * uptime : 1487901234
         * img_path : /Uploads/Picture/2017-02-24/58af8b5be57e8.png
         */

        private String id;
        private String cover_id;
        private String version_number;
        private String weixin;
        private String phone;
        private String mail;
        private String shiwu;
        private String official_website;
        private String uptime;
        private String img_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCover_id() {
            return cover_id;
        }

        public void setCover_id(String cover_id) {
            this.cover_id = cover_id;
        }

        public String getVersion_number() {
            return version_number;
        }

        public void setVersion_number(String version_number) {
            this.version_number = version_number;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getShiwu() {
            return shiwu;
        }

        public void setShiwu(String shiwu) {
            this.shiwu = shiwu;
        }

        public String getOfficial_website() {
            return official_website;
        }

        public void setOfficial_website(String official_website) {
            this.official_website = official_website;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }
    }
}
