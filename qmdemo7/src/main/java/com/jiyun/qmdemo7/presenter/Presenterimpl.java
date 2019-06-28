package com.jiyun.qmdemo7.presenter;

import com.jiyun.qmdemo7.Beans.GirlsBean;
import com.jiyun.qmdemo7.MyCallBack;
import com.jiyun.qmdemo7.model.Modelimpl;
import com.jiyun.qmdemo7.view.MyView;

import java.util.List;

public class Presenterimpl implements MyPresenter {
    MyView view;
    private final Modelimpl modelimpl;

    public Presenterimpl(MyView view) {
        this.view = view;
        modelimpl = new Modelimpl();
    }

    @Override
    public void getgirllist() {
        modelimpl.getgirllist(new MyCallBack() {
            @Override
            public void succeed(List<GirlsBean.ResultsBean> girllist) {
                view.succeediv(girllist);
            }

            @Override
            public void error(String error) {
                view.erroriv(error);
            }
        });
    }
}
