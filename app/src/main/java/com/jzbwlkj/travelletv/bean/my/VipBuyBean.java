package com.jzbwlkj.travelletv.bean.my;

import java.util.List;

/**
 * Created by admin on 2017/3/1.
 */

public class VipBuyBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"user":{"uid":"36","nickname":"冰冰","amount":"20000.00"},"list":[{"id":"1","title":"1个月会员","describe":"浅尝严选风·乐享88折","price":"88","discount":null,"time":null},{"id":"2","title":"6个月会员","describe":"进阶轻奢选·优享86折","price":"500","discount":null,"time":null},{"id":"3","title":"12个月会员","describe":"顶级壕品味·尊享6折","price":null,"discount":null,"time":null}]}
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
         * user : {"uid":"36","nickname":"冰冰","amount":"20000.00"}
         * list : [{"id":"1","title":"1个月会员","describe":"浅尝严选风·乐享88折","price":"88","discount":null,"time":null},{"id":"2","title":"6个月会员","describe":"进阶轻奢选·优享86折","price":"500","discount":null,"time":null},{"id":"3","title":"12个月会员","describe":"顶级壕品味·尊享6折","price":null,"discount":null,"time":null}]
         */

        private UserBean user;
        private List<ListBean> list;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class UserBean {
            /**
             * uid : 36
             * nickname : 冰冰
             * amount : 20000.00
             */

            private String uid;
            private String nickname;
            private String amount;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }

        public static class ListBean {
            /**
             * id : 1
             * title : 1个月会员
             * describe : 浅尝严选风·乐享88折
             * price : 88
             * discount : null
             * time : null
             */

            private String id;
            private String title;
            private String describe;
            private String price;
            private Object discount;
            private Object time;

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

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public Object getDiscount() {
                return discount;
            }

            public void setDiscount(Object discount) {
                this.discount = discount;
            }

            public Object getTime() {
                return time;
            }

            public void setTime(Object time) {
                this.time = time;
            }
        }
    }
}
