package com.jiyun.qmdemo6.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiyun.qmdemo6.R;
import com.jiyun.qmdemo6.adapers.HomeFragmentTwoRecAdaper;
import com.jiyun.qmdemo6.beans.GirlBean;
import com.jiyun.qmdemo6.presenter.Presenterimpl;
import com.jiyun.qmdemo6.view.MyView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentTwo extends Fragment implements MyView {


    private View view;
    private RecyclerView mHometwoRec;
    private Presenterimpl presenterimpl;
    private HomeFragmentTwoRecAdaper homeFragmentTwoRecAdaper;
    private static final String TAG = "HomeFragmentTwo";

    public HomeFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_home_fragment_two, null);
        initView(view);
        initRec();
        initPersenter();
        return view;
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mHometwoRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mHometwoRec.addItemDecoration(dd);
        homeFragmentTwoRecAdaper = new HomeFragmentTwoRecAdaper(getActivity());
        mHometwoRec.setAdapter(homeFragmentTwoRecAdaper);
        registerForContextMenu(mHometwoRec);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 1, 1, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                homeFragmentTwoRecAdaper.initDelete();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initPersenter() {
        presenterimpl = new Presenterimpl(this);
        presenterimpl.getgirllist();
    }

    private void initView(View view) {
        mHometwoRec = (RecyclerView) view.findViewById(R.id.hometwo_rec);
    }

    @Override
    public void succeediv(List<GirlBean.ResultsBean> girllist) {
        homeFragmentTwoRecAdaper.initData(girllist);
    }

    @Override
    public void erroriv(String error) {
        Log.d(TAG, "erroriv: " + error);
    }
}
