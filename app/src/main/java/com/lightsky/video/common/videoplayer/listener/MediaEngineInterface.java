package com.lightsky.video.common.videoplayer.listener;

/**
 * Created by Ivan.L on 2018/7/2.
 * 媒体引擎接口
 */

public interface MediaEngineInterface {
    void onError();

    void onCompletion();

    void onInfo(int what, int extra);

    void onBufferingUpdate(int percent);

    void onPrepared();

    void onVideoSizeChanged(int width, int height);
}
