package com.cyty.mall.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyty.mall.bean.ClassIfPageBannerInfo;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 11:25
 * @描述
 */
public class ImageBannerAdapter extends BannerAdapter<ClassIfPageBannerInfo.ClassifPageBannerListBean, ImageBannerAdapter.BannerViewHolder> {


    private Context context;

    public ImageBannerAdapter(List<ClassIfPageBannerInfo.ClassifPageBannerListBean> datas, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(datas);
        this.context = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, ClassIfPageBannerInfo.ClassifPageBannerListBean data, int position, int size) {
//        GlideUtil.loadImage(data.getFilePath(), holder.imageView);
        Glide.with(context).load(data.getResourceLink()).into(holder.imageView);

    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
