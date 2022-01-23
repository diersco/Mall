package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderAmountInfo implements Parcelable {

    /**
     * orderAmount : 50.0
     * discount : 50.0
     */

    private double orderAmount;
    private double discount;

    protected OrderAmountInfo(Parcel in) {
        orderAmount = in.readDouble();
        discount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(orderAmount);
        dest.writeDouble(discount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderAmountInfo> CREATOR = new Creator<OrderAmountInfo>() {
        @Override
        public OrderAmountInfo createFromParcel(Parcel in) {
            return new OrderAmountInfo(in);
        }

        @Override
        public OrderAmountInfo[] newArray(int size) {
            return new OrderAmountInfo[size];
        }
    };

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
