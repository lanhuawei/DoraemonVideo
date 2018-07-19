package com.lightsky.video.module.entity.databean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.L on 2018/7/4.
 * 首页list
 */

public class DouYinMainVideoListDataBean {
    //这里需要注意这两个字段是进行分页请求功能，大致规则如下：
    /**
     * 第一次请求，这两个字段都是0
     * 第二次请求取第一次请求返回的json数据中的min_cursor字段，max_cursor不需要携带。
     * 第三次以及后面所有的请求都只带max_cursor字段，值为第一次请求返回的json数据中的max_cursor字段。
     */
    private long maxCursor;//最大时间戳
    private long minCursor;//最小时间戳
    private List<MainVideoDataBean> videoDataBeanList = null;

    public static DouYinMainVideoListDataBean fromJSONData(String str) {
        DouYinMainVideoListDataBean data = new DouYinMainVideoListDataBean();
        if (TextUtils.isEmpty(str)) {
            return data;
        }
        try {
            JSONObject json = new JSONObject(str);
            data.maxCursor = json.optLong("max_cursor");
            data.minCursor = json.optLong("min_cursor");
            JSONArray videoAry = json.getJSONArray("aweme_list");
            data.videoDataBeanList = new ArrayList<MainVideoDataBean>(videoAry.length());
            for (int i = 0; i < videoAry.length(); i++) {
                data.videoDataBeanList.add(DouYinMainVideoDataSubclassBean.fromJSONData(videoAry.getJSONObject(i).toString()));
            }
        } catch (Exception e) {
        }

        return data;
    }

    @Override
    public String toString() {
        return "DouYinMainVideoListDataBean{" +
                "maxCursor=" + maxCursor +
                ", minCursor=" + minCursor +
                ", videoDataBeanList=" + videoDataBeanList +
                '}';
    }

    public long getMaxCursor() {
        return maxCursor;
    }

    public long getMinCursor() {
        return minCursor;
    }

    public List<MainVideoDataBean> getVideoDataBeanList() {
        return videoDataBeanList;
    }
}
