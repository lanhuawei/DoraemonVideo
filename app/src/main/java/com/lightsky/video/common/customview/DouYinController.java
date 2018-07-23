package com.lightsky.video.common.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lightsky.video.R;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.videoplayer.controller.BaseVideoController;
import com.lightsky.video.common.videoplayer.listener.MediaPlayerControl;
import com.lightsky.video.common.videoplayer.player.IjkVideoView;

/**
 * Created by Ivan.L on 2018/7/5.
 * 抖音播放控制器
 */

public class DouYinController extends BaseVideoController {
    private ImageView iv_thumb;
    private View rl_root;
    private ImageView iv_play;

    private boolean isSelected = false;

    public DouYinController(@NonNull Context context) {
        super(context);
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_douyin_controller;
    }


    @Override
    protected void initView() {
        controllerView = LayoutInflater.from(getContext()).inflate(getLayoutId(), this);
        iv_thumb = (ImageView) controllerView.findViewById(R.id.iv_thumb);
        rl_root = (View) controllerView.findViewById(R.id.rl_root);
        iv_play = (ImageView) controllerView.findViewById(R.id.iv_play);

        rl_root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelected) {
                    mediaPlayer.start();
                } else {
                    mediaPlayer.pause();
                }
                isSelected = !isSelected;
            }
        });
    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);

        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                LogUtil.e("videoplayer","STATE_IDLE");
                iv_thumb.setVisibility(VISIBLE);
                iv_play.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PLAYING:
                LogUtil.e("videoplayer", "STATE_PLAYING");
                iv_thumb.setVisibility(GONE);
                iv_play.setVisibility(View.GONE);
                break;
            case IjkVideoView.STATE_PREPARED:
                LogUtil.e("videoplayer", "STATE_PREPARED");
                break;
            case IjkVideoView.STATE_PAUSED:
                iv_play.setVisibility(View.VISIBLE);
                iv_play.setSelected(false);
                break;
            case IjkVideoView.STATE_ERROR:
                iv_thumb.setVisibility(VISIBLE);
                iv_play.setVisibility(View.GONE);
                ToastUtil.showToast("播放错误");
                break;
        }
    }

    public ImageView getIv_thumb() {
        return iv_thumb;
    }

    public ImageView getIv_play() {
        return iv_play;
    }

    public View getRl_root() {
        return rl_root;
    }

    public void setSelect(boolean selected) {
        isSelected = selected;
    }

    public boolean getSelect() {
        return isSelected;
    }

    public MediaPlayerControl getMideaPlayer() {
        return mediaPlayer;
    }
}
