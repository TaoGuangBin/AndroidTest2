package com.jiyun.qmdemo5.Adapers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiyun.qmdemo5.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.jiyun.qmdemo5.Beans.BannerBean;
import com.jiyun.qmdemo5.Beans.GirlsBean;

public class MyHomeRecAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BannerBean.DataBean> bannerlist = new ArrayList<>();
    List<GirlsBean.ResultsBean> girllist = new ArrayList<>();
    Context context;

    public MyHomeRecAdaper(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View view = View.inflate(context, R.layout.bannerlayout, null);
            ViewHolder1 holder1 = new ViewHolder1(view);
            return holder1;

        } else {
            View view = View.inflate(context, R.layout.homeitemlayout, null);
            ViewHolder2 holder2 = new ViewHolder2(view);
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = getItemViewType(i);
        if (type == 0) {
            ViewHolder1 holder1 = (ViewHolder1) viewHolder;
            holder1.banner.setImages(bannerlist).setImageLoader(new MyImageLoader()).start();
        } else {
            if (bannerlist.size() > 0) {
                i = i - 1;
            }
            ViewHolder2 holder2 = (ViewHolder2) viewHolder;
            holder2.tv.setText(girllist.get(i).get_id());
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(girllist.get(i).getUrl()).apply(requestOptions).into(holder2.iv);
        }
    }

    class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerBean.DataBean banner = (BannerBean.DataBean) path;
            Glide.with(context).load(banner.getImagePath()).into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (bannerlist.size() > 0) {
            return girllist.size() + 1;
        } else {
            return girllist.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && bannerlist.size() > 0) {
            return 0;
        } else {
            return 1;
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        Banner banner;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_banner);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.item_iv);
            tv = itemView.findViewById(R.id.item_tv);
        }
    }

    public void initRefresh(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.clear();
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }

    }

    public void initLoadMore(List<GirlsBean.ResultsBean> girllist) {
        if (this.girllist != null) {
            this.girllist.addAll(girllist);
            notifyDataSetChanged();
        }
    }

    public void initBanner(List<BannerBean.DataBean> bannerlist) {
        if (this.bannerlist != null) {
            this.bannerlist.clear();
            this.bannerlist.addAll(bannerlist);
            notifyDataSetChanged();
        }
    }

}
