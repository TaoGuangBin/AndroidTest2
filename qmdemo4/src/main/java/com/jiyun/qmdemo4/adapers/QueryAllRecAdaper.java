package com.jiyun.qmdemo4.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo4.Girls;
import com.jiyun.qmdemo4.GirlsUtil;
import com.jiyun.qmdemo4.R;

import java.util.ArrayList;
import java.util.List;

public class QueryAllRecAdaper extends RecyclerView.Adapter<QueryAllRecAdaper.ViewHolder> {
    Context context;

    public QueryAllRecAdaper(Context context) {
        this.context = context;
    }

    List<Girls> girls = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.queryallitemlayout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girls.get(i).get_id());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(girls.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.oneChick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return girls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.queryall_iv);
            tv = itemView.findViewById(R.id.queryall_tv);
        }
    }

    public void initDate(List<Girls> girls) {
        if (this.girls != null) {
            this.girls.clear();
            this.girls.addAll(girls);
            notifyDataSetChanged();
        }
    }

    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void oneChick(int i);
    }

    public void Delete(int i) {
        Girls girl = this.girls.get(i);
        GirlsUtil.Delete(girl);
        this.girls.remove(i);
        notifyDataSetChanged();
    }
}
