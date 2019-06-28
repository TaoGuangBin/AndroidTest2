package com.jiyun.qmtest;

import android.util.Log;

import com.jiyun.qmtest.dao.DaoSession;
import com.jiyun.qmtest.dao.GirlsDao;

import java.util.List;

//1811A 陶广滨
public class GirlUtils {
    private static final String TAG = "GirlUtils";
    private static DaoSession daoSession= MyApplication.GetDaoSession();

    public static void insert(Girls girls){
        //去重添加
        String id = girls.get_id();
        Girls itemquery = itemquery(id);
        if (itemquery == null) {
            daoSession.insert(girls);
        }else {
            Log.d(TAG, "insert: "+"已存在，无需重复添加");
        }
    }

    public static Girls itemquery(String _id){
        Girls girls = daoSession.queryBuilder(Girls.class)
                .where(GirlsDao.Properties._id.eq(_id))
                .build()
                .unique();
        return girls;
    }

    public static void Delete(Girls girls){
        String _id = girls.get_id();
        Girls itemquery = itemquery(_id);
        if (itemquery != null) {
            daoSession.delete(girls);
        }else {
            Log.d(TAG, "Delete: "+"数据不存在");
        }
    }
    public static List<Girls> QueryAll(){
        List<Girls> girllist = daoSession.loadAll(Girls.class);
        return girllist;
    }


}
