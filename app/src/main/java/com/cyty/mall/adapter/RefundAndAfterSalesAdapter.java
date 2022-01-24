package com.cyty.mall.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.AfterSalesInfo;
import com.cyty.mall.util.GlideUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class RefundAndAfterSalesAdapter extends BaseQuickAdapter<AfterSalesInfo, BaseViewHolder> {
    public RefundAndAfterSalesAdapter(@Nullable List<AfterSalesInfo> data) {
        super(R.layout.item_refund_and_after_sales, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AfterSalesInfo afterSalesInfo) {
        baseViewHolder.setText(R.id.tv_title, afterSalesInfo.getGoodsTitle())
                .setText(R.id.tv_time, afterSalesInfo.getCreateTime())
                .setText(R.id.tv_time, afterSalesInfo.getCreateTime())
                .setText(R.id.tv_format, afterSalesInfo.getSpec());
        ImageView ivCover = baseViewHolder.getView(R.id.iv_cover);
        GlideUtil.with(getContext()).displayImage(afterSalesInfo.getThumbnail(), ivCover);
        TextView tvType = baseViewHolder.getView(R.id.tv_type);
        TextView tvFeedback = baseViewHolder.getView(R.id.tv_feedback);
        TextView tvResult = baseViewHolder.getView(R.id.tv_result);
        TextView tvLeft = baseViewHolder.getView(R.id.tv_btn_gary_left);
        TextView tvRight = baseViewHolder.getView(R.id.tv_btn_purple);


        switch (afterSalesInfo.getSalesType()) {
            case 1:
                tvResult.setText("退货");
                break;
            case 2:
                tvResult.setText("退款");
                break;
            case 3:
                tvResult.setText("退货退款");
                break;
            case 4:
                tvResult.setText("售后");
                break;
            default:
                break;
        }
        switch (afterSalesInfo.getAfterSale()) {
            case 2:
                tvRight.setText("取消售后");
                tvFeedback.setText("已申请");
                tvType.setText("已申请");
                break;
            case 3:
                tvRight.setText("查看详情");
                tvFeedback.setText("已取消");
                tvType.setText("已取消");
                break;
            case 4:
                tvRight.setText("查看反馈");
                tvFeedback.setText("已反馈");
                tvType.setText("已反馈");
                break;
            default:
                break;
        }
    }
}