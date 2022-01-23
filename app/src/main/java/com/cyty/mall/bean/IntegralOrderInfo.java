package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class IntegralOrderInfo implements Parcelable {

    /**
     * id : 43
     * userId : 3
     * exchangeNumber : e3d3d2618378460b91af3c2feac15022
     * goodsId : 2
     * goodsTitle : 测试积分水乳
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * specId : 8
     * spec : 红瓶-5ml/瓶
     * payPoints : 5
     * exchangeTime : 2022-01-23 14:05:50
     * exchangeStatus : 2
     */

    private int id;
    private int userId;
    private String exchangeNumber;
    private int goodsId;
    private String goodsTitle;
    private String thumbnail;
    private int specId;
    private String spec;
    private int payPoints;
    private String exchangeTime;
    private int exchangeStatus;

    protected IntegralOrderInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        exchangeNumber = in.readString();
        goodsId = in.readInt();
        goodsTitle = in.readString();
        thumbnail = in.readString();
        specId = in.readInt();
        spec = in.readString();
        payPoints = in.readInt();
        exchangeTime = in.readString();
        exchangeStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(exchangeNumber);
        dest.writeInt(goodsId);
        dest.writeString(goodsTitle);
        dest.writeString(thumbnail);
        dest.writeInt(specId);
        dest.writeString(spec);
        dest.writeInt(payPoints);
        dest.writeString(exchangeTime);
        dest.writeInt(exchangeStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IntegralOrderInfo> CREATOR = new Creator<IntegralOrderInfo>() {
        @Override
        public IntegralOrderInfo createFromParcel(Parcel in) {
            return new IntegralOrderInfo(in);
        }

        @Override
        public IntegralOrderInfo[] newArray(int size) {
            return new IntegralOrderInfo[size];
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

    public String getExchangeNumber() {
        return exchangeNumber;
    }

    public void setExchangeNumber(String exchangeNumber) {
        this.exchangeNumber = exchangeNumber;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(int payPoints) {
        this.payPoints = payPoints;
    }

    public String getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(String exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public int getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(int exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }
}
