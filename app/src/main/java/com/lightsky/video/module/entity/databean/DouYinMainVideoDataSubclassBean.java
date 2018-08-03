package com.lightsky.video.module.entity.databean;

import android.text.TextUtils;

import com.lightsky.video.common.Util.KindsOfUtil;

import org.json.JSONObject;

/**
 * Created by Ivan.L on 2018/7/4.
 *
 */

public class DouYinMainVideoDataSubclassBean extends MainVideoDataBean {

    public static DouYinMainVideoDataSubclassBean fromJSONData(String jsonStr) {
        DouYinMainVideoDataSubclassBean data = new DouYinMainVideoDataSubclassBean();
        if (TextUtils.isEmpty(jsonStr)) {
            return data;
        }
        try {
            JSONObject json = new JSONObject(jsonStr);

            //video标签中的信息
            /**
             * video
             * 	-play_addr
             *  	-url_list
             *  -download_addr
             *  	-url_list
             *  -origin_cover
             *  	-url_list
             *  -cover
             *  	-width
             *  	-height
             *
             * author
             * 	-avatar_thumb
             * 		-url_list
             * 	-nickname
             *
             * statistics
             * 	-play_count
             *
             * music
             * 	-cover_thumb
             * 		-url_list
             * 	-title
             * 	-author
             *
             * desc
             *
             * create_time
             */

            //视频内容信息
            JSONObject videoJson = json.getJSONObject("video");
            JSONObject playAddJson = videoJson.getJSONObject("play_addr");
            data.videoPlayUrl = playAddJson.getJSONArray("url_list").getString(0);
            JSONObject downloadJson = videoJson.getJSONObject("download_addr");
            data.videoDownloadUrl = downloadJson.getJSONArray("url_list").getString(0);
            JSONObject coverJson = videoJson.getJSONObject("origin_cover");
            data.coverImgUrl = coverJson.getJSONArray("url_list").getString(0);
//            在火山中的时长位置
            data.duration = videoJson.optInt("duration");


            JSONObject dynamicCoverJson = videoJson.getJSONObject("dynamic_cover");
            data.dynamicCover = dynamicCoverJson.getJSONArray("url_list").getString(0);

            JSONObject sizeJson = videoJson.getJSONObject("cover");
            data.videoWidth = sizeJson.optInt("width");
            data.videoHeight = sizeJson.optInt("height");
            data.title = json.optString("desc");
            data.createTime = json.optLong("create_time") * 1000;

            data.playCount = json.getJSONObject("statistics").optInt("play_count");
            data.likeCount = json.getJSONObject("statistics").optInt("digg_count");

            //视频作者信息
            JSONObject authorJson = json.getJSONObject("author");
            JSONObject thumbJson = authorJson.getJSONObject("avatar_thumb");
            data.authorImgUrl = thumbJson.getJSONArray("url_list").getString(0);
            data.authorName = authorJson.optString("nickname");

            //视频音乐信息
            JSONObject musicJson = json.getJSONObject("music");
            JSONObject musicThumbJson = musicJson.getJSONObject("cover_thumb");
            data.musicImgUrl = musicThumbJson.getJSONArray("url_list").getString(0);
            data.musicName = musicJson.optString("title");
            data.musicAuthorName = musicJson.optString("author");

            data.formatTimeStr = KindsOfUtil.formatTimeStr(data.createTime);
            data.formatPlayCountStr = KindsOfUtil.formatNumber(data.playCount);

            if (!TextUtils.isEmpty(data.musicAuthorName) && !TextUtils.isEmpty(data.musicName)) {
                if (data.musicName.contains("@")) {
                    data.filterMusicNameStr = data.musicAuthorName + data.musicName;
                } else {
                    data.filterMusicNameStr = data.musicAuthorName + "@" + data.musicName;
                }
            } else if (!TextUtils.isEmpty(data.musicAuthorName)) {
                data.filterMusicNameStr = data.musicAuthorName;
            } else if (!TextUtils.isEmpty(data.musicName)) {
                data.filterMusicNameStr = data.musicName;
            }

            if (!TextUtils.isEmpty(data.title)) {
                if (data.title.length() > 30) {
                    data.filterTitleStr = KindsOfUtil.filterStrBlank(data.title.substring(0, 30) + "...");
                } else {
                    data.filterTitleStr = KindsOfUtil.filterStrBlank(data.title);
                }
            }
            if (!TextUtils.isEmpty(data.authorName)) {
                if (data.authorName.length() > 7) {
                    data.filterUserNameStr = KindsOfUtil.filterStrBlank(data.authorName.substring(0, 7) + "...");
                } else {
                    data.filterUserNameStr = KindsOfUtil.filterStrBlank(data.authorName);
                }
            }
        } catch (Exception e) {
        }

        return data;
    }

    @Override
    public String toString() {
        return "DouYinMainVideoDataSubclassBean{" +
                "title='" + title + '\'' +
                ", authorImgUrl='" + authorImgUrl + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorSignature='" + authorSignature + '\'' +
                ", authorSex=" + authorSex +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", dynamicCover='" + dynamicCover + '\'' +
                ", videoPlayUrl='" + videoPlayUrl + '\'' +
                ", videoDownloadUrl='" + videoDownloadUrl + '\'' +
                ", videoWidth=" + videoWidth +
                ", videoHeight=" + videoHeight +
                ", playCount=" + playCount +
                ", likeCount=" + likeCount +
                ", createTime=" + createTime +
                ", musicImgUrl='" + musicImgUrl + '\'' +
                ", musicName='" + musicName + '\'' +
                ", musicAuthorName='" + musicAuthorName + '\'' +
                ", videoDuration='" + videoDuration + '\'' +
                ", authorCity='" + authorCity + '\'' +
                ", authorAge='" + authorAge + '\'' +
                ", formatTimeStr='" + formatTimeStr + '\'' +
                ", filterTitleStr='" + filterTitleStr + '\'' +
                ", filterUserNameStr='" + filterUserNameStr + '\'' +
                ", formatPlayCountStr='" + formatPlayCountStr + '\'' +
                ", formatLikeCountStr='" + formatLikeCountStr + '\'' +
                ", filterMusicNameStr='" + filterMusicNameStr + '\'' +
                ", duration=" + duration +
                ", type=" + type +
                '}';
    }
}
