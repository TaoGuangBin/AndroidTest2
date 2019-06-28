package com.jiyun.qmdemo3.myadapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo3.Girls;
import com.jiyun.qmdemo3.GirlsUtils;
import com.jiyun.qmdemo3.R;

import java.util.ArrayList;
import java.util.List;

public class QueryAllRecAdaper extends RecyclerView.Adapter<QueryAllRecAdaper.ViewHolder> {
    Context context;
    List<Girls> girls = new ArrayList<>();

    public QueryAllRecAdaper(Context context) {
        this.context = context;
    }

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
        RoundedCorners roundedCorners = new RoundedCorners(30);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(girls.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.onechick(i);
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
            iv = itemView.findViewById(R.id.queryallitem_iv1);
            tv = itemView.findViewById(R.id.queryallitem_tv1);
        }
    }

    public void initdate(List<Girls> girls) {
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

    public interface IsChick{
        void onechick(int i);
    }
    public void Delete(int i){
        Girls girls = this.girls.get(i);
        GirlsUtils.Delete(girls);
        this.girls.remove(i);
        notifyDataSetChanged();
    }
}
