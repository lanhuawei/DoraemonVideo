package com.lightsky.video.module.view.holder;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lightsky.video.MyApplication;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.CommonUtil;
import com.lightsky.video.common.Util.GlideUtil;
import com.lightsky.video.common.Util.KindsOfUtil;
import com.lightsky.video.common.Util.LogUtil;
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
public class HotVideoItemOneHolder extends HotVideoItemBaseHolder {
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
    protected void onItemDataUpdated(@Nullable final MainVideoDataBean mainVideoDataBean,int position) {
        if (mainVideoDataBean != null) {
            GlideUtil.loadImage(MyApplication.getInstance(), mainVideoDataBean.getAuthorImgUrl(), ivUserAvatar, null,
                    R.color.black, R.color.black);
            GlideUtil.loadImage(MyApplication.getInstance(), mainVideoDataBean.getCoverImgUrl(), controller.getThumb(), null,
                    R.color.white, R.color.white);
            controller.getIjkTitle().setText(mainVideoDataBean.getTitle());

            LogUtil.e("HOTHOL", mainVideoDataBean.getDuration() + "");
//            controller.getIjkControlSize().setText(CommonUtil.getTime(mainVideoDataBean.getDuration()));//这是抖音的
//            这是火山的
//            String[] duration = mainVideoDataBean.getVideoDuration().split("[.]");//这俩个都可以
            String[] duration = mainVideoDataBean.getVideoDuration().split("\\.");//这俩个都可以
            controller.getIjkControlSize().setText(CommonUtil.getTime(Integer.parseInt(duration[0])));
            ijkVideoview.setPlayerConfig(mPlayerConfig);
            ijkVideoview.setTitle(mainVideoDataBean.getTitle());
            ijkVideoview.setVideoController(controller);
            ijkVideoview.setTag(getAdapterPosition());

            controller.getThumb().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ijkVideoview.setUrl(mainVideoDataBean.getVideoPlayUrl());
                    ijkVideoview.start();
                }
            });
            tvUsername.setText(mainVideoDataBean.getAuthorName());
            tvPlayCount.setText(KindsOfUtil.formatNumber(mainVideoDataBean.getLikeCount()) + "次观看");
            rlBottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
