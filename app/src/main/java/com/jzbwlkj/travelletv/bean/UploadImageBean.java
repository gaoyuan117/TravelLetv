package com.jzbwlkj.travelletv.bean;

/**
 * Created by admin on 2017/2/25.
 */

public class UploadImageBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"avatar":{"name":"C360_2017-01-18-11-41-08-986.jpg","type":"image/jpeg","size":990484,"key":"avatar","ext":"jpg","md5":"1f6a81e7ebccaf7946a91ff945a704bb","sha1":"004d2553307b8bf9b8619f0023228c36b39ef931","savename":"58b11559a4663.jpg","savepath":"2017-02-25/","path":"/Uploads/Picture/2017-02-25/58b11559a4663.jpg","id":1225}}
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
         * avatar : {"name":"C360_2017-01-18-11-41-08-986.jpg","type":"image/jpeg","size":990484,"key":"avatar","ext":"jpg","md5":"1f6a81e7ebccaf7946a91ff945a704bb","sha1":"004d2553307b8bf9b8619f0023228c36b39ef931","savename":"58b11559a4663.jpg","savepath":"2017-02-25/","path":"/Uploads/Picture/2017-02-25/58b11559a4663.jpg","id":1225}
         */

        private AvatarBean avatar;

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public static class AvatarBean {
            /**
             * name : C360_2017-01-18-11-41-08-986.jpg
             * type : image/jpeg
             * size : 990484
             * key : avatar
             * ext : jpg
             * md5 : 1f6a81e7ebccaf7946a91ff945a704bb
             * sha1 : 004d2553307b8bf9b8619f0023228c36b39ef931
             * savename : 58b11559a4663.jpg
             * savepath : 2017-02-25/
             * path : /Uploads/Picture/2017-02-25/58b11559a4663.jpg
             * id : 1225
             */

            private String name;
            private String type;
            private int size;
            private String key;
            private String ext;
            private String md5;
            private String sha1;
            private String savename;
            private String savepath;
            private String path;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getExt() {
                return ext;
            }

            public void setExt(String ext) {
                this.ext = ext;
            }

            public String getMd5() {
                return md5;
            }

            public void setMd5(String md5) {
                this.md5 = md5;
            }

            public String getSha1() {
                return sha1;
            }

            public void setSha1(String sha1) {
                this.sha1 = sha1;
            }

            public String getSavename() {
                return savename;
            }

            public void setSavename(String savename) {
                this.savename = savename;
            }

            public String getSavepath() {
                return savepath;
            }

            public void setSavepath(String savepath) {
                this.savepath = savepath;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
