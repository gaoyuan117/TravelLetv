package com.jzbwlkj.travelletv.bean.navigation;

/**
 * Created by dn on 2017/2/25.
 */

public class ArticleContentBean {


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
        private String id;
        private String title;
        private String content;
        private String price;
        private Object image_id;
        private Object category_id;
        private String cre_time;
        private String up_time;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Object getImage_id() {
            return image_id;
        }

        public void setImage_id(Object image_id) {
            this.image_id = image_id;
        }

        public Object getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Object category_id) {
            this.category_id = category_id;
        }

        public String getCre_time() {
            return cre_time;
        }

        public void setCre_time(String cre_time) {
            this.cre_time = cre_time;
        }

        public String getUp_time() {
            return up_time;
        }

        public void setUp_time(String up_time) {
            this.up_time = up_time;
        }
    }
}
