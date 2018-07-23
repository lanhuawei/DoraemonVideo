package com.lightsky.video.module.view.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lightsky.video.R;
import com.lightsky.video.module.base.BaseActivity;
import com.lightsky.video.module.view.adapter.VerticalVideoAdapter;
import com.lightsky.video.module.view.ui.fragment.subfragment.PublishFragment;
import com.lightsky.video.module.view.ui.fragment.subfragment.UserCenterFragment;
import com.lightsky.video.module.view.ui.fragment.subfragment.VerticalVideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/23.
 * 仿抖音页面
 * 全屏视频
 * 可以上下滑动，左右滑动页面
 */

public class VerticalVideoMainActivity extends BaseActivity {

    @BindView(R.id.vp_video_detail)
    ViewPager vpVideoDetail;
    private List<Fragment> fragmentList;
    private VerticalVideoAdapter verticalVideoAdapter;
    public static final String MAX_CURSOR = "max_cursor";
    public static final String POSITION = "position";
    private int position;
    private long max_cursor = 0;
    @Override
    protected int layoutResId() {
        return R.layout.activity_vertical_video_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        position = getIntent().getIntExtra(POSITION, -1);
        max_cursor = getIntent().getIntExtra(MAX_CURSOR, -1);

        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmentList.add(new PublishFragment());
            fragmentList.add(VerticalVideoFragment.newInstance(max_cursor, position));
            fragmentList.add(new UserCenterFragment());
        }
        verticalVideoAdapter = new VerticalVideoAdapter(getSupportFragmentManager(), fragmentList);
        vpVideoDetail.setAdapter(verticalVideoAdapter);
        vpVideoDetail.setCurrentItem(1);
    }

}
