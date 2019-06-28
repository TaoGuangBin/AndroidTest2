package com.jiyun.qmdemo5;

import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;

public interface MyCallBack {
    void girlsucceed(List<GirlsBean.ResultsBean> girllist);
    void girlerror(String error);
    void bannersucceed(List<BannerBean.DataBean> bannerlist);
    void bannererror(String error);
}
