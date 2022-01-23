package com.cyty.mall.event;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/18 15:56
 * @描述
 */
public class DiscountEvent {

    private double discount;
    private int couponId;

    public DiscountEvent(double discount, int couponId) {
        this.discount = discount;
        this.couponId = couponId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }
}

