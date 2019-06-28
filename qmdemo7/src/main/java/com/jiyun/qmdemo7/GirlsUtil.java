package com.jiyun.qmdemo7;

import android.util.Log;

import com.jiyun.qmdemo7.dao.DaoSession;
import com.jiyun.qmdemo7.dao.GirlsDao;

import java.util.List;

public class GirlsUtil {
    private static final String TAG = "GirlsUtil";
    private static DaoSession daoSession = MyApplication.getDaoSession();

    public static void Insert(Girls girls) {
        String id = girls.get_id();
        Girls girl = ItemQuery(id);
        if (girl == null) {
            daoSession.insert(girls);
        } else {
            Log.d(TAG, "Insert: " + "无需重复添加");
        }

    }

    public static Girls ItemQuery(String _id) {
        Girls girl = daoSession.queryBuilder(Girls.class)
                .where(GirlsDao.Properties._id.eq(_id))
                .build()
                .unique();
        return girl;

    }

    public static void Delete(Girls girls) {
        String id = girls.get_id();
        Girls girls1 = ItemQuery(id);
        if (girls1 != null) {
            daoSession.delete(girls);
        } else {
            Log.d(TAG, "Delete: " + "数据不存在");
        }
    }

    public static List<Girls> QueryAll() {
        List<Girls> girls = daoSession.loadAll(Girls.class);
        return girls;
    }

}
