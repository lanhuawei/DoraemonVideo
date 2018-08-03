package com.lightsky.video.module.model.event;

/**
 * Created by Ivan.L on 2018/8/3.
 * viewpager 当前位置
 */
public class CurrentPositionEvent {
    private int currentPosition;

    public CurrentPositionEvent(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
