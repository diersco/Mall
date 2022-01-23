package com.cyty.mall.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.CouponInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:30
 * @描述
 */

public class CouponAdapter extends BaseQuickAdapter<CouponInfo, BaseViewHolder> {

    private int type;

    public CouponAdapter(@Nullable List<CouponInfo> data, int type) {
        super(R.layout.item_coupon, data);
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CouponInfo couponInfo) {
        baseViewHolder.setText(R.id.tv_coupon_price, couponInfo.getDiscount() + "")
                .setText(R.id.tv_coupon_remark, "满" + couponInfo.getConditions() + "减" + couponInfo.getDiscount())
                .setText(R.id.tv_coupon_type, "￥" + couponInfo.getCouponTitle())
                .setText(R.id.tv_validity_period, couponInfo.getStartTime().split(" ")[0] + " - " + couponInfo.getEndTime().split(" ")[0]);
        TextView tvUseType = baseViewHolder.getView(R.id.tv_use_type);
        switch (type){
            case 1:
                tvUseType.setText("立即使用");
                tvUseType.setBackgroundColor(Color.parseColor("#333333"));
                break;
            case 2:
                tvUseType.setText("已使用");
                tvUseType.setBackgroundColor(Color.parseColor("#909090"));
                break;
            case 3:
                tvUseType.setText("已过期");
                tvUseType.setBackgroundColor(Color.parseColor("#909090"));
                break;
        }
    }
}
