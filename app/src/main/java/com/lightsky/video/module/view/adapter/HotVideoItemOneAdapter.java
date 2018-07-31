package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.common.videoplayer.player.IjkVideoView;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.holder.HotVideoItemOneHolder;

import butterknife.BindView;

/**
 * Created by Ivan.L on 2018/7/27.
 * 热门tab one精选 Adapter
 */
public class HotVideoItemOneAdapter extends BaseRecyclerAdapter<MainVideoDataBean, HotVideoItemOneHolder> {
    public HotVideoItemOneAdapter(Context context, OnItemClickListener<HotVideoItemOneHolder> listener) {
        super(context, listener);
    }
    @Override
    public HotVideoItemOneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotVideoItemOneHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_one, parent, false));
    }
}
