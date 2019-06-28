package com.jiyun.qmdemo4.presenter;

import com.jiyun.qmdemo4.Beans.GirlsBean;
import com.jiyun.qmdemo4.MyCallBack;
import com.jiyun.qmdemo4.model.Modelimpl;
import com.jiyun.qmdemo4.view.MyView;

import java.util.List;

public class Presenterimpl implements MyPersenter {
    MyView view;
    private final Modelimpl modelimpl;

    public Presenterimpl(final MyView view) {
        this.view = view;
        modelimpl = new Modelimpl();
    }

    @Override
    public void getgirllist(int pace) {
        modelimpl.getgirllist(pace, new MyCallBack() {
            @Override
            public void succeed(List<GirlsBean.ResultsBean> girllist) {
                /*if (pace == 1) {
                    view.SucceedView(girllist);
                }else {
                    view.SucceedView(girllist);
                }*/
                view.SucceedView(girllist);
            }

            @Override
            public void error(String error) {
                view.ErrorView(error);
            }
        });
    }
}
