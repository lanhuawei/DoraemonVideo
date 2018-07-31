package com.lightsky.video.module.view.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lightsky.video.MyApplication;

import com.lightsky.video.R;
import com.lightsky.video.common.Util.DensityUtil;
import com.lightsky.video.common.Util.LogUtil;
import com.lightsky.video.common.Util.NoDoubleClickUtil;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.Util.WeakDataHolderUtil;
import com.lightsky.video.common.Util.httputil.DouyinUtil;
import com.lightsky.video.common.Util.httputil.HuoShanUtil;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.common.customview.PtrCustomHeader;
import com.lightsky.video.common.customview.LoadFrameLayout;
import com.lightsky.video.common.mywidget.implloadmore.OnScrollToBottomLoadMoreListener;
import com.lightsky.video.common.mywidget.recyclerview.PtrRecyclerViewUIComponent;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerAdapter;
import com.lightsky.video.common.mywidget.refersh.OnPullToRefreshListener;
import com.lightsky.video.common.okhttp.manager.OkHttpClientManager;
import com.lightsky.video.common.pulltorefresh.PtrFrameLayout;
import com.lightsky.video.common.pulltorefresh.recyclerview.RecyclerAdapterWithHF;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.entity.databean.HuoShanVideoListDataBean;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.entity.databean.DouYinMainVideoListDataBean;
import com.lightsky.video.module.model.event.ClickToRefreshEvent;
import com.lightsky.video.module.model.event.RefreshEvent;
import com.lightsky.video.module.view.adapter.DouYinVideoShowAdapter;
import com.lightsky.video.module.view.holder.DouYinVideoShowHolder;
import com.lightsky.video.module.view.ui.activity.VerticalVideoMainActivity;
import com.lightsky.video.module.view.ui.fragment.subfragment.VerticalVideoFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by Ivan.L on 2018/7/2.
 * 仿抖音页面展示
 */

public class DouYinVideoFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener<DouYinVideoShowHolder> {
    @BindView(R.id.load_frameLayout)
    LoadFrameLayout loadFrameLayout;
    @BindView(R.id.empty_view)
    View emptyView;
    @BindView(R.id.am_ptr_framelayout)
    PtrRecyclerViewUIComponent ptrRecyclerViewUIComponent;
    Unbinder unbinder;
    private TextView retry;
    private DouYinVideoShowAdapter douYinVideoShowAdapter;
    private RecyclerAdapterWithHF adapterWithHF;
    private List<MainVideoDataBean> mainVideoDataBeans = new ArrayList<>();
    private long max_cursor = 0;
    private boolean isLoadMore = false;
    private boolean douYinDisable = false;//是否是抖音。火山显示不同效果
    private PtrCustomHeader ptrCustomHeader;
    private Context context;
    private static final String TAG = "---->DouYinVideoFragment";


    @Override
    protected int layoutResId() {
        return R.layout.fragment_dou_yin_view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StatusBarCompat.translucentStatusBar(getActivity(), true);
    }

    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    protected void initView() {
        context = getActivity();
        retry = loadFrameLayout.findViewById(R.id.tv_retry);
//        loadFrameLayout.showEmptyView();
    }

    @Override
    protected void initData() {
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NoDoubleClickUtil.isDoubleClickTry()) {
                    if (douYinDisable) {
                        getHuoShanListData();
                    } else {
                        getDouYinListData();
                    }

                }
            }
        });
        douYinVideoShowAdapter = new DouYinVideoShowAdapter(context, this);
        adapterWithHF = new RecyclerAdapterWithHF(douYinVideoShowAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (adapterWithHF.isHeader(position) || adapterWithHF.isFooter(position)) ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        ptrRecyclerViewUIComponent.setLayoutManager(gridLayoutManager);
        ptrRecyclerViewUIComponent.setAdapter(adapterWithHF);
        initHeader();
        ptrRecyclerViewUIComponent.delayRefresh(100);
        ptrRecyclerViewUIComponent.setLoadMoreEnable(true);

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
     * 下拉数据规律：min_cursor=max_cursor=0
     * 上拉数据规律：
     * 第二次请求取第一次请求返回的json数据中的min_cursor字段，max_cursor不需要携带。
     * 第三次以及后面所有的请求都只带max_cursor字段，值为第一次请求返回的json数据中的max_cursor字段
     * 获取抖音数据
     */
    private void getDouYinListData() {
        String url = DouyinUtil.getEncryptUrl(getActivity(), 0, max_cursor);
        LogUtil.e(TAG, url + "       ");
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.StringCallback() {
            @Override
            public void onResponse(String response) {
                LogUtils.json(response);
                loadFrameLayout.showContentView();
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
                        douYinVideoShowAdapter.setDataList(mainVideoDataBeans, false);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        mainVideoDataBeans = listDataBean.getVideoDataBeanList();
                        if (mainVideoDataBeans.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }
                        douYinVideoShowAdapter.setDataList(mainVideoDataBeans);
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
                loadFrameLayout.showErrorView();
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
                        douYinVideoShowAdapter.setDataList(mainVideoDataBeans, false);
                        adapterWithHF.notifyDataSetChanged();
                        ptrRecyclerViewUIComponent.loadMoreComplete(true);
                    } else {
                        mainVideoDataBeans = huoShanVideoListDataBean.getVideoDataList();
                        if (mainVideoDataBeans.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(false);
                        } else {
                            emptyView.setVisibility(View.GONE);
                            ptrRecyclerViewUIComponent.setLoadMoreEnable(true);
                        }
                        douYinVideoShowAdapter.setDataList(mainVideoDataBeans);
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
                loadFrameLayout.showErrorView();
            }


        });
    }

    /**
     * 设置刷新头部
     */
    private void initHeader() {
        ptrCustomHeader = new PtrCustomHeader(context);
        ptrCustomHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(PtrFrameLayout.LayoutParams.MATCH_PARENT, PtrFrameLayout.LayoutParams.WRAP_CONTENT));
        ptrCustomHeader.setPadding(
                0,
                DensityUtil.dip2px(MyApplication.getInstance(), 15),
                0,
                DensityUtil.dip2px(MyApplication.getInstance(), 10));
        ptrCustomHeader.setUp(ptrRecyclerViewUIComponent);
        ptrCustomHeader.getTvtitle().setText("为你推荐了新的内容");

        ptrRecyclerViewUIComponent.setHeaderView(ptrCustomHeader);
        ptrRecyclerViewUIComponent.setDurationToCloseHeader(600);
        ptrRecyclerViewUIComponent.setLoadingMinTime(1200);
        ptrRecyclerViewUIComponent.addPtrUIHandler(ptrCustomHeader);
    }

    @Override
    public void onItemClick(int position) {
        if (ptrRecyclerViewUIComponent.isLoadingMore() || ptrRecyclerViewUIComponent.isRefreshing()) {
            return;
        }
//        Intent intent = new Intent(context, VerticalVideoActivity.class);
//        WeakDataHolderUtil.getInstance().saveData(VerticalVideoActivity.VIDEO_URL_LIST, mainVideoDataBeans);
//        intent.putExtra(VerticalVideoActivity.MAX_CURSOR, max_cursor);
//        intent.putExtra(VerticalVideoActivity.POSITION, position);
//        context.startActivity(intent);


        Intent intent = new Intent(context, VerticalVideoMainActivity.class);
        WeakDataHolderUtil.getInstance().saveData(VerticalVideoFragment.VIDEO_URL_LIST, mainVideoDataBeans);
        intent.putExtra(VerticalVideoMainActivity.MAX_CURSOR, max_cursor);
        intent.putExtra(VerticalVideoMainActivity.POSITION, position);
        context.startActivity(intent);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.translucentStatusBar(getActivity(), true);
        } else {
//            设置了距离状态栏的高度，使得其他fragment有距离状态栏的高度，但是由于MIUI的状态栏设置字体颜色方式有俩种，
//           所以出现  StatusBarFontHelper.setStatusBarMode(getActivity(), true);的话StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);会失效
//            MIUI要是设置了StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);这句话，状态栏颜色文字颜色就一直白色。
            StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);
            StatusBarFontHelper.setStatusBarMode(getActivity(), true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * EventBus
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImageData(RefreshEvent event) {
        douYinVideoShowAdapter.setDataList(event.getMainVideoDataBeans());
        adapterWithHF.notifyDataSetChanged();
        ptrRecyclerViewUIComponent.getRecyclerView().scrollToPosition(event.getPosition());
        max_cursor = event.getMax_cursor();
    }

    /**
     * EventBus
     * @param clickToRefreshEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toRefresh(ClickToRefreshEvent clickToRefreshEvent) {
        if (clickToRefreshEvent.isDoubleClick()) {
            ptrRecyclerViewUIComponent.delayRefresh(100);
//            adapterWithHF.notifyDataSetChanged();
            ptrRecyclerViewUIComponent.getRecyclerView().scrollToPosition(0);
        }
    }

}
