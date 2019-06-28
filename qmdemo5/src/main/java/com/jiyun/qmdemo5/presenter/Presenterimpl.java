package com.jiyun.qmdemo5.presenter;

import com.jiyun.qmdemo5.MyCallBack;
import com.jiyun.qmdemo5.model.Modelimpl;
import com.jiyun.qmdemo5.view.Myview;

import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;

public class Presenterimpl implements Mypresenter {
    Myview view;
    private final Modelimpl modelimpl;

    public Presenterimpl(Myview view) {
        this.view = view;
        modelimpl = new Modelimpl();
    }

    @Override
    public void getlist(int pace) {
        modelimpl.getlist(pace,new MyCallBack() {
            @Override
            public void girlsucceed(List<GirlsBean.ResultsBean> girllist) {
                view.succeedgirllistview(girllist);
            }

            @Override
            public void girlerror(String error) {
                view.errorgirlview(error);
            }

            @Override
            public void bannersucceed(List<BannerBean.DataBean> bannerlist) {
                view.succeedbannerview(bannerlist);
            }

            @Override
            public void bannererror(String error) {
                view.errorbannerview(error);
            }
        });

    }
}
