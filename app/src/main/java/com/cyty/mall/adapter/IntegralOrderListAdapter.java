package com.cyty.mall.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.IntegralOrderInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:15
 * @描述
 */
public class IntegralOrderListAdapter extends BaseQuickAdapter<IntegralOrderInfo, BaseViewHolder> {


    public IntegralOrderListAdapter(@Nullable List<IntegralOrderInfo> data) {
        super(R.layout.item_integral_order, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, IntegralOrderInfo orderListInfo) {
        baseViewHolder.setText(R.id.tv_time, orderListInfo.getExchangeTime().split(" ")[0])
                .setText(R.id.tv_paymentAmount, orderListInfo.getPayPoints() + "积分")
                .setText(R.id.tv_goods_title, orderListInfo.getGoodsTitle())
                .setText(R.id.tv_price, "￥" + orderListInfo.getPayPoints() + "积分")
                .setText(R.id.tv_format, "规格：" + orderListInfo.getSpec());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(orderListInfo.getThumbnail(), ivCover);
        TextView tvType = baseViewHolder.getView(R.id.tv_type);
        TextView tvLineGary = baseViewHolder.getView(R.id.tv_line_gary);

        switch (orderListInfo.getExchangeStatus()) {
            case 2:
                tvType.setText("待发货");
                break;
            case 3:
                tvType.setText("待收货");
                tvLineGary.setVisibility(View.VISIBLE);
                tvLineGary.setText("查看物流");
                break;

        }
    }
}
