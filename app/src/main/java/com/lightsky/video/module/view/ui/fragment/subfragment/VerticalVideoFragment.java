package com.lightsky.video.module.view.ui.fragment.subfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lightsky.video.MyApplication;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.GlideUtil;
import com.lightsky.video.common.Util.KindsOfUtil;
import com.lightsky.video.common.Util.ToastUtil;
import com.lightsky.video.common.Util.WeakDataHolderUtil;
import com.lightsky.video.common.Util.httputil.DouyinUtil;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.common.customview.CircleImageView;
import com.lightsky.video.common.customview.DouYinController;
import com.lightsky.video.common.customview.TextImageView;
import com.lightsky.video.common.customview.VerticalViewPager;
import com.lightsky.video.common.okhttp.manager.OkHttpClientManager;
import com.lightsky.video.common.videoplayer.player.IjkVideoView;
import com.lightsky.video.common.videoplayer.player.PlayerConfig;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.entity.databean.DouYinMainVideoListDataBean;
import com.lightsky.video.module.entity.databean.MainVideoDataBean;
import com.lightsky.video.module.model.event.RefreshEvent;
import com.lightsky.video.module.view.adapter.DouYinAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;

/**
 * Created by Ivan.L on 2018/7/23.
 * 仿抖音页面
 * 全屏视频
 * 可以上下滑动
 */

public class VerticalVideoFragment extends BaseFragment {
    public static final String VIDEO_URL_LIST = "videoUrlList";
    @BindView(R.id.verticalviewpager)
    VerticalViewPager verticalviewpager;
    Unbinder unbinder;
    private int position;
    private long max_cursor = 0;
    private List<MainVideoDataBean> mainVideoDataBeanList = new ArrayList<>();
    private int mCurrentItem;//当前位置
    private IjkVideoView ijkVideoView;//视频播放器
    private DouYinAdapter douYinAdapter;//适配器
    private DouYinController douYinController;//视频播放控制器
    private List<View> mViews = new ArrayList<>();
    private TextView tv_video_title;
    private CircleImageView iv_user_avatar;
    private TextView tv_user_name;
    private TextImageView tv_like_count;
    private TextImageView tv_play_count;
    private ImageView iv_cover;
    private int mPlayingPosition;
    private Context context;

    public static VerticalVideoFragment newInstance(long max_cursor, int position) {
        VerticalVideoFragment instance = new VerticalVideoFragment();
        Bundle args = new Bundle();
        args.putLong("maxCursor", max_cursor);
        args.putInt("position", position);
        instance.setArguments(args);
        return instance;
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_vertical_video;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        StatusBarCompat.translucentStatusBar(getActivity(), true);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        context = getActivity();
        mainVideoDataBeanList = (List<MainVideoDataBean>) WeakDataHolderUtil.getInstance().getData(VIDEO_URL_LIST);
        if (getArguments() != null) {
            max_cursor = (long) getArguments().getLong("maxCursor");
            position = (int) getArguments().getInt("position");
            mCurrentItem = position;
        }

        ijkVideoView = new IjkVideoView(context);
        PlayerConfig config = new PlayerConfig.Builder().setLooping().build();
        ijkVideoView.setPlayerConfig(config);
        douYinController = new DouYinController(context);
        ijkVideoView.setVideoController(douYinController);
        setImageData();
    }

    /**
     * 根据传过来的list
     * 设置页面详情
     */
    private void setImageData() {
        for (MainVideoDataBean item : mainVideoDataBeanList) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_vertical_video_item, null);
            iv_cover = view.findViewById(R.id.iv_cover);

            iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
            tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
            tv_like_count = (TextImageView) view.findViewById(R.id.tv_like_count);
            tv_play_count = (TextImageView) view.findViewById(R.id.tv_play_count);
            tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);

            Glide.with(MyApplication.getInstance()).load(item.getCoverImgUrl()).dontAnimate().into(iv_cover);
            GlideUtil.loadImage(MyApplication.getInstance(), item.getAuthorImgUrl(), iv_user_avatar, null);
            tv_video_title.setText(item.getTitle());
            tv_user_name.setText(item.getAuthorName());
            tv_play_count.setText(KindsOfUtil.formatNumber(item.getPlayCount()) + "播放");
            tv_like_count.setText(KindsOfUtil.formatNumber(item.getLikeCount()) + "赞");
            mViews.add(view);
        }
        douYinAdapter = new DouYinAdapter(mViews);
        verticalviewpager.setAdapter(douYinAdapter);

        if (position != -1) {
            verticalviewpager.setCurrentItem(position);
        }
        verticalviewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;
                ijkVideoView.pause();
                if (mCurrentItem == mainVideoDataBeanList.size() - 1) {
                    ToastUtil.showToast("加载中，请稍后");
                    getDouYinListData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mPlayingPosition == mCurrentItem) {
                    return;
                }
                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    ijkVideoView.release();
                    ViewParent parent = ijkVideoView.getParent();
                    if (parent != null && parent instanceof FrameLayout) {
                        ((FrameLayout) parent).removeView(ijkVideoView);
                    }
                    startPlay();
                }
            }
        });

        verticalviewpager.post(new Runnable() {
            @Override
            public void run() {
                startPlay();
            }
        });
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        View view = mViews.get(mCurrentItem);
        FrameLayout frameLayout = view.findViewById(R.id.fl_container);
        iv_cover = view.findViewById(R.id.iv_cover);
        douYinController.setSelect(false);
        if (iv_cover != null && iv_cover.getDrawable() != null) {
            douYinController.getIv_thumb().setImageDrawable(iv_cover.getDrawable());
        }
        ViewGroup parent = (ViewGroup) ijkVideoView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        frameLayout.addView(ijkVideoView);
        ijkVideoView.setUrl(mainVideoDataBeanList.get(mCurrentItem).getVideoPlayUrl());
//        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_MATCH_PARENT);//充满屏幕
//        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_DEFAULT);//默认
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        ijkVideoView.start();
        mPlayingPosition = mCurrentItem;
    }

    /**
     * 获取数据
     * 下拉数据规律：min_cursor=max_cursor=0
     * 上拉数据规律：
     * 第二次请求取第一次请求返回的json数据中的min_cursor字段，max_cursor不需要携带。
     * 第三次以及后面所有的请求都只带max_cursor字段，值为第一次请求返回的json数据中的max_cursor字段
     */
    private void getDouYinListData() {
        String url = DouyinUtil.getEncryptUrl(getActivity(), 0, max_cursor);
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.StringCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    DouYinMainVideoListDataBean listDataBean = DouYinMainVideoListDataBean.fromJSONData(response);
                    max_cursor = listDataBean.getMaxCursor();
                    if (listDataBean.getVideoDataBeanList() == null || listDataBean.getVideoDataBeanList().size() == 0) {
                        return;
                    }

                    List<MainVideoDataBean> list = listDataBean.getVideoDataBeanList();
                    mainVideoDataBeanList.addAll(list);
                    mViews.clear();//加载更多需要先清空原来的view

                    for (MainVideoDataBean item : mainVideoDataBeanList) {
                        View view = LayoutInflater.from(context).inflate(R.layout.activity_vertical_video_item, null);
                        iv_cover = view.findViewById(R.id.iv_cover);

                        iv_user_avatar = (CircleImageView) view.findViewById(R.id.iv_user_avatar);
                        tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
                        tv_like_count = (TextImageView) view.findViewById(R.id.tv_like_count);
                        tv_play_count = (TextImageView) view.findViewById(R.id.tv_play_count);
                        tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);

                        Glide.with(MyApplication.getInstance()).load(item.getCoverImgUrl()).dontAnimate().into(iv_cover);
                        GlideUtil.loadImage(MyApplication.getInstance(), item.getAuthorImgUrl(), iv_user_avatar, null);
                        tv_video_title.setText(item.getTitle());
                        tv_user_name.setText(item.getAuthorName());
                        tv_play_count.setText(KindsOfUtil.formatNumber(item.getPlayCount()) + "播放");
                        tv_like_count.setText(KindsOfUtil.formatNumber(item.getLikeCount()) + "赞");
                        mViews.add(view);
                    }
                    douYinAdapter.setmViews(mViews);
                    douYinAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                ToastUtil.showToast("网络连接失败");
            }
        });
    }


    /**
     * 自定义的
     * @param view
     */
    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        ijkVideoView.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        ijkVideoView.resume();
        if (douYinController != null) {
            douYinController.setSelect(false);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ijkVideoView.pause();
        if (douYinController != null) {
            douYinController.getIv_play().setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        EventBus.getDefault().post(new RefreshEvent(mainVideoDataBeanList, mCurrentItem, max_cursor));
        getActivity().finish();
    }

    @Override
    public boolean onBackPressed() {
        EventBus.getDefault().post(new RefreshEvent(mainVideoDataBeanList, mCurrentItem, max_cursor));
        return super.onBackPressed();
    }

    //    /**
//     * 使用add hide() show()方法切换fragment  不会走任何的生命周期 无法通过生命周期进行刷新，只能通过这个刷新
//     * @param hidden
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {// 不在最前端显示 相当于调用了onPause();
////            douYinController.getMideaPlayer().pause();
//        } else { // 在最前端显示 相当于调用了onResume();
////            if (douYinController.getSelect()) {
////                douYinController.getMideaPlayer().start();
////            } else {
////                douYinController.getMideaPlayer().pause();
////            }
//
//        }
//
//    }

    /**
     * 当fragment结合viewpager使用的时候 这个方法会调用
     * 提醒:setUserVisibleHint(boolean isVisibleToUser) 实在Fragment OnCreateView()方法之前调用的
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {//界面可见
            if (ijkVideoView != null) {
                ijkVideoView.resume();
            }
            if (douYinController != null) {
                douYinController.setSelect(false);
            }
            if (getActivity() != null) {
                StatusBarCompat.translucentStatusBar(getActivity(), true);
            }

        } else {
            if (ijkVideoView != null) {
                ijkVideoView.pause();
            }
            if (douYinController != null) {
                douYinController.getIv_play().setVisibility(View.GONE);
            }
            if (getActivity() != null) {
                StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);
                StatusBarFontHelper.setStatusBarMode(getActivity(), true);
            }
            EventBus.getDefault().post(new RefreshEvent(mainVideoDataBeanList, mCurrentItem, max_cursor));

        }
    }
}
