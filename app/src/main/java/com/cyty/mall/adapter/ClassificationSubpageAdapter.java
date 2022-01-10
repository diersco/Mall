package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.GoodsListInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/7 16:28
 * @描述
 */

public class ClassificationSubpageAdapter extends BaseQuickAdapter<GoodsListInfo, BaseViewHolder> {
    public ClassificationSubpageAdapter(@Nullable List<GoodsListInfo> data) {
        super(R.layout.item_goods_list, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, GoodsListInfo goodsListInfo) {
        baseViewHolder.setText(R.id.tv_goods_name, goodsListInfo.getTitle())
                .setText(R.id.tv_price, goodsListInfo.getPrice()+"")
                .setText(R.id.tv_redeem_now, goodsListInfo.getSalesVolume()+"人购买");
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(goodsListInfo.getThumbnail(),ivCover);
    }
}