package com.jiyun.qmdemo7.fragments;


import android.app.Application;
import android.content.Context;
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

import com.jiyun.qmdemo7.Girls;
import com.jiyun.qmdemo7.GirlsUtil;
import com.jiyun.qmdemo7.R;
import com.jiyun.qmdemo7.adapers.InsertRecAdaper;
import com.jiyun.qmdemo7.adapers.QueryRecAdaper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryAllFragment extends Fragment {


    private View view;
    private RecyclerView mQueryRec;
    private QueryRecAdaper queryRecAdaper;

    public QueryAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_all, null);
        initView(view);
        initRec();
        initData();
        return view;
    }

    private void initData() {
        List<Girls> girls = GirlsUtil.QueryAll();
        queryRecAdaper.initdata(girls);


    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mQueryRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mQueryRec.addItemDecoration(dd);
        queryRecAdaper = new QueryRecAdaper(getActivity());
        mQueryRec.setAdapter(queryRecAdaper);
        View view = View.inflate(getActivity(),R.layout.populayout,null);
        final PopupWindow popupWindow = new PopupWindow(view, 200, 200);
        final View poputv = view.findViewById(R.id.popu_tv);
        queryRecAdaper.setIsChick(new QueryRecAdaper.IsChick() {
            @Override
            public void longCheck(final int i) {
                popupWindow.showAtLocation(mQueryRec, Gravity.CENTER,0,0);
                poputv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        queryRecAdaper.Delete(i);
                    }
                });

            }
        });



    }

    private void initView(View view) {
        mQueryRec = (RecyclerView) view.findViewById(R.id.query_rec);
    }
}
