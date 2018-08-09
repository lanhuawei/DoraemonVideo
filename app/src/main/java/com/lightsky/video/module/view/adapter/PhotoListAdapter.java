package com.lightsky.video.module.view.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.lightsky.video.R;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.util.ViewInflate;
import com.lightsky.video.module.view.holder.PhotoBaseHolder;
import com.lightsky.video.module.view.holder.PhotoCameraViewHolder;
import com.lightsky.video.module.view.holder.PhotoRecyclerViewHolder;

/**
 * Created by Ivan.L on 2018/8/9.
 * 本地图片adapter
 */
public class PhotoListAdapter extends BaseRecyclerAdapter<String, PhotoBaseHolder> {
    private final int IMG_CAMERA = 0;
    private final int IMG_PICTURE = 1;

    public PhotoListAdapter(Context context, OnItemClickListener<PhotoBaseHolder> listener) {
        super(context, listener);
    }

    @Override
    public PhotoBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IMG_CAMERA) {
            return new PhotoCameraViewHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_photo_camera_view, parent, false));
        } else {
            return new PhotoRecyclerViewHolder(ViewInflate.inflateLayout(getContext(), R.layout.adapter_photo_recycler_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return IMG_CAMERA;
        } else {
            return IMG_PICTURE;
        }

    }
}
