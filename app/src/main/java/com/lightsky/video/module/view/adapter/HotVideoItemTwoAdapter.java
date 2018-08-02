package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.holder.HotVideoItemTwoHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ivan.L on 2018/7/31.
 * 热门tab two新鲜 Adapter
 */
public class HotVideoItemTwoAdapter extends BaseRecyclerAdapter<MainVideoDataBean, HotVideoItemTwoHolder> {
    public static List<Integer> mHeights = new ArrayList<>();
    public HotVideoItemTwoAdapter(Context context, OnItemClickListener<HotVideoItemTwoHolder> listener) {
        super(context, listener);
    }

    @Override
    public HotVideoItemTwoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotVideoItemTwoHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_hot_video_item_two, parent, false));
    }

}
