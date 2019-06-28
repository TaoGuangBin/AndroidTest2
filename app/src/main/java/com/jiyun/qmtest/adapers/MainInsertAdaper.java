package com.jiyun.qmtest.adapers;

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
import com.jiyun.qmtest.GirlUtils;
import com.jiyun.qmtest.Girls;
import com.jiyun.qmtest.R;
import com.jiyun.qmtest.bean.GirlsBean;

import java.util.ArrayList;
import java.util.List;

public class MainInsertAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;

    public MainInsertAdaper(Context context) {
        this.context = context;
    }

    List<GirlsBean.ResultsBean> girllist = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = View.inflate(context, R.layout.maininsertitem1layout, null);
            ViewHolder1 holder1 = new ViewHolder1(view);
            return holder1;

        } else {
            View view = View.inflate(context, R.layout.maininsertitem2layout, null);
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
            requestOptions.placeholder(R.mipmap.ic_launcher);
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder1.iv);
        } else {
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            holder2.tv.setText(girllist.get(i).get_id());
            RoundedCorners roundedCorners = new RoundedCorners(20);
            RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners).placeholder(R.mipmap.ic_launcher);
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder2.iv);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.onechick(i);
            }
        });
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
            iv = itemView.findViewById(R.id.insertitem1_iv);
            tv = itemView.findViewById(R.id.insertitem1_tv);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.insertitem2_iv);
            tv = itemView.findViewById(R.id.insertitem2_tv);
        }
    }

    public void initDate(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }
    public void initLoadmore(List<GirlsBean.ResultsBean> girllist){
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

        void longchick(int i);
    }
    public void initdelete(int i){
        girllist.remove(i);
        notifyDataSetChanged();
    }
    public void initinsert(int i){
        GirlsBean.ResultsBean resultsBean = girllist.get(i);
        String _id = resultsBean.get_id();
        String url = resultsBean.getUrl();
        Girls girls = new Girls(null, _id, url);
        GirlUtils.insert(girls);
        Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
    }
}
