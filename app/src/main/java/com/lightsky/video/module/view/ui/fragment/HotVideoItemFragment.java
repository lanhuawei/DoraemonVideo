package com.lightsky.video.module.view.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lightsky.video.R;
import com.lightsky.video.module.base.BaseLoadFragment;

import java.nio.Buffer;

import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/25.
 * 热门itemFragment
 */
public class HotVideoItemFragment extends BaseLoadFragment {
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    private int position;


    public static HotVideoItemFragment newInstance(Bundle args) {
        HotVideoItemFragment instance = new HotVideoItemFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_hot_video_item;
    }
    @Override
    protected void initView() {

    }

    /**
     * 页面第一次加载
     */
    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
    }
}
