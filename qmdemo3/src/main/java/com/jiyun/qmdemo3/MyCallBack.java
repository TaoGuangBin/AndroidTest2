package com.jiyun.qmdemo3;

import com.jiyun.qmdemo3.mainbean.GirlsBean;

import java.util.List;

public interface MyCallBack {
    void succeed(List<GirlsBean.ResultsBean> girllist);
    void error(String error);
}
