package com.lightsky.video.module.model.event;

/**
 * Created by Ivan.L on 2018/7/9.
 * 双击刷新
 */

public class DoubleClickToRefresh {
    private boolean isDoubleClick;

    public DoubleClickToRefresh(boolean isDoubleClick) {
        this.isDoubleClick = isDoubleClick;
    }

    public void setDoubleClick(boolean doubleClick) {
        isDoubleClick = doubleClick;
    }

    public boolean isDoubleClick() {
        return isDoubleClick;
    }
}
