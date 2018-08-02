package com.lightsky.video.common.Util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Ivan.L on 2018/8/1.
 * RecyclerView分割线 热门
 */
public class SpacesItemDecorationHotTwo extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecorationHotTwo(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) == 1) {
            outRect.top = space * 4;
        }

//        if (parent.getChildAdapterPosition(view) % 2 == 0) {
//            outRect.left = space;
//            outRect.right = space;
//            outRect.bottom = space;
//        } else {
//            outRect.right = space;
//            outRect.bottom = space;
//        }
        outRect.left = space;
        outRect.right = space / 3;
        outRect.bottom = space;



    }
}
