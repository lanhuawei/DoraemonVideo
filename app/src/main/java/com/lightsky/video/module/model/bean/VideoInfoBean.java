package com.lightsky.video.module.model.bean;

/**
 * Created by Ivan.L on 2018/8/10.
 * 本地视频详情
 */
public class VideoInfoBean {
    private String title;

    private String time;
    private String localName; //本地的名称
    private String localVideoPath; //本地的路径
    private String duration; //本地视频时长
    private String width; //本地视频宽度
    private String height; //本地视频高度
    private String rotation; //本地视频旋转方向

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalVideoPath() {
        return localVideoPath;
    }

    public void setLocalVideoPath(String localVideoPath) {
        this.localVideoPath = localVideoPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "VideoInfoBean{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", localName='" + localName + '\'' +
                ", localVideoPath='" + localVideoPath + '\'' +
                ", duration='" + duration + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", rotation='" + rotation + '\'' +
                '}';
    }
}
