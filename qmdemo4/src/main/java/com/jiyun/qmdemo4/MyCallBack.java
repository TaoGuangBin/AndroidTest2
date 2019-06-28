package com.jiyun.qmdemo4;

import com.jiyun.qmdemo4.Beans.GirlsBean;

import java.util.List;

public interface MyCallBack {
    void succeed(List<GirlsBean.ResultsBean> girllist);

    void error(String error);
}
