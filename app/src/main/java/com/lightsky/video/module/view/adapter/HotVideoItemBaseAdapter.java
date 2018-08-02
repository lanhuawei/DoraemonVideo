package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.holder.HotVideoItemBaseHolder;
import com.lightsky.video.module.view.holder.HotVideoItemOneHolder;
import com.lightsky.video.module.view.holder.HotVideoItemThreeHolder;
import com.lightsky.video.module.view.holder.HotVideoItemTwoHolder;

/**
 * Created by Ivan.L on 2018/8/2.
 * 热门tab one Base Adapter
 */
public class HotVideoItemBaseAdapter extends BaseRecyclerAdapter<MainVideoDataBean, HotVideoItemBaseHolder> {
    private final int HotVideoOne = 0;
    private final int HotVideoTwo = 1;
    private final int HotVideoThree = 2;
    private final int HotVideoFour = 3;
    private int currentPosition;

    public HotVideoItemBaseAdapter(Context context, OnItemClickListener<HotVideoItemBaseHolder> listener, int currentPosition) {
        super(context, listener);
        this.currentPosition = currentPosition;
    }

    @Override
    public HotVideoItemBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HotVideoOne) {
            return new HotVideoItemOneHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_one, parent, false));
        } else if (viewType == HotVideoTwo) {
            return new HotVideoItemTwoHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_two, parent, false));
        } else if (viewType == HotVideoThree) {
            return new HotVideoItemThreeHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_two, parent, false));
        } else {
            return new HotVideoItemOneHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_one, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (currentPosition == 0) {
            return HotVideoOne;
        } else if (currentPosition == 1) {
            return HotVideoTwo;
        } else if (currentPosition == 2) {
            return HotVideoThree;
        } else if (currentPosition == 3) {
            return HotVideoFour;
        } else {
            return super.getItemViewType(position);
        }
    }
}
