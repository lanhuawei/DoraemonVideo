package com.lanhuawei.cn.doraemonvideo.library.greendao.generator.db;


import com.lanhuawei.cn.doraemonvideo.MyApplication;

/**
 * GreenDao数据库操作类
 */
public class GreenDaoManager {

    private DaoMaster.DevOpenHelper devOpenHelper;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static final String DB_NAME = "doraemon.db";

    private GreenDaoManager() {
        if (devOpenHelper == null || daoMaster == null || daoSession == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), DB_NAME, null);
            daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
            daoSession = daoMaster.newSession();
        }
    }

    private static class Singleton {
        private static GreenDaoManager mInstance = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance() {
        return Singleton.mInstance;
    }

    public DaoMaster.DevOpenHelper getDevOpenHelper() {
        return devOpenHelper;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
