package com.lightsky.video.module.view.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.customview.CircleImageView;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerViewHolder;
import com.lightsky.video.common.videoplayer.controller.StandardVideoController;
import com.lightsky.video.common.videoplayer.player.IjkVideoView;
import com.lightsky.video.common.videoplayer.player.PlayerConfig;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/27.
 * 热门tab one精选 Holder
 */
public class HotVideoItemOneHolder extends BaseRecyclerViewHolder<MainVideoDataBean> {
    @BindView(R.id.ijk_videoview) IjkVideoView ijkVideoview;
    @BindView(R.id.iv_user_avatar) CircleImageView ivUserAvatar;
    @BindView(R.id.tv_username) TextView tvUsername;
    @BindView(R.id.tv_play_count) TextView tvPlayCount;
    @BindView(R.id.rl_bottom) RelativeLayout rlBottom;
    private StandardVideoController controller;
    private PlayerConfig mPlayerConfig;
    Context mContext;

    public HotVideoItemOneHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mContext = view.getContext();
        controller = new StandardVideoController(view.getContext());
        ijkVideoview.setVideoController(controller);
        mPlayerConfig = new PlayerConfig.Builder()
                .addToPlayerManager()
                .build();
    }

    @Override
    protected void onItemDataUpdated(@Nullable MainVideoDataBean mainVideoDataBean) {
        if (mainVideoDataBean != null) {

        }
    }
}
