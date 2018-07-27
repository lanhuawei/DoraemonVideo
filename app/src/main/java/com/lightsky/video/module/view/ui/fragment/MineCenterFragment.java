package com.lightsky.video.module.view.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.lightsky.video.R;
import com.lightsky.video.common.videoplayer.player.VideoViewManager;
import com.lightsky.video.module.base.BaseFragment;

/**
 * Created by Ivan.L on 2018/7/2.
 * 我的页面Fragment
 */

public class MineCenterFragment extends BaseFragment {
    @Override
    protected int layoutResId() {
        return R.layout.fragment_mine_center;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewReallyCreated(View view) {
        view.setFitsSystemWindows(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            rootView.setFitsSystemWindows(false);
        } else {
            rootView.setFitsSystemWindows(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            rootView.requestApplyInsets();
        } else {
            ViewCompat.requestApplyInsets(rootView);
        }

    }
}
