package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/7 14:48
 * @描述
 */
public class GoodsListInfo implements Parcelable {

    /**
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * salesVolume : 999
     * goodsId : 1
     * price : 0.01
     * id : 6
     * title : 测试水乳
     */

    private String thumbnail;
    private int salesVolume;
    private int goodsId;
    private double price;
    private int id;
    private String title;

    protected GoodsListInfo(Parcel in) {
        thumbnail = in.readString();
        salesVolume = in.readInt();
        goodsId = in.readInt();
        price = in.readDouble();
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<GoodsListInfo> CREATOR = new Creator<GoodsListInfo>() {
        @Override
        public GoodsListInfo createFromParcel(Parcel in) {
            return new GoodsListInfo(in);
        }

        @Override
        public GoodsListInfo[] newArray(int size) {
            return new GoodsListInfo[size];
        }
    };

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbnail);
        parcel.writeInt(salesVolume);
        parcel.writeInt(goodsId);
        parcel.writeDouble(price);
        parcel.writeInt(id);
        parcel.writeString(title);
    }
}
