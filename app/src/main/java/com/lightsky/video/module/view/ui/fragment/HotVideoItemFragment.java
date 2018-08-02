package com.lightsky.video.module.view.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.lightsky.video.MyApplication;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.DensityUtil;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.SpacesItemDecorationHotTwo;
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
import com.lightsky.video.module.view.adapter.HotVideoItemBaseAdapter;
import com.lightsky.video.module.view.adapter.HotVideoItemOneAdapter;
import com.lightsky.video.module.view.adapter.HotVideoItemTwoAdapter;
import com.lightsky.video.module.view.holder.HotVideoItemBaseHolder;

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
public class HotVideoItemFragment extends BaseLoadFragment
        implements BaseRecyclerAdapter.OnItemClickListener<HotVideoItemBaseHolder>{
    private static final String TAG = "---->HotVideoItemFragment";
    public static final String POSITION = "position";
    public static final String TITLE = "title";
    @BindView(R.id.am_ptr_framelayout) PtrRecyclerViewUIComponent ptrRecyclerViewUIComponent;
    @BindView(R.id.ar_empty_view) View ar_empty_view;
    private int position;

    private String title;
    private RecyclerAdapterWithHF adapterWithHF;
    private List<MainVideoDataBean> mainVideoDataBeans = new ArrayList<>();
    private PtrCustomHeader ptrCustomHeader;
    private HotVideoItemOneAdapter hotVideoItemOneAdapter;
    private HotVideoItemTwoAdapter hotVideoItemTwoAdapter;
    private HotVideoItemBaseAdapter hotVideoItemBaseAdapter;
    private boolean douYinDisable = true;//是否抖音禁用 false 是抖音，true不是    是否是抖音。火山显示不同效果
    private boolean isLoadMore = false;
    private long max_cursor = 0;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;


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

//        hotVideoItemOneAdapter = new HotVideoItemOneAdapter(getActivity(), this);
//        adapterWithHF = new RecyclerAdapterWithHF(hotVideoItemOneAdapter);
//        ptrRecyclerViewUIComponent.setLayoutManager(new LinearLayoutManager(getActivity()));
//        ptrRecyclerViewUIComponent.setAdapter(adapterWithHF);

//        hotVideoItemTwoAdapter = new HotVideoItemTwoAdapter(getActivity(), new BaseRecyclerAdapter.OnItemClickListener<HotVideoItemTwoHolder>() {
//            @Override
//            public void onItemClick(int position) {
//
//            }
//        });
//        adapterWithHF = new RecyclerAdapterWithHF(hotVideoItemTwoAdapter);
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        ptrRecyclerViewUIComponent.setLayoutManager(staggeredGridLayoutManager);
//        ptrRecyclerViewUIComponent.setAdapter(adapterWithHF);

        hotVideoItemBaseAdapter = new HotVideoItemBaseAdapter(getActivity(), this, position);
        adapterWithHF = new RecyclerAdapterWithHF(hotVideoItemBaseAdapter);
        positionAdapter(position);
        ptrRecyclerViewUIComponent.setAdapter(adapterWithHF);


//        SpacesItemDecorationHotTwo spacesItemDecoration = new SpacesItemDecorationHotTwo(20);
//        ptrRecyclerViewUIComponent.getRecyclerView().addItemDecoration(spacesItemDecoration);


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

    /**
     * 根据位置
     * @param position
     */
    private void positionAdapter(int position) {
        switch (position) {
            case 0:
                ptrRecyclerViewUIComponent.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case 1:
                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                ptrRecyclerViewUIComponent.setLayoutManager(staggeredGridLayoutManager);
                ((SimpleItemAnimator) ptrRecyclerViewUIComponent.getRecyclerView().getItemAnimator()).setSupportsChangeAnimations(false); //取消RecyclerView的动画效果
                SpacesItemDecorationHotTwo spacesItemDecoration = new SpacesItemDecorationHotTwo(20);
                ptrRecyclerViewUIComponent.getRecyclerView().addItemDecoration(spacesItemDecoration);
                break;
            case 2:
                staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                ptrRecyclerViewUIComponent.setLayoutManager(staggeredGridLayoutManager);
                ((SimpleItemAnimator) ptrRecyclerViewUIComponent.getRecyclerView().getItemAnimator()).setSupportsChangeAnimations(false); //取消RecyclerView的动画效果
//                SpacesItemDecorationMain spacesItemDecorationMain = new SpacesItemDecorationMain(20);
//                ptrRecyclerViewUIComponent.getRecyclerView().addItemDecoration(spacesItemDecorationMain);
                break;
            case 3:
                ptrRecyclerViewUIComponent.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
        }

    }


    private int size;
    private int totalSize;
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
                        size = mainVideoDataBeans.size();
                        mainVideoDataBeans.addAll(listDataBean.getVideoDataBeanList());
                        totalSize = mainVideoDataBeans.size();

                        hotVideoItemBaseAdapter.setDataList(mainVideoDataBeans, false);
//                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans, false);
//                        hotVideoItemTwoAdapter.setDataList(mainVideoDataBeans, false);
                        if (position == 0) {
                            adapterWithHF.notifyDataSetChanged();
                        } else if (position == 1) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        } else if (position == 2) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        } else if (position == 3) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        }
//                        adapterWithHF.notifyDataSetChanged();
//                        adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);

                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        size = listDataBean.getVideoDataBeanList().size();
                        mainVideoDataBeans = listDataBean.getVideoDataBeanList();
                        if (mainVideoDataBeans.size() == 0) {
                            ar_empty_view.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            ar_empty_view.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }

                        HotVideoItemTwoAdapter.mHeights.clear();
//                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans);
//                        hotVideoItemTwoAdapter.setDataList(mainVideoDataBeans);

                        hotVideoItemBaseAdapter.setDataList(mainVideoDataBeans);

                        adapterWithHF.notifyDataSetChanged();
//                        adapterWithHF.notifyItemRangeChangedHF(0, size);
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
                        size = mainVideoDataBeans.size();
                        mainVideoDataBeans.addAll(huoShanVideoListDataBean.getVideoDataList());
                        totalSize = mainVideoDataBeans.size();

//                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans, false);
//                        hotVideoItemTwoAdapter.setDataList(mainVideoDataBeans, false);
                        hotVideoItemBaseAdapter.setDataList(mainVideoDataBeans, false);
                        if (position == 0) {
                            adapterWithHF.notifyDataSetChanged();
                        } else if (position == 1) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        } else if (position == 2) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        } else if (position == 3) {
                            adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        }
//                        adapterWithHF.notifyDataSetChanged();
//                        adapterWithHF.notifyItemRangeChangedHF(size - 1, totalSize);
                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        size = huoShanVideoListDataBean.getVideoDataList().size();
                        mainVideoDataBeans = huoShanVideoListDataBean.getVideoDataList();
                        if (mainVideoDataBeans.size() == 0) {
                            ar_empty_view.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            ar_empty_view.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }
                        HotVideoItemTwoAdapter.mHeights.clear();
//                        hotVideoItemOneAdapter.setDataList(mainVideoDataBeans);
//                        hotVideoItemTwoAdapter.setDataList(mainVideoDataBeans);

                        hotVideoItemBaseAdapter.setDataList(mainVideoDataBeans);

                        adapterWithHF.notifyDataSetChanged();
//                        adapterWithHF.notifyItemRangeChangedHF(0, size);
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
//    HotVideoItemOneHolder
    @Override
    public void onItemClick(int position) {

    }


//    @Override
//    protected void fragmentInvisible() {
//        super.fragmentInvisible();
//        lazyLoad();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
