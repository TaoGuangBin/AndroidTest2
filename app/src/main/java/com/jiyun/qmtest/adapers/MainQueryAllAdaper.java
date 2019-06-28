package com.jiyun.qmtest.adapers;

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
import com.jiyun.qmtest.GirlUtils;
import com.jiyun.qmtest.Girls;
import com.jiyun.qmtest.R;

import java.util.ArrayList;
import java.util.List;

public class MainQueryAllAdaper extends RecyclerView.Adapter<MainQueryAllAdaper.ViewHolder> {
    Context context;
    List<Girls> girllist = new ArrayList<>();

    public MainQueryAllAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.mainqueryallitemlayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tv.setText(girllist.get(i).get_id());
        RoundedCorners roundedCorners = new RoundedCorners(20);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(viewHolder.iv);
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isChick.longchick(i);
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
            iv = itemView.findViewById(R.id.queryallitem_iv);
            tv = itemView.findViewById(R.id.queryallitem_tv);
        }
    }

    public void initData(List<Girls> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }
    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick{
        void longchick(int i);
    }
    public void Delete(int i){
        //先删除数据库，在删除集合
        Girls girls = girllist.get(i);
        GirlUtils.Delete(girls);
        girllist.remove(i);
        notifyDataSetChanged();

    }

}
