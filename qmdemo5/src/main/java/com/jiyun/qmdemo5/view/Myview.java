package com.jiyun.qmdemo5.view;

import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;

public interface Myview {
    void succeedbannerview(List<BannerBean.DataBean> bannerlist);
    void errorbannerview(String error);
    void succeedgirllistview(List<GirlsBean.ResultsBean> girllist);
    void errorgirlview(String error);

}
