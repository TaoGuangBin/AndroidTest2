package com.jiyun.qmtest;

import com.jiyun.qmtest.bean.GirlsBean;

import java.util.List;

public interface MyCallBack {
    void succeed (List<GirlsBean.ResultsBean> girllist);
    void defeated(String error);        ;


}
