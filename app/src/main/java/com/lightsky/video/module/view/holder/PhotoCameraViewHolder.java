package com.lightsky.video.module.view.holder;

import android.support.annotation.Nullable;
import android.view.View;


import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/8/9.
 * 本地相册 点击拍照
 */
public class PhotoCameraViewHolder extends PhotoBaseHolder {


    public PhotoCameraViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onItemDataUpdated(@Nullable String s, int position) {

    }
}
