package com.lightsky.video.module.view.holder;

import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/8/9.
 * 图片展示
 */
public class PhotoRecyclerViewHolder extends PhotoBaseHolder {
    public PhotoRecyclerViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onItemDataUpdated(@Nullable String s, int position) {

    }
}
