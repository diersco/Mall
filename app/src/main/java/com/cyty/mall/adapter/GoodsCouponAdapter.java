package com.cyty.mall.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cyty.mall.R;
import com.cyty.mall.bean.ConfirmOrderInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:30
 * @描述
 */

public class GoodsCouponAdapter extends BaseQuickAdapter<ConfirmOrderInfo.CouponsListBean, BaseViewHolder> {
    public GoodsCouponAdapter(@Nullable List<ConfirmOrderInfo.CouponsListBean> data) {
        super(R.layout.item_coupon, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ConfirmOrderInfo.CouponsListBean couponsListBean) {
        baseViewHolder.setText(R.id.tv_coupon_price, couponsListBean.getDiscount() + "")
                .setText(R.id.tv_coupon_remark, "满" + couponsListBean.getConditions() + "减" + couponsListBean.getDiscount())
                .setText(R.id.tv_coupon_type, "￥" + couponsListBean.getCouponTitle())
                .setText(R.id.tv_validity_period, couponsListBean.getStartTime().split(" ")[0] + " - " + couponsListBean.getEndTime().split(" ")[0]);
    }
}
