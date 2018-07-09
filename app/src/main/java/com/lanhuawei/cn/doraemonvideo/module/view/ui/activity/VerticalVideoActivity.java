package com.lanhuawei.cn.doraemonvideo.module.view.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanhuawei.cn.doraemonvideo.MyApplication;
import com.lanhuawei.cn.doraemonvideo.R;
import com.lanhuawei.cn.doraemonvideo.common.Util.GlideUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.KindsOfUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.ToastUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.WeakDataHolderUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.httputil.DouyinUtil;
import com.lanhuawei.cn.doraemonvideo.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lanhuawei.cn.doraemonvideo.common.customview.CircleImageView;
import com.lanhuawei.cn.doraemonvideo.common.customview.DouYinController;
import com.lanhuawei.cn.doraemonvideo.common.customview.TextImageView;
import com.lanhuawei.cn.doraemonvideo.common.customview.VerticalViewPager;
import com.lanhuawei.cn.doraemonvideo.common.okhttp.manager.OkHttpClientManager;
import com.lanhuawei.cn.doraemonvideo.common.videoplayer.player.IjkVideoView;
import com.lanhuawei.cn.doraemonvideo.common.videoplayer.player.PlayerConfig;
import com.lanhuawei.cn.doraemonvideo.module.base.BaseActivity;
import com.lanhuawei.cn.doraemonvideo.module.bean.DouYinMainVideoListDataBean;
import com.lanhuawei.cn.doraemonvideo.module.bean.MainVideoDataBean;
import com.lanhuawei.cn.doraemonvideo.module.model.event.RefreshEvent;
import com.lanhuawei.cn.doraemonvideo.module.view.adapter.DouYinAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by Ivan.L on 2018/7/5.
 * 仿抖音页面
 * 全屏视频
 * 可以上下滑动
 */

public class VerticalVideoActivity extends BaseActivity {
    public static final String VIDEO_URL_LIST = "videoUrlList";
    public static final String MAX_CURSOR = "max_cursor";
    public static final String POSITION = "position";
    @BindView(R.id.verticalviewpager)
    VerticalViewPager verticalviewpager;
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
    private int position;
    private long max_cursor = 0;
    private Context context;
    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_vertical_video;
    }


    @Override
    protected void initView() {
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    protected void initData() {
        context = VerticalVideoActivity.this;
        mainVideoDataBeanList = (List<MainVideoDataBean>) WeakDataHolderUtil.getInstance().getData(VIDEO_URL_LIST);
        position = getIntent().getIntExtra(POSITION, -1);
        max_cursor = getIntent().getIntExtra(MAX_CURSOR, -1);
        mCurrentItem = position;
        ijkVideoView = new IjkVideoView(this);
        PlayerConfig config = new PlayerConfig.Builder().setLooping().build();
        ijkVideoView.setPlayerConfig(config);
        douYinController = new DouYinController(this);
        ijkVideoView.setVideoController(douYinController);
        setImageData();
    }


    /**
     * 根据传过来的list
     * 设置页面详情
     */
    private void setImageData() {
        for (MainVideoDataBean item : mainVideoDataBeanList) {
            View view = LayoutInflater.from(this).inflate(R.layout.activity_vertical_video_item, null);
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
        ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_DEFAULT);//充满屏幕
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
        String url = DouyinUtil.getEncryptUrl(this, 0, max_cursor);
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

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
        if (douYinController != null) {
            douYinController.setSelect(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ijkVideoView.pause();
        if (douYinController != null) {
            douYinController.getIv_play().setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkVideoView.release();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new RefreshEvent(mainVideoDataBeanList, mCurrentItem, max_cursor));
    }
}
