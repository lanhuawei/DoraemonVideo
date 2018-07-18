package com.lightsky.video.module.view.holder;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lightsky.video.MyApplication;
import com.lightsky.video.R;
import com.lightsky.video.common.Util.DensityUtil;
import com.lightsky.video.common.Util.KindsOfUtil;
import com.lightsky.video.common.mywidget.recyclerview.adapter.BaseRecyclerViewHolder;
import com.lightsky.video.common.videoplayer.util.WindowUtil;
import com.lightsky.video.module.bean.databean.MainVideoDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ivan.L on 2018/7/4.
 * 首页抖音页面展示Holder
 */

public class DouYinVideoShowHolder extends BaseRecyclerViewHolder<MainVideoDataBean> {
    @BindView(R.id.sdv_img) SimpleDraweeView sdvImg;
    @BindView(R.id.tv_play_count) TextView tvPlayCount;
    @BindView(R.id.tv_like_count) TextView tvLikeCount;
    @BindView(R.id.tv_video_title) TextView tvVideoTitle;

    public DouYinVideoShowHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    protected void onItemDataUpdated(@Nullable MainVideoDataBean mainVideoDataBean) {
        if (mainVideoDataBean != null) {
            ViewGroup.LayoutParams params = sdvImg.getLayoutParams();
            params.width = (
                    WindowUtil.getScreenWidth(MyApplication.getInstance()) - DensityUtil.dip2px(MyApplication.getInstance(), 2)) / 2;
            params.height = (params.width) * 8 / 5;
            sdvImg.setLayoutParams(params);
            final Uri uri = Uri.parse(mainVideoDataBean.getDynamicCover());
            if (isNotEqualsUriPath(sdvImg, mainVideoDataBean.getDynamicCover())) {
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true)
                        .setOldController(sdvImg.getController())
                        .setControllerListener(new BaseControllerListener<ImageInfo>() {
                            @Override
                            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
//                                super.onFinalImageSet(id, imageInfo, animatable);
                                sdvImg.setTag(R.id.sdv_img, uri);
                            }
                        }).build();
                sdvImg.setController(controller);
            }
            tvVideoTitle.setText(mainVideoDataBean.getTitle());
            tvPlayCount.setText(KindsOfUtil.formatNumber(mainVideoDataBean.getPlayCount()) + "播放");
            tvLikeCount.setText(KindsOfUtil.formatNumber(mainVideoDataBean.getLikeCount()) + "赞");
        }
    }


    /**
     * 解决fresco 闪屏
     * @param sdvImg
     * @param imgUrl
     * @return
     */
    private boolean isNotEqualsUriPath(SimpleDraweeView sdvImg, String imgUrl) {
        return !(TextUtils.isEmpty(imgUrl) || TextUtils.isEmpty(sdvImg.getTag(R.id.sdv_img) + "")) && !(sdvImg.getTag(R.id.sdv_img) + "").equals(imgUrl);
    }

}
