package com.jiyun.qmdemo6;

import com.jiyun.qmdemo6.beans.GirlBean;

import java.util.List;

public interface MyCallBack {
    void succeed(List<GirlBean.ResultsBean> girllist);
    void error(String error);
}
