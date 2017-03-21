package com.jzbwlkj.travelletv.bean.navigation;

import java.util.List;

/**
 * 作者：admin on 2017/2/11 14:09
 */

public class NavigationBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"6","group":"video","name":"体育","cover_id":"1196","sort":"1","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd8a50c500.png"},{"id":"7","group":"video","name":"科技","cover_id":"1197","sort":"2","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd8c00138c.png"},{"id":"8","group":"video","name":"汽车","cover_id":"1198","sort":"3","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd8cec452b.png"},{"id":"9","group":"video","name":"时尚","cover_id":"1199","sort":"4","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd8fce3976.png"},{"id":"10","group":"video","name":"娱乐","cover_id":"1200","sort":"5","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd92210814.png"},{"id":"11","group":"video","name":"纪录片","cover_id":"1201","sort":"6","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd936c52fa.png"},{"id":"12","group":"video","name":"财经","cover_id":"1202","sort":"7","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd95605337.png"},{"id":"13","group":"video","name":"旅游","cover_id":"1203","sort":"8","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd998da287.png"},{"id":"14","group":"video","name":"文章","cover_id":"1204","sort":"9","create_time":"0","update_time":"0","imgpath":"/Uploads/Picture/2017-02-21/58abd9b360598.png"}]
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
         * id : 6
         * group : video
         * name : 体育
         * cover_id : 1196
         * sort : 1
         * create_time : 0
         * update_time : 0
         * imgpath : /Uploads/Picture/2017-02-21/58abd8a50c500.png
         */

        private String id;
        private String group;
        private String name;
        private String cover_id;
        private String sort;
        private String create_time;
        private String update_time;
        private String imgpath;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }
    }
}
