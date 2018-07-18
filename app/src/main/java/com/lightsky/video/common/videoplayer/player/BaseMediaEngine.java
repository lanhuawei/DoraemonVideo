package com.lightsky.video.common.videoplayer.player;

import android.view.Surface;
import android.view.SurfaceHolder;

import com.lightsky.video.common.videoplayer.listener.MediaEngineInterface;

import java.io.IOException;

/**
 * Created by Ivan.L on 2018/7/2.
 * 基础的媒体引擎
 */

public abstract class BaseMediaEngine {
    protected MediaEngineInterface mMediaEngineInterface;

    public abstract void initPlayer();

    public abstract void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    public abstract void start();

    public abstract void pause();

    public abstract void stop();

    public abstract void prepareAsync();

    public abstract void reset();

    public abstract boolean isPlaying();

    public abstract void seekTo(long time);

    public abstract void release();

    public abstract long getCurrentPosition();

    public abstract long getDuration();

    public abstract void setSurface(Surface surface);

    public abstract void setDisplay(SurfaceHolder holder);

    public abstract void setVolume(int v1, int v2);

    public abstract void setLooping(boolean isLooping);

    public abstract void setEnableMediaCodec(boolean isEnable);

    public abstract void setOptions();

    public void setMediaEngineInterface(MediaEngineInterface mediaEngineInterface) {
        this.mMediaEngineInterface = mediaEngineInterface;
    }
}
