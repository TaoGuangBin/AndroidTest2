package com.jiyun.qmdemo7;

import com.jiyun.qmdemo7.Beans.GirlsBean;

import java.util.List;

public interface MyCallBack {
    void succeed(List<GirlsBean.ResultsBean> girllist);

    void error(String error);
}
