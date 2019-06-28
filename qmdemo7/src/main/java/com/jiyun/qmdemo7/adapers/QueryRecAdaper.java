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
import com.jiyun.qmdemo7.Girls;
import com.jiyun.qmdemo7.GirlsUtil;
import com.jiyun.qmdemo7.R;

import java.util.ArrayList;
import java.util.List;

public class QueryRecAdaper extends RecyclerView.Adapter<QueryRecAdaper.ViewHolder> {

    List<Girls> girls = new ArrayList<>();
    Context context;

    public QueryRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.queryitemlayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girls.get(i).get_id());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(girls.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isChick.longCheck(i);
                return false;
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
            iv = itemView.findViewById(R.id.queryitem_iv);
            tv = itemView.findViewById(R.id.queryitem_tv);

        }
    }

    public void initdata(List<Girls> girls) {
        if (this.girls != null) {
            this.girls.clear();
            this.girls.addAll(girls);
            notifyDataSetChanged();
        }
    }

    public void Delete(int i) {
        Girls girls = this.girls.get(i);
        GirlsUtil.Delete(girls);
        this.girls.remove(i);
        notifyDataSetChanged();

    }

    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void longCheck(int i);
    }

}
