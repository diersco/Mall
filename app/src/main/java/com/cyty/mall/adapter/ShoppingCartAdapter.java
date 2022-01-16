package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.CartGoodsInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShoppingCartAdapter  extends BaseQuickAdapter<CartGoodsInfo, BaseViewHolder> {
    public ShoppingCartAdapter(@Nullable List<CartGoodsInfo> data) {
        super(R.layout.item_cart, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CartGoodsInfo goodsInfo) {
        baseViewHolder.setText(R.id.tv_title, goodsInfo.getGoodsTitle())
                .setText(R.id.tv_format, goodsInfo.getSpec())
                .setText(R.id.tv_price, goodsInfo.getPrice());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(goodsInfo.getThumbnail(), ivCover);
    }
}