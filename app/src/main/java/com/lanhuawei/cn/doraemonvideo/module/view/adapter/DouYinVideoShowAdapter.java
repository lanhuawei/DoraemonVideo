package com.lanhuawei.cn.doraemonvideo.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lanhuawei.cn.doraemonvideo.R;
import com.lanhuawei.cn.doraemonvideo.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lanhuawei.cn.doraemonvideo.common.mywidget.util.ViewInflate;
import com.lanhuawei.cn.doraemonvideo.module.bean.DouYinMainVideoDataBean;
import com.lanhuawei.cn.doraemonvideo.module.view.holder.DouYinVideoShowHolder;


/**
 * Created by Ivan.L on 2018/7/4.
 * 首页抖音页面展示adapter
 */

public class DouYinVideoShowAdapter extends BaseRecyclerAdapter<DouYinMainVideoDataBean, DouYinVideoShowHolder> {

    public DouYinVideoShowAdapter(Context context, OnItemClickListener<DouYinVideoShowHolder> listener) {
        super(context, listener);
    }

    @Override
    public DouYinVideoShowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DouYinVideoShowHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_dou_yin_video_show, parent, false));
    }
}
