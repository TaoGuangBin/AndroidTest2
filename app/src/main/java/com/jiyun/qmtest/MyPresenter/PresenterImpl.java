package com.jiyun.qmtest.MyPresenter;

import com.jiyun.qmtest.MyCallBack;
import com.jiyun.qmtest.MyModel.MyModelImpl;
import com.jiyun.qmtest.MyView.MyView;
import com.jiyun.qmtest.bean.GirlsBean;

import java.util.List;

public class PresenterImpl implements MyPresenter{

    MyView myView;
    private final MyModelImpl myModel;

    public PresenterImpl(MyView myView) {
        this.myView = myView;
        myModel = new MyModelImpl();
    }

    @Override
    public void getgirllist() {
        myModel.getGirllist(new MyCallBack() {
            @Override
            public void succeed(List<GirlsBean.ResultsBean> girllist) {
                myView.succeedui(girllist);
            }

            @Override
            public void defeated(String error) {
                myView.defeatedui(error);
            }
        });
    }
}
