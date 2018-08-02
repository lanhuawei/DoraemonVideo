package com.lightsky.video.module.view.holder;

import android.view.View;

import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerViewHolder;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;

/**
 * Created by Ivan.L on 2018/8/2.
 * 热门tab BaseHolder
 */
public abstract class HotVideoItemBaseHolder extends BaseRecyclerViewHolder<MainVideoDataBean> {
    public HotVideoItemBaseHolder(View view) {
        super(view);
    }
}
