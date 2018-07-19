package com.lightsky.video.common.Util;

import android.view.View;

/**
 * Created by Ivan.L on 2018/7/4.
 * 防止多次点击的工具类
 */

public class NoDoubleClickUtil {
    private static long tryClick;
    private static long lastClickTimeOne;
    private final static int SPACE_TIME = 1000;
    private static long lastClickTimeTwo;

    public synchronized static boolean isDoubleClickOne() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTimeOne >
                SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTimeOne = currentTime;
        return isClick2;
    }


    public synchronized static boolean isDoubleClickTry() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = currentTime - tryClick <= SPACE_TIME;
        tryClick = currentTime;
        return isClick2;
    }

    public synchronized static boolean isDoubleClickTwo() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        isClick2 = currentTime - lastClickTimeTwo <= SPACE_TIME;
        lastClickTimeTwo = currentTime;
        return isClick2;
    }




    public static void setEnable(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {

                view.setEnabled(true);
            }
        });
    }
}
