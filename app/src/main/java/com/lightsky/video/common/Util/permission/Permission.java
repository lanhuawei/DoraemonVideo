package com.lightsky.video.common.Util.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan.L on 2018/8/7.
 * Permission封装类
 */
public class Permission {
    //持有弱引用HandlerActivity,GC回收时会被回收掉.解决内存泄漏的问题
    private WeakReference<Activity> mWeakActivity;
    private int requestCode;
    private PermissionListener permissionListener;
    private String[] permissions;
    private static Permission instance = new Permission();
    private static List<Integer> codes = new ArrayList<>();

    private Permission() {
    }

    /**
     * 关联上下文
     * @param activity
     * @return
     */
    @NonNull
    public static Permission with(@NonNull Activity activity) {
        instance.setActivity(activity);
        return instance;
    }
    /**
     * 关联上下文
     * 不常用
     * @param fragment
     * @return
     */
    @NonNull
    public static Permission with(@NonNull android.app.Fragment fragment) {
        instance.setActivity(fragment.getActivity());
        return instance;
    }

    /**
     * 关联上下文
     * 通常用的Fragment是这个包下的
     * @param fragment
     * @return
     */
    @NonNull
    public static Permission with(@NonNull android.support.v4.app.Fragment fragment) {
        instance.setActivity(fragment.getActivity());
        return instance;
    }

    /**
     * 设置权限请求码
     *
     * @param requestCode
     * @return
     */
    @NonNull
    public Permission requestCode(@NonNull int requestCode) {
        codes.add(requestCode);
        instance.setRequestCode(requestCode);
        return instance;
    }

    /**
     * 设置请求回调
     *
     * @param listener
     * @return
     */
    @NonNull
    public Permission callBack(@NonNull PermissionListener listener) {
        instance.setPermissionListener(listener);
        return instance;
    }

    /**
     * 请求各种权限
     * @param permissions
     * @return
     */
    @NonNull
    public Permission permission(@NonNull String... permissions) {
        instance.setPermissions(permissions);
        return instance;
    }


    /**
     * 开始请求
     */
    @NonNull
    public void send() {
        if (instance == null || instance.getmWeakActivity().get() == null || instance.getPermissionListener() == null
                || instance.getPermissions() == null) {
            return;
        }

        // 判断是否授权
        if (PermissionUtils.getInstance().checkPermission(instance.getmWeakActivity().get(), instance.getPermissions())) {
            // 已经授权，执行授权回调
            instance.getPermissionListener().onPermit(instance.getRequestCode(), instance.getPermissions());
        } else {
            PermissionUtils.getInstance().requestPermission(instance.getmWeakActivity().get(), instance.getRequestCode(), instance.getPermissions());
        }
    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance == null) {
            return;
        }
        for (int j = 0; j < codes.size(); j++) {
            if (requestCode == codes.get(j)) {
                // 遍历请求时的所有权限
                for (int i = 0; i < grantResults.length; i++) {
                    // 授权
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        instance.getPermissionListener().onPermit(codes.get(j), permissions);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        instance.getPermissionListener().onCancel(codes.get(j), permissions);
                    }

                }
                codes.remove(codes.get(j));
            }
        }
    }


    //==================================以下为get、set方法================================================
    public WeakReference<Activity> getmWeakActivity() {
        return mWeakActivity;
    }

    public void setmWeakActivity(WeakReference<Activity> mWeakActivity) {
        this.mWeakActivity = mWeakActivity;
    }
    public void setActivity(Activity activity) {
        mWeakActivity = new WeakReference<Activity>(activity);
    }


    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public PermissionListener getPermissionListener() {
        return permissionListener;
    }

    public void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public static Permission getInstance() {
        return instance;
    }

    public static void setInstance(Permission instance) {
        Permission.instance = instance;
    }

    public static List<Integer> getCodes() {
        return codes;
    }

    public static void setCodes(List<Integer> codes) {
        Permission.codes = codes;
    }
}
