package com.lightsky.video.module.library.greendao.generator.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lightsky.video.module.entity.UserInfoBean;

import com.lightsky.video.module.library.greendao.generator.db.UserInfoBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInfoBeanDaoConfig;

    private final UserInfoBeanDao userInfoBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInfoBeanDaoConfig = daoConfigMap.get(UserInfoBeanDao.class).clone();
        userInfoBeanDaoConfig.initIdentityScope(type);

        userInfoBeanDao = new UserInfoBeanDao(userInfoBeanDaoConfig, this);

        registerDao(UserInfoBean.class, userInfoBeanDao);
    }
    
    public void clear() {
        userInfoBeanDaoConfig.clearIdentityScope();
    }

    public UserInfoBeanDao getUserInfoBeanDao() {
        return userInfoBeanDao;
    }

}
