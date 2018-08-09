package com.lightsky.video.module.view.ui.fragment.subfragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lightsky.video.R;
import com.lightsky.video.common.Util.permission.Permission;
import com.lightsky.video.common.Util.permission.PermissionListener;
import com.lightsky.video.common.Util.permission.PermissionUtils;
import com.lightsky.video.common.Util.statusbar.StatusBarFontHelper;
import com.lightsky.video.common.Util.statusbar.statusbarcompat.StatusBarCompat;
import com.lightsky.video.module.base.BaseFragment;
import com.lightsky.video.module.model.event.RefreshEvent;
import com.lightsky.video.module.view.ui.activity.PictureSelectActivity;
import com.lightsky.video.module.view.ui.activity.VideoSelectActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Ivan.L on 2018/7/23.
 * 发布
 */

public class PublishFragment extends BaseFragment {
    @BindView(R.id.tv_picture)
    TextView tvPicture;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    private RefreshEvent refreshEvent;
    private Context context;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void initView() {
        context = getActivity();
        StatusBarCompat.setStatusBarColor(getActivity(), 0xfffffff);
        StatusBarFontHelper.setStatusBarMode(getActivity(), true);
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.tv_picture, R.id.tv_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_picture:
            case R.id.tv_video:
                judgePermission(view);
                break;
        }
    }


    @Override
    protected void onViewReallyCreated(View view) {
        unbinder = ButterKnife.bind(this, view);
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


    /**
     * 权限判断
     *
     * @param view
     */
    private void judgePermission(final View view) {
        if (PermissionUtils.needRequestPermission()) {//需要授权
            Permission.with(this)
                    .requestCode(200)
                    .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callBack(new PermissionListener() {
                        @Override
                        public void onPermit(int requestCode, String... permission) {
                            if (view == tvPicture) {
                                jumpToPictureActivity(true);
                            } else if (view == tvVideo) {
                                jumpToPictureActivity(false);
                            }
                        }

                        @Override
                        public void onCancel(int requestCode, String... permission) {
                            PermissionUtils.goSetting(context);
                        }
                    })
                    .send();
        } else {//不需要授权
            if (view == tvPicture) {
                jumpToPictureActivity(true);
            } else if (view == tvVideo) {
                jumpToPictureActivity(false);
            }
        }
    }

    /**
     * activity跳转
     * @param isPic
     */
    private void jumpToPictureActivity(boolean isPic) {
        Intent intent = new Intent();
        if (isPic) {
            intent.setClass(context, PictureSelectActivity.class);
        } else {
            intent.setClass(context, VideoSelectActivity.class);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);

    }

}
