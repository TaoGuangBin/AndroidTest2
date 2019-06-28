package com.jiyun.qmtest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jiyun.qmtest.BaiDuActivity;
import com.jiyun.qmtest.MyPresenter.PresenterImpl;
import com.jiyun.qmtest.MyView.MyView;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.adapers.MainInsertAdaper;
import com.jiyun.qmtest.adapers.MainShowAdaper;
import com.jiyun.qmtest.bean.GirlsBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainShowFragment extends Fragment implements MyView {
    //1811A 陶广滨

    private View view;
    private RecyclerView mMainshowRec;
    private MainShowAdaper mainShowAdaper;

    public MainShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_show, null);
        initView(view);
        initRec();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            initPresenter();
        }
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mMainshowRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mMainshowRec.addItemDecoration(dd);
        mainShowAdaper = new MainShowAdaper(getActivity());
        mMainshowRec.setAdapter(mainShowAdaper);
        mainShowAdaper.setIsChick(new MainShowAdaper.IsChick() {
            @Override
            public void onechick(final int i) {
                View view = View.inflate(getActivity(), R.layout.mainshowpwlayout, null);
                final PopupWindow popupWindow = new PopupWindow(view, 200, 200);
                popupWindow.showAtLocation(mMainshowRec, Gravity.CENTER, 0, 0);
                view.findViewById(R.id.pw_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainShowAdaper.initDelete(i);
                        popupWindow.dismiss();
                    }
                });
            }

            @Override
            public void Longchick(int i) {
                Intent intent = new Intent(getActivity(), BaiDuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initPresenter() {
        if (mainShowAdaper != null) {
            PresenterImpl presenter = new PresenterImpl(this);
            presenter.getgirllist();
        }

    }


    private void initView(View view) {
        mMainshowRec = (RecyclerView) view.findViewById(R.id.mainshow_rec);
    }

    @Override
    public void succeedui(List<GirlsBean.ResultsBean> girllist) {
        mainShowAdaper.initDate(girllist);

    }

    private static final String TAG = "MainShowFragment";

    @Override
    public void defeatedui(String error) {
        Log.d(TAG, "defeatedui: " + error);
    }
}
