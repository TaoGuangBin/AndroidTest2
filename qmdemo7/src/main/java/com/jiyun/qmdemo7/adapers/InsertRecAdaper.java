package com.jiyun.qmdemo7.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo7.Beans.GirlsBean;
import com.jiyun.qmdemo7.Girls;
import com.jiyun.qmdemo7.GirlsUtil;
import com.jiyun.qmdemo7.R;

import java.util.ArrayList;
import java.util.List;

public class InsertRecAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<GirlsBean.ResultsBean> girllist = new ArrayList<>();
    Context context;
    private int index;
    public InsertRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = View.inflate(context, R.layout.insertitemonelayout, null);
            ViewHolder1 holder1 = new ViewHolder1(view);
            return holder1;

        } else {
            View view = View.inflate(context, R.layout.insertitemtwolayout, null);
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
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder1.iv);

        } else {
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            holder2.tv.setText(girllist.get(i).get_id());
            RoundedCorners roundedCorners = new RoundedCorners(20);
            RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder2.iv);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = i;
                insert();
                Toast.makeText(context, "添加完成", Toast.LENGTH_SHORT).show();
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
            iv = itemView.findViewById(R.id.insertitemone_iv);
            tv = itemView.findViewById(R.id.insertitemone_tv);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.insertitemtwo_iv);
            tv = itemView.findViewById(R.id.insertitemtwo_tv);
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

    public void insert() {
        String id = this.girllist.get(index).get_id();
        String url = this.girllist.get(index).getUrl();
        Girls girls = new Girls(null, id, url);
        GirlsUtil.Insert(girls);
    }

}
