package com.jiyun.qmdemo3.view;

import com.jiyun.qmdemo3.mainbean.GirlsBean;

import java.util.List;

public interface MyView {
    void succeedui(List<GirlsBean.ResultsBean> girllist);
    void errorui(String error);
}
