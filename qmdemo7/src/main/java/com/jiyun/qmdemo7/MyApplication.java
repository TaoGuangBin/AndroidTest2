package com.jiyun.qmdemo7;

import android.app.Application;

import com.jiyun.qmdemo7.dao.DaoMaster;
import com.jiyun.qmdemo7.dao.DaoSession;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "girls.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDatabase());
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
