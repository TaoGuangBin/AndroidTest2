package com.jiyun.qmtest.MyView;

import com.jiyun.qmtest.bean.GirlsBean;

import java.util.List;

public interface MyView {
    void succeedui(List<GirlsBean.ResultsBean> girllist);
    void defeatedui(String error);
}
