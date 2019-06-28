package com.jiyun.qmdemo4.view;

import com.jiyun.qmdemo4.Beans.GirlsBean;

import java.util.List;

public interface MyView {
    void SucceedView(List<GirlsBean.ResultsBean> girllist);
    void ErrorView(String error);
}
