package com.jiyun.qmdemo4.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.jiyun.qmdemo4.Beans.GirlsBean;
import com.jiyun.qmdemo4.R;
import com.jiyun.qmdemo4.adapers.ShowRecAdaper;
import com.jiyun.qmdemo4.presenter.Presenterimpl;
import com.jiyun.qmdemo4.view.MyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment implements MyView {

    private int pace = 1;
    private View view;
    private RecyclerView mShowRec;
    private SmartRefreshLayout mShowSm;
    private Presenterimpl presenterimpl;
    private ShowRecAdaper showRecAdaper;
    private static final String TAG = "ShowFragment";

    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, null);
        initView(view);
        initPresenter();
        initShowRec();
        return view;
    }

    private void initShowRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mShowRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mShowRec.addItemDecoration(dd);
        showRecAdaper = new ShowRecAdaper(getActivity());
        mShowRec.setAdapter(showRecAdaper);
        mShowSm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pace = 1;
                presenterimpl.getgirllist(pace);
            }
        });
        mShowSm.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pace++;
                presenterimpl.getgirllist(pace);
            }
        });
        showRecAdaper.setIsChick(new ShowRecAdaper.IsChick() {

            private PopupWindow popupWindow;

            @Override
            public void LongChick(final int i) {
                View view = View.inflate(getActivity(), R.layout.populayout, null);
                popupWindow = new PopupWindow(view, 300, 300);
                popupWindow.showAtLocation(mShowRec, Gravity.CENTER, 0, 0);
                View poputv = view.findViewById(R.id.popuitem_tv);
                poputv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRecAdaper.Delete(i);
                        popupWindow.dismiss();
                    }
                });

            }
        });
    }

    private void initPresenter() {
        presenterimpl = new Presenterimpl(this);
        presenterimpl.getgirllist(pace);
    }

    private void initView(View view) {
        mShowRec = (RecyclerView) view.findViewById(R.id.show_rec);
        mShowSm = (SmartRefreshLayout) view.findViewById(R.id.show_sm);
    }


    @Override
    public void SucceedView(List<GirlsBean.ResultsBean> girllist) {
        if (pace == 1) {
            showRecAdaper.Refresh(girllist);
            mShowSm.finishRefresh();
        } else {
            showRecAdaper.LoadMore(girllist);
            mShowSm.finishLoadMore();
        }
    }

    @Override
    public void ErrorView(String error) {
        Log.d(TAG, "ErrorView: " + error);
    }
}
