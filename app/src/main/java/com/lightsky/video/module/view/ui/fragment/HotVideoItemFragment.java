package com.lightsky.video.module.view.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lightsky.video.MyApplication;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.DensityUtil;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.Util.httputil.DouyinUtil;
import com.lightsky.video.common.Util.httputil.HuoShanUtil;
import com.lightsky.video.common.customview.PtrCustomHeader;
import com.lightsky.video.common.mywidget.implloadmore.OnScrollToBottomLoadMoreListener;
import com.lightsky.video.common.mywidget.recyclerview.PtrRecyclerViewUIComponent;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.refersh.OnPullToRefreshListener;
import com.lightsky.video.common.okhttp.manager.OkHttpClientManager;
import com.lightsky.video.common.pulltorefresh.PtrFrameLayout;
import com.lightsky.video.common.pulltorefresh.recyclerview.RecyclerAdapterWithHF;
import com.lightsky.video.common.videoplayer.player.IjkVideoView;
import com.lightsky.video.module.base.BaseLoadFragment;
import com.lightsky.video.module.entity.databean.DouYinMainVideoListDataBean;
import com.lightsky.video.module.entity.databean.HuoShanVideoListDataBean;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.view.adapter.HotVideoItemOneAdapter;
import com.lightsky.video.module.view.holder.HotVideoItemOneHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

/**
 * Created by Ivan.L on 2018/7/25.
 * 热门itemFragment
 */
public class HotVideoItemFragment extends BaseLoadFragment implements BaseRecyclerAdapter.OnItemClickListener<HotVideoItemOneHolder> {
    private static final String TAG = "---->HotVideoItemFragment";
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    @BindView(R.id.am_ptr_framelayout)
    PtrRecyclerViewUIComponent ptrRecyclerViewUIComponent;
    @BindView(R.id.ar_empty_view)
    View ar_empty_view;
    private int position;
    private String title;
    private RecyclerAdapterWithHF adapterWithHF;
    private List<MainVideoDataBean> mainVideoDataBeans = new ArrayList<>();
    private PtrCustomHeader ptrCustomHeader;
    private HotVideoItemOneAdapter hotVideoItemOneAdapter;
    private boolean douYinDisable = true;//是否抖音禁用 false 不是    是否是抖音。火山显示不同效果
    private String LoadMoreOne = "down";
    private String LoadMoreTwo = "down";
    private String LoadMoreThree = "down";
    private boolean isLoadMore = false;


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

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        position = bundle.getInt(POSITION);
        title = bundle.getString(TITLE);
        hotVideoItemOneAdapter = new HotVideoItemOneAdapter(getActivity(), this);
        adapterWithHF = new RecyclerAdapterWithHF(hotVideoItemOneAdapter);
        ptrRecyclerViewUIComponent.setLayoutManager(new LinearLayoutManager(getActivity()));
        ptrRecyclerViewUIComponent.setAdapter(adapterWithHF);
        initHeader();
        ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
        ptrRecyclerViewUIComponent.getRecyclerView().addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                IjkVideoView ijkVideoView = view.findViewById(R.id.ijk_videoview);
                if (ijkVideoView != null && !ijkVideoView.isFullScreen()) {
                    int tag = (int) ijkVideoView.getTag();
                    ijkVideoView.stopPlayback();
                }
            }
        });
//        下拉刷新
        ptrRecyclerViewUIComponent.setOnPullToRefreshListener(new OnPullToRefreshListener() {
            @Override
            public void onPullToRefresh() {
                switch (position) {
                    case 0:
                        max_cursor = 0;
                        isLoadMore = false;
                        if (mainVideoDataBeans != null && mainVideoDataBeans.size() > 0) {
                            mainVideoDataBeans.clear();
                        }
                        if (douYinDisable) {
                            getHuoShanListData();
                        } else {
                            getDouYinListData();
                        }
                        break;

                }
            }
        });
//        滚动到底部加载更多
        ptrRecyclerViewUIComponent.setOnScrollToBottomLoadMoreListener(new OnScrollToBottomLoadMoreListener() {
            @Override
            public void onScrollToBottomLoadMore() {
                isLoadMore = true;
                if (douYinDisable) {
                    getHuoShanListData();
                } else {
                    getDouYinListData();
                }
            }
        });


    }

    private long max_cursor = 0;

    /**
     * 下拉数据规律：min_cursor=max_cursor=0
     * 上拉数据规律：
     * 第二次请求取第一次请求返回的json数据中的min_cursor字段，max_cursor不需要携带。
     * 第三次以及后面所有的请求都只带max_cursor字段，值为第一次请求返回的json数据中的max_cursor字段
     * 获取抖音数据
     */
    private void getDouYinListData() {
        String url = DouyinUtil.getEncryptUrl(getActivity(), 0, max_cursor);
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.StringCallback() {
            @Override
            public void onResponse(String response) {
//                LogUtils.json(response);
//                loadFrameLayout.showContentView();
                try {
                    DouYinMainVideoListDataBean listDataBean = DouYinMainVideoListDataBean.fromJSONData(response);
                    max_cursor = listDataBean.getMaxCursor();
                    if (listDataBean.getVideoDataBeanList() == null || listDataBean.getVideoDataBeanList().size() == 0) {
                        douYinDisable = true;
                        max_cursor = 0;
                        isLoadMore = false;
                        getHuoShanListData();
                        return;
                    } else {
                        douYinDisable = false;
                    }
                    LogUtil.e(TAG, listDataBean.getVideoDataBeanList().size() + "  ");
                    ptrCustomHeader.getTvtitle().setText("为您推荐了一组新鲜内容");
                    ptrRecyclerViewUIComponent.removeView(ptrCustomHeader);
                    ptrRecyclerViewUIComponent.setHeaderView(ptrCustomHeader);
                    if (isLoadMore) {
                        mainVideoDataBeans.addAll(listDataBean.getVideoDataBeanList());
                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans, false);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        mainVideoDataBeans = listDataBean.getVideoDataBeanList();
                        if (mainVideoDataBeans.size() == 0) {
                            ar_empty_view.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            ar_empty_view.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }
                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.refreshComplete();
                    }
                } catch (Exception e) {
                    ptrRecyclerViewUIComponent.refreshComplete();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                ptrRecyclerViewUIComponent.loadMoreComplete(false);
                ptrRecyclerViewUIComponent.refreshComplete();
                ToastUtil.showToast("网络连接失败");
                ptrCustomHeader.getTvtitle().setText("网络连接失败，请重试");
                ptrRecyclerViewUIComponent.removeView(ptrCustomHeader);
                ptrRecyclerViewUIComponent.setHeaderView(ptrCustomHeader);
//                loadFrameLayout.showErrorView();
            }


        });
    }

    /**
     * 下拉数据规律：min_cursor=max_cursor=0
     * 上拉数据规律：
     * 第二次请求取第一次请求返回的json数据中的min_cursor字段，max_cursor不需要携带。
     * 第三次以及后面所有的请求都只带max_cursor字段，值为第一次请求返回的json数据中的max_cursor字段
     * 获取火山数据
     */
    public void getHuoShanListData() {
        String url = HuoShanUtil.getEncryptUrl(getActivity(), 0, max_cursor);
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.StringCallback() {
            @Override
            public void onResponse(String response) {
//                LogUtils.json(response);
                try {
                    HuoShanVideoListDataBean huoShanVideoListDataBean = HuoShanVideoListDataBean.fromJSONData(response);
                    max_cursor = huoShanVideoListDataBean.getMaxTime();
                    if (isLoadMore) {
                        mainVideoDataBeans.addAll(huoShanVideoListDataBean.getVideoDataList());
                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans, false);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        mainVideoDataBeans = huoShanVideoListDataBean.getVideoDataList();
                        if (mainVideoDataBeans.size() == 0) {
                            ar_empty_view.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            ar_empty_view.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }
                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.refreshComplete();
                    }
                } catch (Exception e) {
                    ptrRecyclerViewUIComponent.refreshComplete();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                ptrRecyclerViewUIComponent.loadMoreComplete(true);
                ptrRecyclerViewUIComponent.refreshComplete();
                ToastUtil.showToast("网络连接失败");

                ptrCustomHeader.getTvtitle().setText("网络连接失败，请重试");
                ptrRecyclerViewUIComponent.removeView(ptrCustomHeader);
                ptrRecyclerViewUIComponent.setHeaderView(ptrCustomHeader);
//                loadFrameLayout.showErrorView();
            }


        });
    }



    /**
     * 初始化刷新头部
     */
    private void initHeader() {
        ptrCustomHeader = new PtrCustomHeader(getActivity());
        ptrCustomHeader.setLayoutParams(
                new PtrFrameLayout.LayoutParams(PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.WRAP_CONTENT));
        ptrCustomHeader.setPadding(0, DensityUtil.dip2px(MyApplication.getInstance(), 15),
                0, DensityUtil.dip2px(MyApplication.getInstance(), 10));
        ptrCustomHeader.setUp(ptrRecyclerViewUIComponent);
        ptrRecyclerViewUIComponent.setHeaderView(ptrCustomHeader);
        ptrRecyclerViewUIComponent.setDurationToClose(600);
        ptrRecyclerViewUIComponent.setLoadingMinTime(1200);
        ptrRecyclerViewUIComponent.addPtrUIHandler(ptrCustomHeader);
    }


    /**
     * 页面第一次加载
     */
    @Override
    protected void lazyLoad() {
        ptrRecyclerViewUIComponent.delayRefresh(100);
    }

//    每个点击事件
    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
