package com.jiyun.qmtest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jiyun.qmtest.GirlUtils;
import com.jiyun.qmtest.Girls;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.adapers.MainQueryAllAdaper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainQueryALLFragment extends Fragment {


    private View view;
    private RecyclerView mMainshowRec;
    private MainQueryAllAdaper mainQueryAllAdaper;
    private int index;

    public MainQueryALLFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_query_all, null);
        initView(view);
        initRec();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            initData();
        }
    }

    private void initData() {
        if (mainQueryAllAdaper != null) {
            List<Girls> girlsList = GirlUtils.QueryAll();
            mainQueryAllAdaper.initData(girlsList);
        }
    }

    private void initRec() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        mMainshowRec.setLayoutManager(lm);
        DividerItemDecoration dd = new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL);
        mMainshowRec.addItemDecoration(dd);
        mainQueryAllAdaper = new MainQueryAllAdaper(getActivity());
        mMainshowRec.setAdapter(mainQueryAllAdaper);
        mainQueryAllAdaper.setIsChick(new MainQueryAllAdaper.IsChick() {
            @Override
            public void longchick(int i) {
                index = i;
            }
        });
        registerForContextMenu(mMainshowRec);

    }

    private void initView(View view) {
        mMainshowRec = (RecyclerView) view.findViewById(R.id.mainshow_rec);
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
                mainQueryAllAdaper.Delete(index);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
