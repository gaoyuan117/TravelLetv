package com.jzbwlkj.travelletv.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DownDao {
    @Id(autoincrement = true)
    private Long id;



    @Property(nameInDb = "video_id")
    @NotNull
    @Unique
    private String videoId;//视频id
    @Property(nameInDb = "path")
    @NotNull
    private String filePath;

    @Property(nameInDb = "user")
    @NotNull
    private String user;

    @Property(nameInDb = "title")
    private String title;//视频标题

    @Property(nameInDb = "video_path")
    private String videoPath;//视频图片

    @Property(nameInDb = "des")
    private String des;//视频描述

    @Generated(hash = 1686205320)
    public DownDao(Long id, @NotNull String videoId, @NotNull String filePath,
            @NotNull String user, String title, String videoPath, String des) {
        this.id = id;
        this.videoId = videoId;
        this.filePath = filePath;
        this.user = user;
        this.title = title;
        this.videoPath = videoPath;
        this.des = des;
    }

    @Generated(hash = 1413163828)
    public DownDao() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

