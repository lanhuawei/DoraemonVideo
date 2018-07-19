package com.lightsky.video.module.entity;

/**
 * Created by Ivan.L on 2018/7/9.
 * 统计事件相关实体类
 */

public class EventEntity {
    private String eventId;
    private String eventName;


    public EventEntity(String eventId, String eventName) {
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
