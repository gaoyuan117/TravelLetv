package com.jzbwlkj.travelletv.bean.home;

import java.util.List;

/**
 * Created by dn on 2017/2/24.
 */

public class BannerBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"1","position":"","video_id":"1","title":"海贼王","cover_id":"1223","sort":"0","create_time":"1487815069","update_time":"1487815069","begin_time":"0","end_time":"0","content":"","imgpath":"/Uploads/Picture/2017-02-23/58ae4152bd51c.jpg"}]
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
         * position :
         * video_id : 1
         * title : 海贼王
         * cover_id : 1223
         * sort : 0
         * create_time : 1487815069
         * update_time : 1487815069
         * begin_time : 0
         * end_time : 0
         * content :
         * imgpath : /Uploads/Picture/2017-02-23/58ae4152bd51c.jpg
         */

        private String id;
        private String position;
        private String video_id;
        private String title;
        private String cover_id;
        private String sort;
        private String create_time;
        private String update_time;
        private String begin_time;
        private String end_time;
        private String content;
        private String imgpath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
    }
}
