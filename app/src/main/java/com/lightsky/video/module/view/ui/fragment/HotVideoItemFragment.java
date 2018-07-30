package com.lightsky.video.module.view.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.lightsky.video.R;
import com.lightsky.video.common.customview.PtrCustomHeader;
import com.lightsky.video.common.mywidget.recyclerview.PtrRecyclerViewUIComponent;
import com.lightsky.video.common.pulltorefresh.recyclerview.RecyclerAdapterWithHF;
import com.lightsky.video.module.base.BaseLoadFragment;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.adapter.HotVideoItemOneAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/25.
 * 热门itemFragment
 */
public class HotVideoItemFragment extends BaseLoadFragment {
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    @BindView(R.id.am_ptr_framelayout)
    PtrRecyclerViewUIComponent ptrRecyclerViewUIComponent;
    @BindView(R.id.ar_empty_view) View ar_empty_view;
    private int position;
    private RecyclerAdapterWithHF adapterWithHF;
    private List<MainVideoDataBean> mainVideoDataBeans = new ArrayList<>();
    private PtrCustomHeader ptrCustomHeader;
    private HotVideoItemOneAdapter hotVideoItemOneAdapter;



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
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
