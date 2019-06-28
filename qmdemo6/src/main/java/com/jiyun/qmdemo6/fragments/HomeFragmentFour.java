package com.jiyun.qmdemo6.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jiyun.qmdemo6.Girls;
import com.jiyun.qmdemo6.Girlsutil;
import com.jiyun.qmdemo6.R;
import com.jiyun.qmdemo6.adapers.HomeFragmentFourRecAdaper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentFour extends Fragment {


    private View view;
    private RecyclerView mFfourRec;
    private HomeFragmentFourRecAdaper homeFragmentFourRecAdaper;
    private View popubt;

    public HomeFragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home_fragment_four, null);
        initView(view);
        initRec();
        initData();
        return view;
    }

    private void initData() {
        List<Girls> girls = Girlsutil.QueryAll();
        homeFragmentFourRecAdaper.initdate(girls);
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mFfourRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mFfourRec.addItemDecoration(dd);
        homeFragmentFourRecAdaper = new HomeFragmentFourRecAdaper(getActivity());
        mFfourRec.setAdapter(homeFragmentFourRecAdaper);
        View view = View.inflate(getActivity(), R.layout.popuwindowlayout, null);
        final PopupWindow popupWindow = new PopupWindow(view, 200, 200);
        popubt = view.findViewById(R.id.popu_tv);
        homeFragmentFourRecAdaper.setIsChick(new HomeFragmentFourRecAdaper.IsChick() {
            @Override
            public void onechick(final int i) {
                popupWindow.showAtLocation(mFfourRec, Gravity.CENTER, 0, 0);
                popubt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        homeFragmentFourRecAdaper.initDelete(i);
                        popupWindow.dismiss();
                    }
                });
            }
        });


    }

    private void initView(View view) {
        mFfourRec = (RecyclerView) view.findViewById(R.id.ffour_rec);
    }
}
