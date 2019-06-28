package com.jiyun.qmdemo6;

import android.util.Log;

import com.jiyun.mygreendaodemo2.dao.DaoMaster;
import com.jiyun.mygreendaodemo2.dao.DaoSession;
import com.jiyun.mygreendaodemo2.dao.GirlsDao;

import java.util.List;

public class Girlsutil {
    private static final String TAG = "Girlsutil";
    private static DaoSession daoSession = MyApplication.getdaoSession();

    public static void insert(Girls girls) {
        //去重添加
        String id = girls.get_id();
        Girls itemquery = itemquery(id);
        if (itemquery == null) {
            daoSession.insert(girls);
        }

    }

    public static Girls itemquery(String _id) {
        Girls girl = daoSession.queryBuilder(Girls.class)
                .where(GirlsDao.Properties._id.eq(_id))
                .build()
                .unique();
        return girl;

    }

    //删除
    public static void Delete(Girls girls) {
        String id = girls.get_id();
        Girls itemquery = itemquery(id);
        if (itemquery != null) {
            daoSession.delete(girls);
        } else {
            Log.d(TAG, "Delete: 不存在");
        }

    }

    public static List<Girls> QueryAll() {
        List<Girls> girls = daoSession.loadAll(Girls.class);
        return girls;
    }


}
