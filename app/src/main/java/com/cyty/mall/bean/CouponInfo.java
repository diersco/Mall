package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:12
 * @描述
 */
public class CouponInfo implements Parcelable {

    /**
     * id : 6
     * userId : 3
     * couponTitle : 5折券
     * couponType : 2
     * discount : 0.5
     * conditions : 0
     * startTime : 2021-12-01 16:10:43
     * endTime : 2021-12-29 16:10:47
     * validStatus : 1
     * receiveRemark : null
     * deletes : 1
     */

    private int id;
    private int userId;
    private String couponTitle;
    private int couponType;
    private String discount;
    private String conditions;
    private String startTime;
    private String endTime;
    private int validStatus;
    private String receiveRemark;
    private int deletes;

    protected CouponInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        couponTitle = in.readString();
        couponType = in.readInt();
        discount = in.readString();
        conditions = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        validStatus = in.readInt();
        receiveRemark = in.readString();
        deletes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(couponTitle);
        dest.writeInt(couponType);
        dest.writeString(discount);
        dest.writeString(conditions);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeInt(validStatus);
        dest.writeString(receiveRemark);
        dest.writeInt(deletes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CouponInfo> CREATOR = new Creator<CouponInfo>() {
        @Override
        public CouponInfo createFromParcel(Parcel in) {
            return new CouponInfo(in);
        }

        @Override
        public CouponInfo[] newArray(int size) {
            return new CouponInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public String getReceiveRemark() {
        return receiveRemark;
    }

    public void setReceiveRemark(String receiveRemark) {
        this.receiveRemark = receiveRemark;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }
}
