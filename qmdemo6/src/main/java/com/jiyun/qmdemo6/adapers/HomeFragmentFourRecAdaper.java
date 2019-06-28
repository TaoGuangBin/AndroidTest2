package com.jiyun.qmdemo6.adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo6.Girls;
import com.jiyun.qmdemo6.Girlsutil;
import com.jiyun.qmdemo6.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentFourRecAdaper extends RecyclerView.Adapter<HomeFragmentFourRecAdaper.ViewHolder> {
    Context context;
    List<Girls> girls = new ArrayList<>();

    public HomeFragmentFourRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.fragmentfouritemlayout, null);
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
                isChick.onechick(i);
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
            iv = itemView.findViewById(R.id.fouritem_iv);
            tv = itemView.findViewById(R.id.fouritem_tv);
        }
    }

    public void initdate(List<Girls> girls) {
        if (this.girls != null) {
            this.girls.clear();
            this.girls.addAll(girls);
            notifyDataSetChanged();
        }

    }

    public void initDelete(int i) {
        Girls girl = girls.get(i);
        Girlsutil.Delete(girl);
        girls.remove(girl);
        notifyDataSetChanged();
    }

    private IsChick isChick;

    public void setIsChick(IsChick isChick) {
        this.isChick = isChick;
    }

    public interface IsChick {
        void onechick(int i);
    }
}
