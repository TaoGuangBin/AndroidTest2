package com.jiyun.qmdemo7.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo7.Beans.GirlsBean;
import com.jiyun.qmdemo7.R;

import java.util.ArrayList;
import java.util.List;

public class ShowRecAdaper extends RecyclerView.Adapter<ShowRecAdaper.ViewHolder> {
    List<GirlsBean.ResultsBean> girllist = new ArrayList<>();
    Context context;
    private int index;

    public ShowRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.showitemlayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girllist.get(i).get_id());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                Delete();
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
            iv = itemView.findViewById(R.id.showitem_iv);
            tv = itemView.findViewById(R.id.showitem_tv);

        }
    }

    public void initdata(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }

    }

    public void loadmore(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    public void Delete() {
        this.girllist.remove(index);
        notifyDataSetChanged();

    }
}
