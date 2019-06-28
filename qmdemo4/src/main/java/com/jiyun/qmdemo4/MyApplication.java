package com.jiyun.qmdemo4;

import android.app.Application;

import com.jiyun.qmdemo4.dao.DaoMaster;
import com.jiyun.qmdemo4.dao.DaoSession;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "girls.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getdaosession() {
        return daoSession;
    }
}
