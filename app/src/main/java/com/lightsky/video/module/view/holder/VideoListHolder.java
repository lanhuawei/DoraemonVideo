package com.lightsky.video.module.view.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerViewHolder;
import com.lightsky.video.module.model.bean.VideoInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/8/10.
 * 视频展示Holder
 */
public class VideoListHolder extends BaseRecyclerViewHolder<VideoInfoBean> {
    @BindView(R.id.iv_thumb) ImageView ivThumb;
    @BindView(R.id.tv_duration) TextView tvDuration;
    @BindView(R.id.iv_select) ImageView ivSelect;
    @BindView(R.id.rl_video_item) RelativeLayout rlVideoItem;

    public VideoListHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onItemDataUpdated(@Nullable VideoInfoBean videoInfoBean, int position) {

    }
}
