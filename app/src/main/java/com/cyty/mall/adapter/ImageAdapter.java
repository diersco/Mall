package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_img, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String str) {
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        Glide.with(getContext()).load(str).into(ivCover);
    }
}
