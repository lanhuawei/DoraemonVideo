package com.lanhuawei.cn.doraemonvideo.common.videoplayer.listener;

/**
 * Created by Ivan.L on 2018/7/2.
 * 视频播放监听
 */

public interface VideoListener {
    //播放完成
    void onComplete();

    //准备完成
    void onPrepared();

    void onError();

    void onInfo(int what, int extra);

}
