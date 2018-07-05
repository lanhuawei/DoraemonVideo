package com.lanhuawei.cn.doraemonvideo.module.bean;

import android.text.TextUtils;

import com.lanhuawei.cn.doraemonvideo.common.Util.KindsOfUtil;

import org.json.JSONObject;

/**
 * Created by Ivan.L on 2018/7/5.
 */

public class HuoShanVideoDataBean extends MainVideoDataBean {
    public static HuoShanVideoDataBean fromJSONData(String jsonStr) {
        HuoShanVideoDataBean data = new HuoShanVideoDataBean();
        if (TextUtils.isEmpty(jsonStr)) {
            return data;
        }
        try {
            JSONObject json = new JSONObject(jsonStr);
            //video标签中的信息
            /**
             * data
             * 	-video
             * 		-url_list
             *  	-download_url
             *  		-url_list
             *  	-cover
             *  		-url_list
             *  	-width
             *  	-height
             *
             * 	-author
             * 		-avatar_jpg
             * 			-url_list
             * 		-nickname
             * 		-city
             * 		-birthday_description
             *
             * 	-stats
             * 		-play_count
             *
             * 	-title
             *
             * 	-create_time
             */

            //视频内容信息
            JSONObject dataJson = json.getJSONObject("data");
            JSONObject videoJson = dataJson.getJSONObject("video");
            data.videoPlayUrl = videoJson.getJSONArray("url_list").getString(0);
            data.videoDownloadUrl = videoJson.getJSONArray("download_url").getString(0);
            JSONObject coverJson = videoJson.getJSONObject("cover");
            data.coverImgUrl = coverJson.getJSONArray("url_list").getString(0);
            data.videoWidth = videoJson.optInt("width");
            data.videoHeight = videoJson.optInt("height");
            data.videoDuration = videoJson.optDouble("duration") + "";
            data.title = dataJson.optString("title");
            data.createTime = dataJson.optLong("create_time") * 1000;
            data.playCount = dataJson.getJSONObject("stats").optInt("play_count");

            //视频作者信息
            JSONObject authorJson = dataJson.getJSONObject("author");
            JSONObject thumbJson = authorJson.getJSONObject("avatar_jpg");
            data.authorImgUrl = thumbJson.getJSONArray("url_list").getString(0);
            data.authorName = authorJson.optString("nickname");
            data.authorCity = authorJson.optString("city");
            data.authorAge = authorJson.optString("birthday_description");

            JSONObject coverThumbJson = videoJson.getJSONObject("cover");
            data.dynamicCover = coverThumbJson.getJSONArray("url_list").getString(0);

            data.likeCount = dataJson.getJSONObject("stats").optInt("digg_count");


            data.formatTimeStr = KindsOfUtil.formatTimeStr(data.createTime);
            data.formatPlayCountStr = KindsOfUtil.formatNumber(data.playCount);

            if (data.title.length() > 30) {
                data.filterTitleStr = KindsOfUtil.filterStrBlank(data.title.substring(0, 30) + "...");
            } else {
                data.filterTitleStr = KindsOfUtil.filterStrBlank(data.title);
            }
            if (data.authorName.length() > 7) {
                data.filterUserNameStr = KindsOfUtil.filterStrBlank(data.authorName.substring(0, 7) + "...");
            } else {
                data.filterUserNameStr = KindsOfUtil.filterStrBlank(data.authorName);
            }

        } catch (Exception e) {
        }

        return data;
    }

    @Override
    public String toString() {
        return "videotitle=" + title + ",videoplayurl=" + videoPlayUrl + ",videodownloadurl=" + videoDownloadUrl + ",width=" + videoWidth + ",height=" + videoHeight
                + ",coverimgurl=" + coverImgUrl + ",authorname=" + authorName + ",authorimgurl=" + authorImgUrl + ",authorcity=" + authorCity + ",authorage=" + authorAge
                + ",videoplaycount=" + playCount + ",videoduration=" + videoDuration;
    }
}
