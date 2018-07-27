package com.lightsky.video.module.model.event;

/**
 * Created by Ivan.L on 2018/7/9.
 * 点击刷新
 */

public class ClickToRefreshEvent {
    private boolean isDoubleClick;

    public ClickToRefreshEvent(boolean isDoubleClick) {
        this.isDoubleClick = isDoubleClick;
    }

    public void setDoubleClick(boolean doubleClick) {
        isDoubleClick = doubleClick;
    }

    public boolean isDoubleClick() {
        return isDoubleClick;
    }
}
