package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.ConfirmOrderInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConfirmOrderAdapter extends BaseQuickAdapter<ConfirmOrderInfo.ListBean, BaseViewHolder> {
    public ConfirmOrderAdapter(@Nullable List<ConfirmOrderInfo.ListBean> data) {
        super(R.layout.item_confirm_order, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ConfirmOrderInfo.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_goods_name, listBean.getGoodsTitle())
                .setText(R.id.tv_goods_format, listBean.getSpec())
                .setText(R.id.tv_buy_num, "x"+listBean.getQuantity())
                .setText(R.id.tv_goods_price, "￥" + listBean.getPrice());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(listBean.getThumbnail(), ivCover);
    }
}
