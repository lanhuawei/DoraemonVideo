package com.lanhuawei.cn.doraemonvideo.module.view.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanhuawei.cn.doraemonvideo.R;
import com.lanhuawei.cn.doraemonvideo.common.customview.LoadFrameLayout;
import com.lanhuawei.cn.doraemonvideo.common.mywidget.recyclerview.PtrRecyclerViewUIComponent;
import com.lanhuawei.cn.doraemonvideo.common.mywidget.recyclerview.RecyclerViewWithEV;
import com.lanhuawei.cn.doraemonvideo.common.pulltorefresh.recyclerview.RecyclerAdapterWithHF;
import com.lanhuawei.cn.doraemonvideo.module.base.BaseFragment;
import com.lanhuawei.cn.doraemonvideo.module.view.adapter.DouYinVideoShowAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/2.
 * 仿抖音页面展示
 */

public class DouYinVideoFragment extends BaseFragment {

    @BindView(R.id.load_frameLayout)
    LoadFrameLayout loadFrameLayout;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.am_ptr_framelayout)
    PtrRecyclerViewUIComponent ptrRecyclerViewUIComponent;
    private TextView retry;
    private DouYinVideoShowAdapter douYinVideoShowAdapter;
    private RecyclerAdapterWithHF adapterWithHF;
    private long max_cursor = 0;

    boolean isLoadMore = false;

    boolean douYinDisable = false;


    @Override
    protected int layoutResId() {
        return R.layout.fragment_dou_yin_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
