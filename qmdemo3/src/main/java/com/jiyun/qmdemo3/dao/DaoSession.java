package com.jiyun.qmdemo3.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.jiyun.qmdemo3.Girls;

import com.jiyun.qmdemo3.dao.GirlsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig girlsDaoConfig;

    private final GirlsDao girlsDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        girlsDaoConfig = daoConfigMap.get(GirlsDao.class).clone();
        girlsDaoConfig.initIdentityScope(type);

        girlsDao = new GirlsDao(girlsDaoConfig, this);

        registerDao(Girls.class, girlsDao);
    }
    
    public void clear() {
        girlsDaoConfig.clearIdentityScope();
    }

    public GirlsDao getGirlsDao() {
        return girlsDao;
    }

}
