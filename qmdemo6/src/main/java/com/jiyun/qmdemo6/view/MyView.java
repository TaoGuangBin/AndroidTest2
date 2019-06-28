package com.jiyun.qmdemo6.view;

import com.jiyun.qmdemo6.beans.GirlBean;

import java.util.List;

public interface MyView {
    void succeediv(List<GirlBean.ResultsBean> girllist);
    void erroriv(String error);
}
