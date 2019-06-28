package com.jiyun.qmdemo3.presenter;

import com.jiyun.qmdemo3.MyCallBack;
import com.jiyun.qmdemo3.mainbean.GirlsBean;
import com.jiyun.qmdemo3.model.Modelimpl;
import com.jiyun.qmdemo3.view.MyView;

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
                view.succeedui(girllist);
            }

            @Override
            public void error(String error) {
                view.errorui(error);
            }
        });
    }
}
