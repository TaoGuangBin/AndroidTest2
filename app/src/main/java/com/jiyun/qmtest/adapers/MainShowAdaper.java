package com.jiyun.qmtest.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.bean.GirlsBean;

import java.util.ArrayList;
import java.util.List;

public class MainShowAdaper extends RecyclerView.Adapter<MainShowAdaper.ViewHolder> {
    Context context;

    public MainShowAdaper(Context context) {
        this.context = context;
    }

    List<GirlsBean.ResultsBean> girllist = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.mainshowitem1layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girllist.get(i).get_id());
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.onechick(i);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isChick.Longchick(i);
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
            iv = itemView.findViewById(R.id.showitem1_iv);
            tv = itemView.findViewById(R.id.showitem1_tv);

        }
    }

    public void initDate(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    ;
    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void onechick(int i);

        void Longchick(int i);
    }

    public void initDelete(int i) {
        girllist.remove(i);
        notifyDataSetChanged();
    }

}
