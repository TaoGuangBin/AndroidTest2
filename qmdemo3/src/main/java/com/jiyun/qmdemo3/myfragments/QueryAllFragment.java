package com.jiyun.qmdemo3.myfragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiyun.qmdemo3.Girls;
import com.jiyun.qmdemo3.GirlsUtils;
import com.jiyun.qmdemo3.myadapers.QueryAllRecAdaper;
import com.jiyun.qmdemo3.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryAllFragment extends Fragment {


    private View view;
    public RecyclerView mFqueryRec;
    private QueryAllRecAdaper queryAllRecAdaper;

    public QueryAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_all, null);
        initView(view);
        initQueryAllRec();
        initData();
        return view;
    }

    private void initData() {
        List<Girls> girls = GirlsUtils.QueryAll();
        queryAllRecAdaper.initdate(girls);
    }

    private void initQueryAllRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mFqueryRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mFqueryRec.addItemDecoration(dd);
        queryAllRecAdaper = new QueryAllRecAdaper(getActivity());
        mFqueryRec.setAdapter(queryAllRecAdaper);
        queryAllRecAdaper.setIsChick(new QueryAllRecAdaper.IsChick() {
            @Override
            public void onechick(int i) {
                queryAllRecAdaper.Delete(i);
            }
        });
    }

    private void initView(View view) {
        mFqueryRec = (RecyclerView) view.findViewById(R.id.Fquery_rec);

    }
}
