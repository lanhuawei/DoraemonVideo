package com.lightsky.video.module.view.ui.fragment;

import android.os.Build;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.lightsky.video.R;
import com.lightsky.video.common.customview.LoadFrameLayout;
import com.lightsky.video.common.customview.tablayout.SlidingTabLayout;
import com.lightsky.video.common.videoplayer.player.VideoViewManager;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.model.event.ClickToRefreshEvent;
import com.lightsky.video.module.model.event.CurrentPositionEvent;
import com.lightsky.video.module.view.adapter.HotVideoPagerAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/2.
 * 热门Fragment
 */

public class HotVideoFragment extends BaseFragment {
    private static final String TAG = "--->HotVideoFragment";
    @BindView(R.id.stl_hot)
    SlidingTabLayout stlHot;
    @BindView(R.id.vp_hot_video)
    ViewPager vpHotVideo;
    @BindView(R.id.load_frameLayout)
    LoadFrameLayout loadFrameLayout;
    Unbinder unbinder;
    private String[] titles = {"精选", "新鲜", "搞笑", "娱乐"};
    private TextView retry;
    private HotVideoPagerAdapter hotVideoPagerAdapter;
//    private int vpCurrentPosition;

    @Override
    protected int layoutResId() {
        return R.layout.fragment_hot_video;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
        view.setFitsSystemWindows(true);
    }

    @Override
    protected void initView() {
        retry = loadFrameLayout.findViewById(R.id.tv_retry);
    }


    @Override
    protected void initData() {
        loadFrameLayout.showContentView();
        hotVideoPagerAdapter = new HotVideoPagerAdapter(getChildFragmentManager(), titles);
        vpHotVideo.setAdapter(hotVideoPagerAdapter);
        vpHotVideo.setOffscreenPageLimit(3);
        stlHot.setViewPager(vpHotVideo);
        vpHotVideo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                VideoViewManager.instance().releaseVideoPlayer();
//                vpCurrentPosition = position;
                EventBus.getDefault().post(new CurrentPositionEvent(position));
                if (position == 0) {
                    StatService.onEvent(getActivity(), "choiceness", "精选");
                } else if (position == 1) {
                    StatService.onEvent(getActivity(), "fresh", "新鲜");
                } else if (position == 2) {
                    StatService.onEvent(getActivity(), "funny", "搞笑");
                } else {
                    StatService.onEvent(getActivity(), "recreation", "娱乐");
                }
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            VideoViewManager.instance().releaseVideoPlayer();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


}
