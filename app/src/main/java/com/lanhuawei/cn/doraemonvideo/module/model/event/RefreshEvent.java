package com.lanhuawei.cn.doraemonvideo.module.model.event;

import com.lanhuawei.cn.doraemonvideo.module.bean.databean.MainVideoDataBean;

import java.util.List;

/**
 * Created by Ivan.L on 2018/7/4.
 * 刷新事件
 */

public class RefreshEvent {
    private List<MainVideoDataBean> mainVideoDataBeans;
    private int position;
    private long max_cursor;

    public RefreshEvent(List<MainVideoDataBean> mainVideoDataBeans, int position, long max_cursor) {
        this.mainVideoDataBeans = mainVideoDataBeans;
        this.position = position;
        this.max_cursor = max_cursor;
    }

    public List<MainVideoDataBean> getMainVideoDataBeans() {
        return mainVideoDataBeans;
    }

    public int getPosition() {
        return position;
    }

    public long getMax_cursor() {
        return max_cursor;
    }
}
