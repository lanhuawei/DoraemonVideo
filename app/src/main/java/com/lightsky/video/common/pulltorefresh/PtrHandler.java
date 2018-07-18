package com.lightsky.video.common.pulltorefresh;

import android.view.View;

/**
 * Created by Ivan.L on 2018/7/3.
 *
 */

public interface PtrHandler {
    /**
     * Check can do refresh or not. For example the content is empty or the first child is in view.
     * <p/>
     * {@link PtrDefaultHandler#checkContentCanBePulledDown}
     */
    boolean checkCanDoRefresh(final PtrFrameLayout frame, final View content, final View header);

    /**
     * When refresh begin
     *
     * @param frame
     */
    void onRefreshBegin(final PtrFrameLayout frame);
}
