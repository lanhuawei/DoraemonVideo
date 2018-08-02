package com.lightsky.video.common.Util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ivan.L on 2018/8/1.
 * 小视频页面分割线
 */
public class SpacesItemDecorationMain extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecorationMain(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        } else {
            outRect.right = space;
            outRect.bottom = space;
        }


    }

}
