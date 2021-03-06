package com.cyty.mall.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyty.mall.bean.MemberBenefitsInfo;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class MemberBannerAdapter extends BannerAdapter<MemberBenefitsInfo.ListBean, MemberBannerAdapter.BannerViewHolder> {
    private Context context;

    public MemberBannerAdapter(List<MemberBenefitsInfo.ListBean> datas, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(datas);
        this.context = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public MemberBannerAdapter.BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new MemberBannerAdapter.BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(MemberBannerAdapter.BannerViewHolder holder, MemberBenefitsInfo.ListBean data, int position, int size) {
//        GlideUtil.loadImage(data.getFilePath(), holder.imageView);
        Glide.with(context).load(data.getIcon()).into(holder.imageView);

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
