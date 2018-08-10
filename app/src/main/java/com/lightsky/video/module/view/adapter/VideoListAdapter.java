package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.module.model.bean.VideoInfoBean;
import com.lightsky.video.module.view.holder.VideoListHolder;


/**
 * Created by Ivan.L on 2018/8/10.
 * 视频列表adapter
 */
public class VideoListAdapter extends BaseRecyclerAdapter<VideoInfoBean, VideoListHolder> {

    public VideoListAdapter(Context context, OnItemClickListener<VideoListHolder> listener) {
        super(context, listener);
    }

    @Override
    public VideoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoListHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_video_list, parent, false));
    }
}
