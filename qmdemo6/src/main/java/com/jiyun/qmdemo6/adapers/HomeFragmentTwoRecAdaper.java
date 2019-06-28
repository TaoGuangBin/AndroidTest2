package com.jiyun.qmdemo6.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo6.R;
import com.jiyun.qmdemo6.beans.GirlBean;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentTwoRecAdaper extends RecyclerView.Adapter<HomeFragmentTwoRecAdaper.ViewHolder> {
    private int index;
    List<GirlBean.ResultsBean> girllist = new ArrayList<>();
    Context context;

    public HomeFragmentTwoRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.homefragmenttwoitemonelayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girllist.get(i).get_id());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                index = i;
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return girllist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.fitemone_iv);
            tv = itemView.findViewById(R.id.fitemone_tv);
        }
    }

    public void initData(List<GirlBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    /*private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void onechick(int i);
    }*/
    public void initDelete() {
        this.girllist.remove(index);
        notifyDataSetChanged();
    }

}
