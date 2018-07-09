package com.lanhuawei.cn.doraemonvideo.module.bean.databean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ivan.L on 2018/7/4.
 * 首页返回的类似抖音火山页面数据
 */

public class MainVideoDataBean implements Parcelable {

    public String title;//视频标题
    public String authorImgUrl;//作者头像地址
    public String authorName;//作者名称
    public String authorSignature;//作者签名
    public int authorSex;//作者性别
    public String coverImgUrl;//视频封面图片地址
    public String dynamicCover;//视频动态封面图片地址
    public String videoPlayUrl;//视频播放地址
    public String videoDownloadUrl;//视频下载地址
    public int videoWidth;//视频宽度
    public int videoHeight;//视频高度
    public long playCount;//播放次数
    public long likeCount;//点赞次数
    public long createTime;//创建时间

    //抖音专用
    public String musicImgUrl;//音乐图片
    public String musicName;//音乐名称
    public String musicAuthorName;//音乐作者

    //火山专用
    public String videoDuration;//视频时长(秒拍也用到)
    public String authorCity;//作者所在城市
    public String authorAge;//作者年龄

    //为了更好的效率问题提前格式化内容字段
    public String formatTimeStr = "";
    public String filterTitleStr = "";
    public String filterUserNameStr = "";
    public String formatPlayCountStr = "";
    public String formatLikeCountStr = "";
    public String filterMusicNameStr = "";

    //视频的Base64值
    public int type = 0;//视频类型：1 抖音 2 火山 3 快手 4 秒拍

    public String getTitle() {
        return title;
    }

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public int getAuthorSex() {
        return authorSex;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public String getDynamicCover() {
        return dynamicCover;
    }

    public String getVideoPlayUrl() {
        return videoPlayUrl;
    }

    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public long getPlayCount() {
        return playCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getMusicImgUrl() {
        return musicImgUrl;
    }

    public String getMusicName() {
        return musicName;
    }

    public String getMusicAuthorName() {
        return musicAuthorName;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public String getAuthorCity() {
        return authorCity;
    }

    public String getAuthorAge() {
        return authorAge;
    }

    public String getFormatTimeStr() {
        return formatTimeStr;
    }

    public String getFilterTitleStr() {
        return filterTitleStr;
    }

    public String getFilterUserNameStr() {
        return filterUserNameStr;
    }

    public String getFormatPlayCountStr() {
        return formatPlayCountStr;
    }

    public String getFormatLikeCountStr() {
        return formatLikeCountStr;
    }

    public String getFilterMusicNameStr() {
        return filterMusicNameStr;
    }

    public int getType() {
        return type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.authorImgUrl);
        dest.writeString(this.authorName);
        dest.writeString(this.authorSignature);
        dest.writeInt(this.authorSex);
        dest.writeString(this.coverImgUrl);
        dest.writeString(this.dynamicCover);
        dest.writeString(this.videoPlayUrl);
        dest.writeString(this.videoDownloadUrl);
        dest.writeInt(this.videoWidth);
        dest.writeInt(this.videoHeight);
        dest.writeLong(this.playCount);
        dest.writeLong(this.likeCount);
        dest.writeLong(this.createTime);
        dest.writeString(this.musicImgUrl);
        dest.writeString(this.musicName);
        dest.writeString(this.musicAuthorName);
        dest.writeString(this.videoDuration);
        dest.writeString(this.authorCity);
        dest.writeString(this.authorAge);
        dest.writeString(this.formatTimeStr);
        dest.writeString(this.filterTitleStr);
        dest.writeString(this.filterUserNameStr);
        dest.writeString(this.formatPlayCountStr);
        dest.writeString(this.formatLikeCountStr);
        dest.writeString(this.filterMusicNameStr);
        dest.writeInt(this.type);
    }

    public MainVideoDataBean() {
    }

    protected MainVideoDataBean(Parcel in) {
        this.title = in.readString();
        this.authorImgUrl = in.readString();
        this.authorName = in.readString();
        this.authorSignature = in.readString();
        this.authorSex = in.readInt();
        this.coverImgUrl = in.readString();
        this.dynamicCover = in.readString();
        this.videoPlayUrl = in.readString();
        this.videoDownloadUrl = in.readString();
        this.videoWidth = in.readInt();
        this.videoHeight = in.readInt();
        this.playCount = in.readLong();
        this.likeCount = in.readLong();
        this.createTime = in.readLong();
        this.musicImgUrl = in.readString();
        this.musicName = in.readString();
        this.musicAuthorName = in.readString();
        this.videoDuration = in.readString();
        this.authorCity = in.readString();
        this.authorAge = in.readString();
        this.formatTimeStr = in.readString();
        this.filterTitleStr = in.readString();
        this.filterUserNameStr = in.readString();
        this.formatPlayCountStr = in.readString();
        this.formatLikeCountStr = in.readString();
        this.filterMusicNameStr = in.readString();
        this.type = in.readInt();
    }

    public static final Creator<MainVideoDataBean> CREATOR = new Creator<MainVideoDataBean>() {
        @Override
        public MainVideoDataBean createFromParcel(Parcel source) {
            return new MainVideoDataBean(source);
        }

        @Override
        public MainVideoDataBean[] newArray(int size) {
            return new MainVideoDataBean[size];
        }
    };


}
