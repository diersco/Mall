package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.OrderDetailInfo;
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
        super(R.layout.item_order_detail, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderDetailInfo.OrderDetailsListBean orderDetailsListBean) {
        baseViewHolder.setText(R.id.tv_goods_title, orderDetailsListBean.getGoodsTitle())
                .setText(R.id.tv_price, "￥" + orderDetailsListBean.getPrice())
                .setText(R.id.tv_num, "x" + orderDetailsListBean.getQuantity())
                .setText(R.id.tv_format, "规格：" + orderDetailsListBean.getSpec());
        TextView tvBuyAgain = baseViewHolder.getView(R.id.tv_buy_again);
        TextView tvAfterSales = baseViewHolder.getView(R.id.tv_after_sales);
        TextView tvEvaluate = baseViewHolder.getView(R.id.tv_evaluate);
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(orderDetailsListBean.getThumbnail(), ivCover);
        switch (orderDetailsListBean.getAfterSale()) {
            case 1:
                tvAfterSales.setText("申请售后");
                break;
            case 2:
                tvAfterSales.setText("已申请");
                break;
            case 3:
                tvAfterSales.setText("已取消");
                break;
            case 4:
                tvAfterSales.setText("已处理");
                break;
            default:
                break;
        }
        switch (orderDetailsListBean.getEvaluateStatus()) {
            case 1:
                tvEvaluate.setText("未出货");
                tvAfterSales.setVisibility(View.GONE);
                tvEvaluate.setVisibility(View.GONE);
                break;
            case 2:
                tvEvaluate.setText("待评价");
                break;
            case 3:
                tvEvaluate.setText("已评价");
                break;
            default:
                break;
        }
    }
}
