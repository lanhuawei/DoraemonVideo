package com.lightsky.video.module.view.ui.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.TextureView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.customview.SquareRelativeLayout;
import com.lightsky.video.common.customview.WithWordsCircleProgressBar;
import com.lightsky.video.module.base.BaseActivity;
import com.lightsky.video.module.model.bean.VideoInfoBean;
import com.lightsky.video.module.view.adapter.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/8/8.
 * 视频选择
 */
public class VideoSelectActivity extends BaseActivity {

    private static final String TAG = "-------->VideoSelectActivity";
    @BindView(R.id.fl_back) FrameLayout flBack;//返回
    @BindView(R.id.tv_title_bar_title) TextView tvTitleBarTitle;
    @BindView(R.id.iv_empty) ImageView ivEmpty;
    @BindView(R.id.tv_empty) TextView tvEmpty;
    @BindView(R.id.srl_video_play) SquareRelativeLayout srlVideoPlay;
    @BindView(R.id.rv_video_list) RecyclerView rvVideoList;
    @BindView(R.id.fl_loading) FrameLayout flLoading;
    @BindView(R.id.circle_progress) WithWordsCircleProgressBar circleProgress;
    @BindView(R.id.fl_circle_progress) FrameLayout flCircleProgress;

    private static final int MSG_SHOW_VIDEO_LIST = 1000;
    private static final int MSG_HIDE_PLAY_PAUSE_VIEW = 2000;
    private static final int MSG_DELAY_FIRST_FRAME = 3000;

    public static final int STATE_IDLE = 0; //通常状态
    public static final int STATE_PLAYING = 1; //视频正在播放
    public static final int STATE_PAUSED = 2; //视频暂停
    public static final int DEFAULT_SHOW_TIME = 3000; // 控制器的默认显示时间3秒
    private TextureView mTextureView; //更换为TextureView
    private Surface mSurface; //surface
    private ImageView mPlayPause; // 播放暂停按钮
    private ImageView mVideoBg; // 视频缩略图
    private List<VideoInfoBean> videoInfoBeanList = new ArrayList<>();// 视频信息集合
    private MediaPlayer mMediaPlayer = new MediaPlayer(); // 播放器
    private Context mContext;
    private VideoListAdapter videoListAdapter;

    @Override
    protected int layoutResId() {
        return R.layout.activity_video_select;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

}
