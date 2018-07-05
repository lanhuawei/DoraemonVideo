package com.lanhuawei.cn.doraemonvideo.common.Util;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan.L on 2018/7/5.
 * Intent传递大量数据出现TransactionTooLargeException异常的解决方案
 */

public class WeakDataHolderUtil {
    private static WeakDataHolderUtil instance;

    public static WeakDataHolderUtil getInstance() {
        if (instance == null) {
            synchronized (WeakDataHolderUtil.class) {
                if (instance == null) {
                    instance = new WeakDataHolderUtil();
                }
            }
        }
        return instance;
    }

    private Map<String, WeakReference<Object>> map = new HashMap<>();

    /**
     * 数据存储
     *
     * @param id
     * @param object
     */
    public void saveData(String id, Object object) {
        map.put(id, new WeakReference<>(object));
    }

    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    public Object getData(String id) {
        WeakReference<Object> weakReference = map.get(id);
        return weakReference.get();
    }
}
