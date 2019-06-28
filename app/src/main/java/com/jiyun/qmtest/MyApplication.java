package com.jiyun.qmtest;

import android.app.Application;

import com.jiyun.qmtest.dao.DaoMaster;
import com.jiyun.qmtest.dao.DaoSession;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "girls.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoSession GetDaoSession() {
        return daoSession;
    }

}
