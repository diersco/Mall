package com.cyty.mall.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.OrderListInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:15
 * @描述
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListInfo, BaseViewHolder> {


    public OrderListAdapter(@Nullable List<OrderListInfo> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderListInfo orderListInfo) {
        baseViewHolder.setText(R.id.tv_time, orderListInfo.getOrderTime().split(" ")[0])
                .setText(R.id.tv_paymentAmount, "￥" + orderListInfo.getPaymentAmount());
        RecyclerView recyclerView = baseViewHolder.getView(R.id.recyclerview);
        TextView tvType = baseViewHolder.getView(R.id.tv_type);
        TextView tvLineGary = baseViewHolder.getView(R.id.tv_line_gary);
        TextView tvRoundPurple = baseViewHolder.getView(R.id.tv_round_purple);
        OrderSecondAdapter mAdapter = new OrderSecondAdapter(orderListInfo.getOrderDetailsList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        switch (orderListInfo.getOrderStatus()) {
            case 1:
                tvType.setText("待付款");
                tvLineGary.setVisibility(View.VISIBLE);
                tvRoundPurple.setVisibility(View.VISIBLE);
                tvLineGary.setText("取消订单");
                tvRoundPurple.setText("立即支付");
                break;
            case 2:
                tvType.setText("待发货");
                tvRoundPurple.setVisibility(View.VISIBLE);
                tvRoundPurple.setText("查看详情");
                break;
            case 3:
                tvType.setText("待收货");
                tvLineGary.setVisibility(View.VISIBLE);
                tvRoundPurple.setVisibility(View.VISIBLE);
                tvLineGary.setText("查看物流");
                tvRoundPurple.setText("确认收货");
                break;
            case 4:
                tvType.setText("已完成");
                tvRoundPurple.setVisibility(View.VISIBLE);
                tvRoundPurple.setText("查看详情");
                break;
            case 5:
                tvType.setText("已取消");
                break;
        }
    }
}
