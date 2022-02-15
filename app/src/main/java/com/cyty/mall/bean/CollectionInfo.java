package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/13 16:17
 * @描述
 */

public class CollectionInfo implements Parcelable {

    /**
     * id : 4
     * userId : 3
     * cellPhoneNumber : 17396263951
     * goodsId : 1
     * goodsTitle : 测试水乳
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * specId : 4
     * spec : 蓝瓶-120ml/瓶
     * price : 100
     * deletes : 1
     */

    private int id;
    private int userId;
    private String cellPhoneNumber;
    private int goodsId;
    private String goodsTitle;
    private String thumbnail;
    private int specId;
    private String spec;
    private String price;
    private int deletes;

    protected CollectionInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        cellPhoneNumber = in.readString();
        goodsId = in.readInt();
        goodsTitle = in.readString();
        thumbnail = in.readString();
        specId = in.readInt();
        spec = in.readString();
        price = in.readString();
        deletes = in.readInt();
    }

    public static final Creator<CollectionInfo> CREATOR = new Creator<CollectionInfo>() {
        @Override
        public CollectionInfo createFromParcel(Parcel in) {
            return new CollectionInfo(in);
        }

        @Override
        public CollectionInfo[] newArray(int size) {
            return new CollectionInfo[size];
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

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(cellPhoneNumber);
        parcel.writeInt(goodsId);
        parcel.writeString(goodsTitle);
        parcel.writeString(thumbnail);
        parcel.writeInt(specId);
        parcel.writeString(spec);
        parcel.writeString(price);
        parcel.writeInt(deletes);
    }
}
