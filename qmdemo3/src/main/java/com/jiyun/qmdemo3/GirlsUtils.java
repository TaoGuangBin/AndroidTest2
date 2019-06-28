package com.jiyun.qmdemo3;


import com.jiyun.qmdemo3.dao.DaoSession;
import com.jiyun.qmdemo3.dao.GirlsDao;

import java.util.List;

public class GirlsUtils {

    private static DaoSession daoSession = MyApplication.getDaoSession();

    public static void insert(Girls girls) {
        //去重添加
        String id = girls.get_id();
        Girls itemquery = itemquery(id);
        if (itemquery == null) {
            daoSession.insert(girls);
        }

    }

    public static Girls itemquery(String _id) {
        Girls girls = daoSession.queryBuilder(Girls.class)
                .where(GirlsDao.Properties._id.eq(_id))
                .build()
                .unique();
        return girls;
    }

    public static void Delete(Girls girls) {
        String id = girls.get_id();
        Girls itemquery = itemquery(id);
        if (itemquery != null) {
            daoSession.delete(girls);
        }
    }

    public static List<Girls> QueryAll() {
        List<Girls> girls = daoSession.loadAll(Girls.class);
        return girls;
    }


}
