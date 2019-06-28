package com.jiyun.qmdemo7.view;

import com.jiyun.qmdemo7.Beans.GirlsBean;

import java.util.List;

public interface MyView {
    void succeediv(List<GirlsBean.ResultsBean> girllist);

    void erroriv(String error);
}
