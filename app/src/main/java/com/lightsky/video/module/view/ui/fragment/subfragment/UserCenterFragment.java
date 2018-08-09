package com.lightsky.video.module.view.ui.fragment.subfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lightsky.video.R;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.model.event.RefreshEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Ivan.L on 2018/7/23.
 * 用户中心
 */

public class UserCenterFragment extends BaseFragment {
    private RefreshEvent refreshEvent;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_user_center;
    }
    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);
        StatusBarFontHelper.setStatusBarMode(getActivity(), true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onViewReallyCreated(View view) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCurrentRefresh(RefreshEvent event) {
        refreshEvent = event;
    }


    @Override
    public boolean onBackPressed() {
        EventBus.getDefault().post(new RefreshEvent(refreshEvent.getMainVideoDataBeans(), refreshEvent.getPosition(), refreshEvent.getMax_cursor()));
        return super.onBackPressed();
    }
}
