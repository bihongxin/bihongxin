package com.bwie.TaoBao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.bwie.TaoBao.bean.WatchBean;

import com.bwie.TaoBao.dao.WatchBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig watchBeanDaoConfig;

    private final WatchBeanDao watchBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        watchBeanDaoConfig = daoConfigMap.get(WatchBeanDao.class).clone();
        watchBeanDaoConfig.initIdentityScope(type);

        watchBeanDao = new WatchBeanDao(watchBeanDaoConfig, this);

        registerDao(WatchBean.class, watchBeanDao);
    }
    
    public void clear() {
        watchBeanDaoConfig.clearIdentityScope();
    }

    public WatchBeanDao getWatchBeanDao() {
        return watchBeanDao;
    }

}
