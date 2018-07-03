package com.lanhuawei.cn.doraemonvideo.common.pulltorefresh;

import com.lanhuawei.cn.doraemonvideo.common.pulltorefresh.indicator.PtrIndicator;

/**
 * Created by Ivan.L on 2018/7/3.
 * 下拉刷新UI处理
 */

public interface PtrUIHandler {
    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     *
     * @param frame
     */
    void onUIReset(PtrFrameLayout frame);

    /**
     * prepare for loading
     *
     * @param frame
     */
    void onUIRefreshPrepare(PtrFrameLayout frame);

    /**
     * perform refreshing UI
     */
    void onUIRefreshBegin(PtrFrameLayout frame);

    /**
     * perform UI after refresh
     */
    void onUIRefreshComplete(PtrFrameLayout frame);

    void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator);
}
