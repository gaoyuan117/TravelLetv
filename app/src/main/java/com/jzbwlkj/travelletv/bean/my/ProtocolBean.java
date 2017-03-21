package com.jzbwlkj.travelletv.bean.my;

/**
 * Created by admin on 2017/2/25.
 */

public class ProtocolBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"id":"1","title":"保证金协议","content":"<p><img src=\"http://lxls.jzbwlkj.com/Uploads/Editor/2017-02-24/58afe1ca902e4.jpg\" title=\"设置-保证金协议.jpg\"/><\/p>","uptime":"1487921613"}
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
         * id : 1
         * title : 保证金协议
         * content : <p><img src="http://lxls.jzbwlkj.com/Uploads/Editor/2017-02-24/58afe1ca902e4.jpg" title="设置-保证金协议.jpg"/></p>
         * uptime : 1487921613
         */

        private String id;
        private String title;
        private String content;
        private String uptime;

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

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }
    }
}
