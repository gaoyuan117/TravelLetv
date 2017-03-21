package com.jzbwlkj.travelletv.bean.home;

import java.util.List;

/**
 * Created by dn on 2017/2/10.
 */

public class RecommendBean {


    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"1","title":"海贼王","details":"海贼王动漫","image_id":"1207","size":null,"video_up_name":null,"video_up_path":null,"video_down_name":null,"video_down_path":null,"price":"100.00","cre_time":"0","up_time":null,"status":"1","imgpath":"/Uploads/Picture/2017-02-22/58ad3d4f4c901.jpg"}]
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
         * title : 海贼王
         * details : 海贼王动漫
         * image_id : 1207
         * size : null
         * video_up_name : null
         * video_up_path : null
         * video_down_name : null
         * video_down_path : null
         * price : 100.00
         * cre_time : 0
         * up_time : null
         * status : 1
         * imgpath : /Uploads/Picture/2017-02-22/58ad3d4f4c901.jpg
         */

        private String id;
        private String title;
        private String brief;
        private String image_id;
        private Object size;
        private Object video_up_name;
        private Object video_up_path;
        private Object video_down_name;
        private Object video_down_path;
        private String price;
        private String cre_time;
        private Object up_time;
        private String status;
        private String imgpath;

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

        public String getDetails() {
            return brief;
        }

        public void setDetails(String details) {
            this.brief = details;
        }

        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
        }

        public Object getSize() {
            return size;
        }

        public void setSize(Object size) {
            this.size = size;
        }

        public Object getVideo_up_name() {
            return video_up_name;
        }

        public void setVideo_up_name(Object video_up_name) {
            this.video_up_name = video_up_name;
        }

        public Object getVideo_up_path() {
            return video_up_path;
        }

        public void setVideo_up_path(Object video_up_path) {
            this.video_up_path = video_up_path;
        }

        public Object getVideo_down_name() {
            return video_down_name;
        }

        public void setVideo_down_name(Object video_down_name) {
            this.video_down_name = video_down_name;
        }

        public Object getVideo_down_path() {
            return video_down_path;
        }

        public void setVideo_down_path(Object video_down_path) {
            this.video_down_path = video_down_path;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCre_time() {
            return cre_time;
        }

        public void setCre_time(String cre_time) {
            this.cre_time = cre_time;
        }

        public Object getUp_time() {
            return up_time;
        }

        public void setUp_time(Object up_time) {
            this.up_time = up_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
    }
}
