package com.jiyun.qmdemo4.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiyun.qmdemo4.Girls;
import com.jiyun.qmdemo4.GirlsUtil;
import com.jiyun.qmdemo4.R;
import com.jiyun.qmdemo4.adapers.QueryAllRecAdaper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryAllFragment extends Fragment {


    private View view;
    private RecyclerView mQueryallRec;
    private QueryAllRecAdaper queryAllRecAdaper;

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
        queryAllRecAdaper.initDate(girls);
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mQueryallRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mQueryallRec.addItemDecoration(dd);
        queryAllRecAdaper = new QueryAllRecAdaper(getActivity());
        mQueryallRec.setAdapter(queryAllRecAdaper);
        queryAllRecAdaper.setIsChick(new QueryAllRecAdaper.IsChick() {
            @Override
            public void oneChick(int i) {
                queryAllRecAdaper.Delete(i);
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(View view) {
        mQueryallRec = (RecyclerView) view.findViewById(R.id.queryall_rec);
    }
}
