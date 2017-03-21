package com.jzbwlkj.travelletv.bean.my;

import java.util.List;

/**
 * Created by dn on 2017/2/26.
 */

public class VideoBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"id":"2","title":"航海王：发条岛的冒险","image_id":"1234","details":"草帽海贼团的船\u201c黄金梅利号\u201d在悠闲度假大伙眼前被偷走了，后来他们在寻找船的途中遇到一对小偷兄弟，从他们口中打听到小偷是现在统治着发条岛的\u201c扑克牌海贼团\u201d，而小偷兄弟正好也要去偷取岛上著名的\u201c钻石钟\u201d，双方的目标一致，决定联手攻进扑克牌海贼团的大本营，但可怕的头目\u201c大熊王\u201d手中却掌握著攸关整座发条岛生死的巨大发条\u2026\u2026","price":"110.00","imgpath":"/Uploads/Picture/2017-02-28/58b4c941425b8.jpg"},{"id":"3","title":"航海王：珍兽岛的乔巴王国","image_id":"1235","details":"在伟大的航道上，路飞一行人朝着目的地前进\u2026这一次所要去的地方是娜美前阵子所得到手的地图珍兽岛，传说有的宝物在那里。在大家像平时一样在船上悠闲的做着自己的事时，海中的火山爆发，海水向上喷发，把一座小岛围了起来，原来，珍兽岛已经到了。在一阵混乱中，乔巴从船上飞了出去\u2026 传说中的珍兽岛是个动物的天堂，岛上的居民（都是动物\u2026）在王的领导之下快乐的生活着但是王却在最近去世了，掉在珍兽岛的乔巴，被岛上居民认为是新任的王乔巴等待其它人来找他，乔巴只好答应先暂时当珍兽岛的王。在珍兽岛的居民快乐的迎接新任的王的同时，一个以岛上动物为目标的黑暗人物逐渐逼近\u2026","price":"120.00","imgpath":"/Uploads/Picture/2017-02-28/58b4c94c189c0.jpg"},{"id":"4","title":"航海王：死亡尽头的冒险","image_id":"1236","details":"草帽海贼团来到汉那巴尔，吃饭的时候娜美注意到老板正和某位海贼在做可疑的交易，一问之下才知道，地下赌场正在举办每年一次的死亡海上竞赛，而且今年的奖金高达3亿贝里，为了寻求奖金和冒险的一行决定参加这场比赛。","price":"130.00","imgpath":"/Uploads/Picture/2017-02-28/58b4c95581970.jpg"}],"tjlist":[{"id":"1","title":"航海王：黄金岛冒险","image_id":"1233","details":"传说中的黄金大盗乌南，带着大批黄金隐居到某座岛屿，之后生死不明，向着伟大航道持续前进的的路飞一行人，遇到了梦想成为乌南的部下的少年托比欧，他因为不愿继承爷爷经营的甜不辣摊，和爷爷闹的很不愉快，而爱黄金成痴的海贼艾拉德哥也正窥视著乌南埋藏的黄金\u2026\u2026","price":"100.00","imgpath":"/Uploads/Picture/2017-02-28/58b4c931e8210.jpg"}]}
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
        private List<ListBean> list;
        private List<TjlistBean> tjlist;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<TjlistBean> getTjlist() {
            return tjlist;
        }

        public void setTjlist(List<TjlistBean> tjlist) {
            this.tjlist = tjlist;
        }

        public static class ListBean {
            /**
             * id : 2
             * title : 航海王：发条岛的冒险
             * image_id : 1234
             * details : 草帽海贼团的船“黄金梅利号”在悠闲度假大伙眼前被偷走了，后来他们在寻找船的途中遇到一对小偷兄弟，从他们口中打听到小偷是现在统治着发条岛的“扑克牌海贼团”，而小偷兄弟正好也要去偷取岛上著名的“钻石钟”，双方的目标一致，决定联手攻进扑克牌海贼团的大本营，但可怕的头目“大熊王”手中却掌握著攸关整座发条岛生死的巨大发条……
             * price : 110.00
             * imgpath : /Uploads/Picture/2017-02-28/58b4c941425b8.jpg
             */

            private String id;
            private String title;
            private String image_id;
            private String describe;
            private String price;
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

            public String getImage_id() {
                return image_id;
            }

            public void setImage_id(String image_id) {
                this.image_id = image_id;
            }

            public String getDetails() {
                return describe;
            }

            public void setDetails(String details) {
                this.describe = details;
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
        }

        public static class TjlistBean {
            /**
             * id : 1
             * title : 航海王：黄金岛冒险
             * image_id : 1233
             * details : 传说中的黄金大盗乌南，带着大批黄金隐居到某座岛屿，之后生死不明，向着伟大航道持续前进的的路飞一行人，遇到了梦想成为乌南的部下的少年托比欧，他因为不愿继承爷爷经营的甜不辣摊，和爷爷闹的很不愉快，而爱黄金成痴的海贼艾拉德哥也正窥视著乌南埋藏的黄金……
             * price : 100.00
             * imgpath : /Uploads/Picture/2017-02-28/58b4c931e8210.jpg
             */

            private String id;
            private String title;
            private String image_id;
            private String details;
            private String price;
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

            public String getImage_id() {
                return image_id;
            }

            public void setImage_id(String image_id) {
                this.image_id = image_id;
            }

            public String getDetails() {
                return details;
            }

            public void setDetails(String details) {
                this.details = details;
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
        }
    }
}
