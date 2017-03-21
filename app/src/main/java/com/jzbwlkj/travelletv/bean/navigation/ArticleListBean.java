package com.jzbwlkj.travelletv.bean.navigation;

import java.util.List;

/**
 * Created by dn on 2017/2/25.
 */

public class ArticleListBean {

    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":"7","title":"经营好自己一生中的三天","price":"3.00"},{"id":"6","title":"价值20美金的时间","price":"20.00"},{"id":"5","title":"人生三大陷井","price":"10.00"},{"id":"4","title":"朋友就是一碗抄手的味道","price":"5.00"},{"id":"1","title":"8元5角钱的震撼","price":"8.50"}]
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
         * id : 7
         * title : 经营好自己一生中的三天
         * price : 3.00
         */

        private String id;
        private String title;
        private String price;
        private String is_pur;

        public String getIs_pur() {
            return is_pur;
        }

        public void setIs_pur(String is_pur) {
            this.is_pur = is_pur;
        }

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
