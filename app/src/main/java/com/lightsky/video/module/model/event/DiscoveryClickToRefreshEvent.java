package com.lightsky.video.module.model.event;

import android.view.View;

/**
 * Created by Ivan.L on 2018/7/19.
 * 发现点击刷新
 */

public class DiscoveryClickToRefreshEvent {
    private boolean isClick;
    private View view;
//    private int lastPosition;
//    private int newPosition;

    public DiscoveryClickToRefreshEvent(boolean isClick, View view) {
        this.isClick = isClick;
        this.view = view;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
