package com.jzbwlkj.travelletv.bean.home;

import java.util.List;

/**
 * Created by admin on 2017/2/23.
 */

public class TabLayoutBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":1,"name":"推荐"},{"id":"6","name":"体育"},{"id":"7","name":"科技"},{"id":"8","name":"汽车"},{"id":"9","name":"时尚"},{"id":"10","name":"娱乐"},{"id":"11","name":"纪录片"},{"id":"12","name":"财经"},{"id":"13","name":"旅游"},{"id":"14","name":"文章"}]
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
         * name : 推荐
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
