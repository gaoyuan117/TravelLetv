package com.jzbwlkj.travelletv.bean;

import java.io.Serializable;

/**
 * Created by dn on 2017/3/2.
 */

public class DownLoadBean implements Serializable {
    private String videoId;
    private String title;
    private String videoPath;
    private String des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
