package com.jiyun.qmdemo6;

import android.app.Application;

import com.jiyun.mygreendaodemo2.dao.DaoMaster;
import com.jiyun.mygreendaodemo2.dao.DaoSession;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "girl.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getdaoSession() {
        return daoSession;
    }
}
