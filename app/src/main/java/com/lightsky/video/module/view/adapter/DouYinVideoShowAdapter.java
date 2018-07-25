package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.holder.DouYinVideoShowHolder;


/**
 * Created by Ivan.L on 2018/7/4.
 * 首页抖音页面展示adapter
 */

public class DouYinVideoShowAdapter extends BaseRecyclerAdapter<MainVideoDataBean, DouYinVideoShowHolder> {

    public DouYinVideoShowAdapter(Context context, OnItemClickListener<DouYinVideoShowHolder> listener) {
        super(context, listener);
    }

    @Override
    public DouYinVideoShowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DouYinVideoShowHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_dou_yin_video_show, parent, false));
    }






}
