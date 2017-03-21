package com.jzbwlkj.travelletv.bean.home;

import java.util.List;

/**
 * Created by gaoyuan on 2017/3/9.
 */

public class SearchBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"type":"1","list":[{"id":"27","title":"野生动物-mkv","cover_id":"1259","price":"10.00","imgpath":"/Uploads/Picture/2017-03-07/58be544339d89.png","is_pur":0}]}
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
         * type : 1
         * list : [{"id":"27","title":"野生动物-mkv","cover_id":"1259","price":"10.00","imgpath":"/Uploads/Picture/2017-03-07/58be544339d89.png","is_pur":0}]
         */

        private String type;
        private List<ListBean> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 27
             * title : 野生动物-mkv
             * cover_id : 1259
             * price : 10.00
             * imgpath : /Uploads/Picture/2017-03-07/58be544339d89.png
             * is_pur : 0
             */

            private String id;
            private String title;
            private String cover_id;
            private String price;
            private String imgpath;
            private int is_pur;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover_id() {
                return cover_id;
            }

            public void setCover_id(String cover_id) {
                this.cover_id = cover_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getImgpath() {
                return imgpath;
            }

            public void setImgpath(String imgpath) {
                this.imgpath = imgpath;
            }

            public int getIs_pur() {
                return is_pur;
            }

            public void setIs_pur(int is_pur) {
                this.is_pur = is_pur;
            }
        }
    }
}
