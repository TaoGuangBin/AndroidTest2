package com.jiyun.qmdemo4.adapers;

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
import com.jiyun.qmdemo4.Beans.GirlsBean;
import com.jiyun.qmdemo4.Girls;
import com.jiyun.qmdemo4.GirlsUtil;
import com.jiyun.qmdemo4.R;

import java.util.ArrayList;
import java.util.List;

public class InsertRecAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<GirlsBean.ResultsBean> girlslist = new ArrayList<>();

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
            holder1.tv.setText(girlslist.get(i).get_id());
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(girlslist.get(i).getUrl()).apply(requestOptions).into(holder1.iv);
        } else {
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            holder2.tv.setText(girlslist.get(i).get_id());
            RoundedCorners roundedCorners = new RoundedCorners(20);
            RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(context).load(girlslist.get(i).getUrl()).apply(requestOptions).into(holder2.iv);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChick.OneChick(i);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isChick.LongChick(i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return girlslist.size();
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

    public void Refresh(List<GirlsBean.ResultsBean> girlslist) {
        if (this.girlslist != null) {
            this.girlslist.clear();
            this.girlslist.addAll(girlslist);
            notifyDataSetChanged();
        }
    }

    public void LoadMore(List<GirlsBean.ResultsBean> girlslist) {
        if (this.girlslist != null) {
            this.girlslist.addAll(girlslist);
            notifyDataSetChanged();
        }
    }

    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void OneChick(int i);

        void LongChick(int i);
    }

    public void Delete(int i) {
        this.girlslist.remove(i);
        notifyDataSetChanged();
    }

    public void insert(int i) {
        GirlsBean.ResultsBean girl = this.girlslist.get(i);
        String id = girl.get_id();
        String url = girl.getUrl();
        Girls girls = new Girls(null, id, url);
        GirlsUtil.Insert(girls);
    }


}
