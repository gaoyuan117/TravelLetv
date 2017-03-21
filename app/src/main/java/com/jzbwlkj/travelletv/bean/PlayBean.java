package com.jzbwlkj.travelletv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dn on 2017/2/28.
 */

public class PlayBean implements Serializable{


    /**
     * code : 200
     * message : 操作成功
     * data : {"id":"21","title":"航海王：黄金岛冒险","cover_id":"1254","describe":"传说中的黄金大盗乌南，带着大批黄金隐居到某座岛屿，之后生死不明，向着伟大航道持续前进的的路飞一行人，遇到了梦想成为乌南的部下的少年托比欧，他因为不愿继承爷爷经营的甜不辣摊，和爷爷闹的很不愉快，而爱黄金成痴的海贼艾拉德哥也正窥视著乌南埋藏的黄金\u2026\u2026","price":"100.00","moviepath":"http://lxle.oss-cn-shanghai.aliyuncs.com/2017-03-07/[海贼王]第775集_bd.mp4","video_weizhi":"2","imgpath":"/Uploads/Picture/2017-03-07/58be03120de92.JPG","jishu":[{"jishu":"1"}],"is_collect":0,"is_pur":0}
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

    public static class DataBean implements Serializable{
        /**
         * id : 21
         * title : 航海王：黄金岛冒险
         * cover_id : 1254
         * describe : 传说中的黄金大盗乌南，带着大批黄金隐居到某座岛屿，之后生死不明，向着伟大航道持续前进的的路飞一行人，遇到了梦想成为乌南的部下的少年托比欧，他因为不愿继承爷爷经营的甜不辣摊，和爷爷闹的很不愉快，而爱黄金成痴的海贼艾拉德哥也正窥视著乌南埋藏的黄金……
         * price : 100.00
         * moviepath : http://lxle.oss-cn-shanghai.aliyuncs.com/2017-03-07/[海贼王]第775集_bd.mp4
         * video_weizhi : 2
         * imgpath : /Uploads/Picture/2017-03-07/58be03120de92.JPG
         * jishu : [{"jishu":"1"}]
         * is_collect : 0
         * is_pur : 0
         */

        private String id;
        private String title;
        private String cover_id;
        private String describe;
        private String price;
        private String moviepath;
        private String video_weizhi;
        private String imgpath;
        private int is_collect;
        private int is_pur;
        private List<JishuBean> jishu;

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

        public String getMoviepath() {
            return moviepath;
        }

        public void setMoviepath(String moviepath) {
            this.moviepath = moviepath;
        }

        public String getVideo_weizhi() {
            return video_weizhi;
        }

        public void setVideo_weizhi(String video_weizhi) {
            this.video_weizhi = video_weizhi;
        }

        public String getImgpath() {
            return imgpath;
        }

        public void setImgpath(String imgpath) {
            this.imgpath = imgpath;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getIs_pur() {
            return is_pur;
        }

        public void setIs_pur(int is_pur) {
            this.is_pur = is_pur;
        }

        public List<JishuBean> getJishu() {
            return jishu;
        }

        public void setJishu(List<JishuBean> jishu) {
            this.jishu = jishu;
        }

        public static class JishuBean implements Serializable{
            /**
             * jishu : 1
             */

            private String jishu;

            public String getJishu() {
                return jishu;
            }

            public void setJishu(String jishu) {
                this.jishu = jishu;
            }
        }
    }
}
