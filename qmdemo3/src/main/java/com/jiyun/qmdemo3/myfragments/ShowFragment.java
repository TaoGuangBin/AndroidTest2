package com.jiyun.qmdemo3.myfragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiyun.qmdemo3.BaiDuActivity;
import com.jiyun.qmdemo3.R;
import com.jiyun.qmdemo3.mainbean.GirlsBean;
import com.jiyun.qmdemo3.myadapers.ShowRecAdaper;
import com.jiyun.qmdemo3.presenter.Presenterimpl;
import com.jiyun.qmdemo3.view.MyView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment implements MyView {


    private View view;
    private RecyclerView mShowRec;
    private Presenterimpl presenterimpl;
    private static final String TAG = "ShowFragment";
    private ShowRecAdaper showRecAdaper;

    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, null);
        initView(view);
        initRec();
        initPresenter();
        return view;
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mShowRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mShowRec.addItemDecoration(dd);
        showRecAdaper = new ShowRecAdaper(getActivity());
        mShowRec.setAdapter(showRecAdaper);
        showRecAdaper.setIsChick(new ShowRecAdaper.IsChick() {
            @Override
            public void onechick(int i) {
                showRecAdaper.Delete(i);
            }

            @Override
            public void longchick() {
                Intent intent = new Intent(getActivity(), BaiDuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initPresenter() {
        presenterimpl = new Presenterimpl(this);
        presenterimpl.getgirllist();
    }

    private void initView(View view) {
        mShowRec = (RecyclerView) view.findViewById(R.id.show_rec);
    }


    @Override
    public void succeedui(List<GirlsBean.ResultsBean> girllist) {
        showRecAdaper.initData(girllist);
    }

    @Override
    public void errorui(String error) {
        Log.d(TAG, "errorui: " + error);
    }
}
