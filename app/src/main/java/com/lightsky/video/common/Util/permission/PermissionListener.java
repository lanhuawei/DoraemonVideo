package com.lightsky.video.common.Util.permission;

/**
 * Created by Ivan.L on 2018/8/7.
 * 权限监听
 */
public interface PermissionListener {
    /**
     * 授权
     */
    void onPermit(int requestCode, String... permission);
    /**
     * 未授权
     */
    void onCancel(int requestCode, String... permission);
}
