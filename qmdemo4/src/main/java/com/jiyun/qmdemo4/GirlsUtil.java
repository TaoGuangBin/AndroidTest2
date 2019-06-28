package com.jiyun.qmdemo4;

import android.app.Application;
import android.util.Log;

import com.jiyun.qmdemo4.dao.DaoSession;
import com.jiyun.qmdemo4.dao.GirlsDao;

import java.util.List;

public class GirlsUtil {

    private static DaoSession daosession = MyApplication.getdaosession();
    private static final String TAG = "GirlsUtil";

    public static void Insert(Girls girls) {
        String id = girls.get_id();
        Girls girl = ItemQuery(id);
        if (girl == null) {
            daosession.insert(girls);
        } else {
            Log.d(TAG, "insert: " + "无需重复添加");
        }

    }


    public static Girls ItemQuery(String _id) {
        Girls girl = daosession.queryBuilder(Girls.class)
                .where(GirlsDao.Properties._id.eq(_id))
                .build()
                .unique();
        return girl;
    }

    public static void Delete(Girls girls) {
        String id = girls.get_id();
        Girls girl = ItemQuery(id);
        if (girl != null) {
            daosession.delete(girls);
        } else {
            Log.d(TAG, "Delete: " + "没有该对象");
        }
    }

    public static List<Girls> QueryAll() {
        List<Girls> girls = daosession.loadAll(Girls.class);
        return girls;
    }

}
