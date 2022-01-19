package com.cyty.mall.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.OrderDetailInfo;
import com.cyty.mall.bean.OrderListInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:15
 * @描述
 */
public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailInfo.OrderDetailsListBean, BaseViewHolder> {


    public OrderDetailAdapter(@Nullable List<OrderDetailInfo.OrderDetailsListBean> data) {
        super(R.layout.item_order_second, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderDetailInfo.OrderDetailsListBean orderDetailsListBean) {
        baseViewHolder.setText(R.id.tv_goods_title, orderDetailsListBean.getGoodsTitle())
                .setText(R.id.tv_price, "￥" + orderDetailsListBean.getPrice())
                .setText(R.id.tv_num, "x" + orderDetailsListBean.getQuantity())
                .setText(R.id.tv_format, "规格：" + orderDetailsListBean.getSpec());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(orderDetailsListBean.getThumbnail(), ivCover);
    }
}
