package com.jiyun.qmdemo6.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.shape.RoundedCornerTreatment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo6.Girls;
import com.jiyun.qmdemo6.Girlsutil;
import com.jiyun.qmdemo6.R;
import com.jiyun.qmdemo6.beans.GirlBean;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentOneRecAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GirlBean.ResultsBean> girllist = new ArrayList<>();
    Context context;

    public HomeFragmentOneRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = View.inflate(context, R.layout.fragmentoneitemonelayout, null);
            ViewHolder1 holder1 = new ViewHolder1(view);
            return holder1;
        } else {
            View view = View.inflate(context, R.layout.fragmenttwoitemlayout, null);
            ViewHolder2 holder2 = new ViewHolder2(view);
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int type = getItemViewType(i);
        if (type == 0) {
            ViewHolder1 holder1 = (ViewHolder1) viewHolder;
            holder1.tv.setText(girllist.get(i).get_id());
            RoundedCorners roundedCorners = new RoundedCorners(20);
            RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder1.iv);
        } else {
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            holder2.tv.setText(girllist.get(i).get_id());
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder2.iv);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.onechick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return girllist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.itemone_iv);
            tv = itemView.findViewById(R.id.itemone_tv);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.twoitem_iv);
            tv = itemView.findViewById(R.id.twoitem_tv);
        }
    }

    public void initDate(List<GirlBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    public void initLoadMore(List<GirlBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void onechick(int i);
    }

    public void insert(int i) {
        //数据库添加操作
        GirlBean.ResultsBean girl = girllist.get(i);
        String id = girl.get_id();
        String url = girl.getUrl();
        Girls girls = new Girls(null,id,url);
        Girlsutil.insert(girls);
    }

}
